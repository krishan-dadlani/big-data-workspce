package edu.nyu.rangefloatprofiler;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RangeFloatMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String line = value.toString();
		String[] lines = line.split("\n");

		for (int i = 0; i < lines.length; i++) {
			String[] token = lines[i].split(",");
			// specify the column index for which we need to find the range. In
			// this case it is 6
			context.write(new Text(token[6]), new FloatWritable(token[6].length()));

		}
	}
}
