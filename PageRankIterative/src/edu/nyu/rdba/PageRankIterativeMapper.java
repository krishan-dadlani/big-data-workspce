package edu.nyu.rdba;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PageRankIterativeMapper extends Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		Text outLink = new Text();
		Text sourceAndPageRank = new Text();

		String[] tokens = value.toString().split("\\s+");

		int numberOfOutlinks = tokens.length - 2;

		String source = tokens[0];
		float pageRank = Float.parseFloat(tokens[tokens.length - 1]);
		float calculatedPageRank = pageRank / numberOfOutlinks;

		for (int j = 1; j <= numberOfOutlinks; j++) {
			outLink.set(tokens[j]);
			sourceAndPageRank.set(source + " " + calculatedPageRank);

			context.write(outLink, sourceAndPageRank);
		}

		Text page = new Text();
		Text outLinks = new Text();

		page.set(source);

		String outLinksStr = "";
		for (int j = 1; j <= numberOfOutlinks; j++) {
			outLinksStr = outLinksStr + tokens[j] + " ";
		}

		outLinks.set(outLinksStr.trim());

		context.write(page, outLinks);
	}
}
