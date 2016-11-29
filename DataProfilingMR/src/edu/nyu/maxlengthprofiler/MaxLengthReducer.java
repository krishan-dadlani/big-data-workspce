package edu.nyu.maxlengthprofiler;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxLengthReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	String maxWord;

	protected void setup(Context context) throws java.io.IOException, InterruptedException {
		maxWord = new String();
	}

	protected void reduce(Text key, Iterable<IntWritable> value, Context context)
			throws java.io.IOException, InterruptedException {
		if (key.toString().length() > maxWord.length()) {
			maxWord = key.toString();
		}
	}

	protected void cleanup(Context context) throws IOException, InterruptedException {
		context.write(new Text(maxWord), new IntWritable(maxWord.length()));
	}

}
