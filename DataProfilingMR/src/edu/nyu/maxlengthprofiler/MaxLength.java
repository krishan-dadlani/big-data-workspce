package edu.nyu.maxlengthprofiler;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxLength {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if (args.length != 2) {
			System.err.println("Usage: MaxLength <input path> <output path>");
			System.exit(-1);
		}

		Path input, output = null;
		FileSystem fs = null;

		Configuration wordConf = new Configuration();
		fs = FileSystem.get(wordConf);

		Job job = Job.getInstance(wordConf);
		job.setJarByClass(MaxLength.class);
		job.setJobName("Count Words");

		input = new Path(args[0]);
		output = new Path("max_length_output/" + args[1]);

		if (fs.exists(output))
			fs.delete(output, true);

		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);

		job.setMapperClass(MaxLengthMapper.class);
		job.setReducerClass(MaxLengthReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
