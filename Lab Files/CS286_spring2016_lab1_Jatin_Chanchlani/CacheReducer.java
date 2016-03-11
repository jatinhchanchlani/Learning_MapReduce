package Dictionary;
import java.io.IOException;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Reducer;


public class CacheReducer extends Reducer<Text,Text,Text,Text> {
      public void reduce(Text word, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
	// TODO: add appropriate translations to the existing word/part-of-speech translations
	
		StringBuilder sb = new StringBuilder();

		for( Text t: values)
		{
			sb.append(t.toString());
		}
		context.write(word, new Text(sb.toString()));

      }

   }

