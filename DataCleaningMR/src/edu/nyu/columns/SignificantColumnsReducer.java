package edu.nyu.columns;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SignificantColumnsReducer extends Reducer<Text, Text, Text, Text> {
	String id, value;

	protected void setup(Context context) throws java.io.IOException, InterruptedException {
		id = new String();
		value = new String();
	}

	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws java.io.IOException, InterruptedException {

		id = key.toString();

		for (Text val : values) {
			value = val.toString();
		}

		context.write(new Text(id), new Text(value));

	}
}
