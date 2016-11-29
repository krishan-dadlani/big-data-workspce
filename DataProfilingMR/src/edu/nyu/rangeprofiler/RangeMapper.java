package edu.nyu.rangeprofiler;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RangeMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String line = value.toString();
		String[] lines = line.split("\n");

		for (String data : lines) {
			String[] token = data.split(",");
			try {
				// specify the column index for which we need to find the range.
				// In this case it is 2
				// context.write(new Text(token[7]), new IntWritable(Integer.valueOf(token[7])));
				context.write(new Text(token[5]), new IntWritable(token[5].length()));
			} catch (Exception e) {
				System.out.println("Skipping :: " + token[5]);
				e.printStackTrace();
			}
		}
	}
}
