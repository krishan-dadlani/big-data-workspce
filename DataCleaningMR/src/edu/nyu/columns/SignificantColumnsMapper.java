package edu.nyu.columns;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SignificantColumnsMapper extends Mapper<LongWritable, Text, Text, Text> {
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String line = value.toString();
		String[] lines = line.split("\n");

		for (String data : lines) {
			String[] token = data.split(",");

			// specify the column indices for which we are significant
			context.write(new Text(token[0]), // OPEID
					new Text(token[0] + // OPEID
							", " + token[1] + // Name
							", " + token[3] + // City
							", " + token[4] + // State
							", " + token[5] + // State Desc
							", " + token[6] + // Zip code 
							", " + token[7]	+ // Zip Ext 
							", " + token[10] + // Year 1 (2013)
							", " + token[13] + // DRate 1 
							", " + token[15] + // Ethnic Code 
							", " + token[19] + // Year 2 (2012)
							", " + token[22] + // DRate 2 
							", " + token[24] + // Year 3 (2011)
							", " + token[27]  // DRate 3 
									));
		}
	}
}
