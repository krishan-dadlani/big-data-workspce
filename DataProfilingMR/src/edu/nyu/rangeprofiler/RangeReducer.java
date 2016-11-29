package edu.nyu.rangeprofiler;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class RangeReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	int max, min;
	String maxString, minString;

	protected void setup(Context context) throws java.io.IOException, InterruptedException {
		max = 0;
		min = 999999999;
	}

	protected void reduce(Text key, Iterable<IntWritable> value, Context context)
			throws java.io.IOException, InterruptedException {
		/*
		 * IntWritable max = value.iterator().next();
		 * 
		 * while( value.iterator().hasNext() ) { IntWritable current =
		 * value.iterator().next(); if( current.compareTo(max) > 0 ) max =
		 * current; } context.write(key, max);
		 */
		try {
			if (Integer.valueOf(key.toString()) > max) {
				max = Integer.valueOf(key.toString());
				maxString = key.toString();
			}

			if (Integer.valueOf(key.toString()) < min) {
				min = Integer.valueOf(key.toString());
				minString = key.toString();
			}
		} catch (Exception e) {
			System.out.println("Skipping :: " + key.toString());
		}

	}

	protected void cleanup(Context context) throws IOException, InterruptedException {
		if (minString != null && maxString != null) {
			// we add '0' for identifying minimum value
			context.write(new Text(minString), new IntWritable(0));

			// we add '1' for identifying maximum value
			context.write(new Text(maxString), new IntWritable(1));
		}
	}

}
