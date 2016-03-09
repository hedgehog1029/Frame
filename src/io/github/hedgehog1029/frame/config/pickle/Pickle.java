package io.github.hedgehog1029.frame.config.pickle;

import io.github.hedgehog1029.frame.config.pickle.io.PickleReader;
import io.github.hedgehog1029.frame.config.pickle.io.PickleWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Pickle {

	private String file;

	public static Pickle open(String file) {
		return new Pickle(file);
	}

	public static boolean exists(String file) {
		return new File(file).exists();
	}

	private Pickle(String file) {
		this.file = file;
	}

	private PickleWriter writer;
	private PickleReader reader;

	public PickleWriter writer() {
		if (writer != null) return writer;

		try {
			this.writer = new PickleWriter(new FileWriter(file));

			return writer;
		} catch (IOException e) {
			System.out.println("[pickle WARN] Couldn't open that file!");

			return null;
		}
	}

	public PickleReader reader() {
		if (reader != null) return reader;

		try {
			this.reader = new PickleReader(new FileReader(file));

			return reader;
		} catch (IOException e) {
			System.out.println("[pickle WARN] Couldn't open that file!");
		}

		return null;
	}
}
