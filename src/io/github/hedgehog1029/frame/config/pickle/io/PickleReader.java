package io.github.hedgehog1029.frame.config.pickle.io;

import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PickleReader {

	private FileReader in;

	public PickleReader(FileReader in) {
		this.in = in;
	}

	public JSONObject read() {
		Scanner scanner = new Scanner(in);
		String result = "";

		while(scanner.hasNext()) {
			result += scanner.next();
		}

		return new JSONObject(result);
	}

	public void end() {
		try {
			in.close();
		} catch (IOException e) {
			System.out.println("[pickle WARN] Couldn't close the input stream!");
		}
	}
}
