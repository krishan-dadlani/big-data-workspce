package edu.nyu.falsy;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FalsyColumnsMapper extends Mapper<LongWritable, Text, Text, Text> {
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String line = value.toString();
		String[] lines = line.split("\n");

		for (String data : lines) {
			String dateReceived = data.split("\t")[0];
			String[] token = data.split("\t")[1].split(", ");

			// Discarding all the rows with null or empty values and cleaning
			// the dataset
			if (!token[0].equals("NULL") && !token[10].equals("NULL") && !token[13].equals("NULL")
					&& !token[19].equals("NULL") && !token[22].equals("NULL") && !token[27].equals("NULL") && !token[27].equals("NULL")) {

				context.write(new Text(dateReceived),
						new Text(token[0] + ", " + token[1] + ", " + token[2] + ", " + token[3] + ", " + token[4]));
			}
		}
	}
}
