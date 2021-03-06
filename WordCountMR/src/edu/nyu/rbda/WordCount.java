package edu.nyu.rbda;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if (args.length != 2) {
			System.err.println("Usage: CountWords <input path> <output path>");
			System.exit(-1);
		}

		String searchStrings[] = { "hackathon", "Dec", "Chicago", "Java" };

		Configuration wordConf = new Configuration();
		wordConf.setStrings("wordsToSearch", searchStrings);

		Job job = Job.getInstance(wordConf);
		job.setJarByClass(WordCount.class);
		job.setJobName("Count Words");

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}
