
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CleanColumnsReducer extends Reducer<Text, Text, Text, Text> {
	String ID, value;

	protected void setup(Context context) throws java.io.IOException, InterruptedException {
		ID = new String();
		value = new String();
	}

	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws java.io.IOException, InterruptedException {

		ID = key.toString();

		for (Text val : values) {
			value = val.toString();
		}

		context.write(new Text(ID), new Text(value));

	}
}
