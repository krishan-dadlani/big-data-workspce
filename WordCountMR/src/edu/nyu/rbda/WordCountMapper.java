package edu.nyu.rbda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		Configuration conf = context.getConfiguration();
		ArrayList<String> searchStrings = new ArrayList<String>(Arrays.asList(conf.getStrings("wordsToSearch")));

		String line = value.toString();
		String[] tokens = line.split("d*[.@:=#\\-,/ ]");

		for (String token : tokens) {
			Iterator<String> searchStringsItr = searchStrings.iterator();
			while (searchStringsItr.hasNext()) {
				String searchString = searchStringsItr.next();
				if (searchString.equalsIgnoreCase(token)) {
					System.out.println(searchString);
					searchStringsItr.remove();
					context.write(new Text(searchString), new IntWritable(1));
				} else {
					context.write(new Text(searchString), new IntWritable(0));
				}
			}
		}
	}
}
