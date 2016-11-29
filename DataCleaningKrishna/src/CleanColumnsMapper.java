
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CleanColumnsMapper extends Mapper<LongWritable, Text, Text, Text> {
	long ID = 0;

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String line = value.toString();
		String[] lines = line.split("\n");

		for (String data : lines) {
			String[] token = data.split(",");
			ID++;
			System.out.println(ID);
			// specify the column indices for which we are significant
			context.write(new Text(String.valueOf(ID)), // Assigning ID to the
														// column
					new Text(token[0] + ", " + token[1] + ", " + token[2] + ", " + token[3] + ", " + token[4] + ", "
							+ token[5] + ", " + token[6] + ", " + token[7] + ", " + token[8] + ", " + token[9] + ", "
							+ token[10] + ", " + token[11] + ", " + token[12] + ", " + token[16] + ", " + token[17]));
		}
	}
}