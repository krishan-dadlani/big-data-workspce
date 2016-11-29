package edu.nyu.falsy;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FalsyColumns {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if (args.length != 1) {
			System.err.println("Usage: FalsyColumns <input path>");
			System.exit(-1);
		}

		Path input, output = null;
		FileSystem fs = null;

		Configuration wordConf = new Configuration();
		fs = FileSystem.get(wordConf);

		Job job = Job.getInstance(wordConf);
		job.setJarByClass(FalsyColumns.class);
		job.setJobName("Falsy Columns");

		input = new Path("significant_columns_output");
		output = new Path("falsy_columns_output");

		if (fs.exists(output))
			fs.delete(output, true);

		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);

		job.setMapperClass(FalsyColumnsMapper.class);
		job.setReducerClass(FalsyColumnsReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
