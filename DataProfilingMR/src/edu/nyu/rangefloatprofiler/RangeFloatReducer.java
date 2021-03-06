package edu.nyu.rangefloatprofiler;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class RangeFloatReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
	float max, min;
	String maxString, minString;

	protected void setup(Context context) throws java.io.IOException, InterruptedException {
		max = 0;
		min = 999999999;
	}

	protected void reduce(Text key, Iterable<FloatWritable> value, Context context)
			throws java.io.IOException, InterruptedException {
		try {
			if (Float.valueOf(key.toString()) > max) {
				max = Float.valueOf(key.toString());
				maxString = key.toString();
			}

			if (Float.valueOf(key.toString()) < min) {
				min = Float.valueOf(key.toString());
				minString = key.toString();
			}
		} catch (Exception e) {
			System.out.println("Ignoring value :: " + key.toString());
		}

	}

	protected void cleanup(Context context) throws IOException, InterruptedException {
		if (minString != null && maxString != null) {
			// we add '0' for identifying minimum value
			context.write(new Text(minString), new FloatWritable(0));

			// we add '1' for identifying maximum value
			context.write(new Text(maxString), new FloatWritable(1));
		}
	}

}
