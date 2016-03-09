package io.github.hedgehog1029.frame.config;

import io.github.hedgehog1029.frame.annotations.Configuration;
import io.github.hedgehog1029.frame.annotations.Setting;
import io.github.hedgehog1029.frame.config.pickle.Pickle;
import io.github.hedgehog1029.frame.logger.Logger;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.logging.Level;

public class ConfigurationBuilder {

	private static HashMap<Class, Object> configurations = new HashMap<>();

	public static void add(Class clazz) {
		if (!clazz.isAnnotationPresent(Configuration.class))
			return;

		configurations.put(clazz, build(clazz));
	}

	public static Object get(Class clazz) {
		return configurations.get(clazz);
	}

	/**
	 * Write in-memory changes of config to its file
	 * @param clazz The configuration class to sync.
	 */
	public static void sync(Class clazz) {
		Object config = configurations.get(clazz);

		if (config == null)
			return;

		JSONObject result = buildToJson(config);
		String name = config.getClass().getDeclaredAnnotation(Configuration.class).value();

		Pickle pickle = Pickle.open(String.format("%s.json", name));
		pickle.writer().write(result).end();
	}

	public static void syncAll() {
		configurations.keySet().forEach(ConfigurationBuilder::sync);
	}

	/**
	 * Replace the in-memory variant of the configuration with the saved version.
	 * This will silently do nothing if the config class was never loaded (use .add instead)
	 * @param clazz The configuration class to reload.
	 */
	public static void reload(Class clazz) {
		configurations.replace(clazz, build(clazz));
	}

	private static Object build(Class clazz) {
		Object object;

		try {
			object = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			Logger.err("Failed to build a configuration - couldn't access the class!");
			return null;
		}

		String name = object.getClass().getDeclaredAnnotation(Configuration.class).value();

		String filename = String.format("%s.json", name);
		if (Pickle.exists(filename)) {
			Pickle pickle = Pickle.open(filename);

			JSONObject data = pickle.reader().read();

			buildFromJson(data, clazz, object);

			pickle.reader().end();
		}

		sync(clazz);

		return object;
	}

	private static JSONObject buildToJson(Object instance) {
		JSONObject config = new JSONObject();
		Class clazz = instance.getClass();

		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Setting.class)) {
				try {
					field.setAccessible(true);
					config.put(field.getName(), field.get(instance));
				} catch (IllegalAccessException | ClassCastException e) {
					Logger.err("Failed to build a configuration - couldn't access a field!");
				}
			}
		}

		return config;
	}

	private static Object buildFromJson(JSONObject object, Class clazz, Object instance) {
		for (String key : object.keySet()) {
			try {
				Field field = clazz.getField(key);

				field.setAccessible(true);
				field.set(instance, object.get(key));
			} catch (NoSuchFieldException ignored) {
				Logger.log(Level.FINER, "A key in JSON was not found in the class it is being rebuilt to.");
			} catch (IllegalAccessException e) {
				Logger.err("Couldn't access a field when rebuilding a config!");
			}
		}

		return object;
	}
}
