package edu.nyu.rdba;

import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankIterativeReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		final String regexFloat = "^-?\\d*\\.\\d+$";
		final String regexInteger = "^-?\\d+$";
		final String regex = regexFloat + "|" + regexInteger;

		float totalPageRank = 0;
		String pages = "";

		for (Text val : values) {
			String pageRank = val.toString();
			String[] pagesWithRank = pageRank.split(" ");
			String[] initPages = {};

			if (pages.length() > 0) {
				initPages = pages.split(" ");
			}

			String last = pagesWithRank[pagesWithRank.length - 1].trim();
			float individualPageRank = 0.0f;

			if (Pattern.matches(regex, last)) {
				individualPageRank = Float.parseFloat(last);
				totalPageRank += individualPageRank;

				if (initPages.length > 0 && Pattern.matches(regex, initPages[initPages.length - 1])) {
					pages = pages.replace(initPages[initPages.length - 1], String.valueOf(totalPageRank));
				} else {
					pages = pages.concat(String.valueOf(totalPageRank));
				}
			} else {
				pages = "";
				for (int i = 0; i < pagesWithRank.length; i++) {
					pages = pages.concat(pagesWithRank[i] + " ");
				}
				pages = pages.concat(String.valueOf(totalPageRank));
			}
		}

		context.write(key, new Text(pages));
	}
}
