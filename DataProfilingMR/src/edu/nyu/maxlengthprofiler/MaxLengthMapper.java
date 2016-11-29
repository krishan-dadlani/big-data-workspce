package edu.nyu.maxlengthprofiler;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxLengthMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String line = value.toString();
		String[] lines = line.split("\n");

		for (String data : lines) {
			String[] token = data.split(",");
			// specify the column index for which we need to find the maximum length. In this case it is 3
			context.write(new Text(token[3]), new IntWritable(token[3].length()));
		}
	}
}
