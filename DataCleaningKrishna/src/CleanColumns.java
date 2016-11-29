import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CleanColumns {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if (args.length != 1) {
			System.err.println("Usage: Columns <input path>");
			System.exit(-1);
		}

		Path input, output = null;
		FileSystem fs = null;

		Configuration wordConf = new Configuration();
		fs = FileSystem.get(wordConf);

		Job job = Job.getInstance(wordConf);
		job.setJarByClass(CleanColumns.class);
		job.setJobName("Important Columns");

		input = new Path(args[0]);
		output = new Path("important_columns_output");

		if (fs.exists(output))
			fs.delete(output, true);

		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);

		job.setMapperClass(CleanColumnsMapper.class);
		job.setReducerClass(CleanColumnsReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
