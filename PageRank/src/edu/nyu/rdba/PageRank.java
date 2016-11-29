package edu.nyu.rdba;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class PageRank {

	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			System.err.println("Usage: PageCount <input path> <output path>");
			System.exit(-1);
		}

		Path input = new Path(args[0]);
		Path output = new Path(args[1]);

		Configuration pageRankConf = new Configuration();
		FileSystem fs = FileSystem.get(pageRankConf);

		if (fs.exists(output))
			fs.delete(output, true);

		Job job = Job.getInstance(pageRankConf);
		job.setJarByClass(PageRank.class);
		job.setJobName("Page Rank");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(PageRankMapper.class);
		job.setReducerClass(PageRankReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
