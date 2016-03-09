package io.github.hedgehog1029.frame.config.pickle.io;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class PickleWriter {

	private FileWriter out;

	public PickleWriter(FileWriter os) {
		this.out = os;
	}

	public PickleWriter write(JSONObject object) {
		object.write(out);

		return this;
	}

	public void end() {
		try {
			out.close();
		} catch (IOException e) {
			System.out.println("[pickle WARN] Couldn't close the output stream!");
		}
	}
}
