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

public class PageRankIterative {

	public static void main(String[] args) throws Exception {
		int counter = 1;
		Configuration pageRankConf = null;
		Job job = null;
		Path input, output = null;
		FileSystem fs = null;

		while (counter <= 3) {
			pageRankConf = new Configuration();
			job = Job.getInstance(pageRankConf);
			fs = FileSystem.get(pageRankConf);

			job.setJarByClass(PageRankIterative.class);
			job.setJobName("Page Rank Iterative - " + counter);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			job.setMapperClass(PageRankIterativeMapper.class);
			job.setReducerClass(PageRankIterativeReducer.class);

			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);

			input = new Path("class3/input-" + (counter - 1) + "/");
			output = new Path("class3/input-" + counter + "/");

			if (fs.exists(output))
				fs.delete(output, true);

			FileInputFormat.addInputPath(job, input);
			FileOutputFormat.setOutputPath(job, output);

			job.waitForCompletion(true);

			counter++;
		}

		System.exit(0);
	}
}
