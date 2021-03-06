package edu.nyu.rbda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CountWords {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Usage: CountWords <input path>");
			System.exit(-1);
		}

		Map<String, Integer> searchStrings = new HashMap<String, Integer>();

		searchStrings.put("hackathon", 0);
		searchStrings.put("Dec", 0);
		searchStrings.put("Chicago", 0);
		searchStrings.put("Java", 0);

		Iterator it = countWord(searchStrings, new FileReader(args[0])).entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " : " + pair.getValue());
		}

	}

	public static Map<String, Integer> countWord(Map<String, Integer> searchStrings, FileReader file)
			throws IOException {

		try (BufferedReader br = new BufferedReader(file)) {
			String line;
			while ((line = br.readLine()) != null) {

				Iterator it = searchStrings.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, Integer> pair = (Map.Entry) it.next();

					if (line.toLowerCase().contains(pair.getKey().toString().toLowerCase())) {
						int count = Integer.parseInt(pair.getValue().toString());
						searchStrings.put(pair.getKey().toString(), ++count);
					}
				}
			}
		}
		return searchStrings;
	}
}
