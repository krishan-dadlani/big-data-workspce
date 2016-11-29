package edu.nyu.falsy;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FalsyColumnsReducer extends Reducer<Text, Text, Text, Text> {
	String universityID, value;

	protected void setup(Context context) throws java.io.IOException, InterruptedException {
		universityID = new String();
		value = new String();
	}

	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws java.io.IOException, InterruptedException {

		universityID = key.toString();

		for (Text val : values) {
			value = val.toString();
		}

		context.write(new Text(universityID), new Text(value));

	}
}
