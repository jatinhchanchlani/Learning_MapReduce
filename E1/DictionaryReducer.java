package Dictionary;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DictionaryReducer extends Reducer<Text,Text,Text,Text> {
   public void reduce(Text word, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
      // TODO iterate through values, parse, transform, and write to context
	  
	   
	   	StringBuilder frenchBuilder = new StringBuilder();
		StringBuilder italianBuilder = new StringBuilder();
		StringBuilder spanishBuilder = new StringBuilder();
		StringBuilder portBuilder = new StringBuilder();
		StringBuilder germanBuilder = new StringBuilder();
		StringBuilder finalAppender = new StringBuilder();	   
		for(Text t : values)
		{
			String val  = t.toString();
			if(val.contains("french:"))
			{
				frenchBuilder.append(val.substring(val.indexOf(":")+1)).append(", ");
			}
			else if(val.contains("spanish:"))
			{
				spanishBuilder.append(val.substring(val.indexOf(":")+1)).append(", ");
			}
			else if(val.contains("italian:"))
			{
				italianBuilder.append(val.substring(val.indexOf(":")+1)).append(", ");
			}
			else if(val.contains("portuguese:"))
			{
				portBuilder.append(val.substring(val.indexOf(":")+1)).append(", ");
			}
			else if(val.contains("german:"))
			{
				germanBuilder.append(val.substring(val.indexOf(":")+1)).append(", ");
			}

		}
		String temp = " N/A";
		if(frenchBuilder.length()>0)
		{
			temp = frenchBuilder.toString();
			temp = temp.substring(0,temp.length()-2);
		}
		finalAppender.append("french:").append(temp).append(" | ");

		temp = " N/A";
		if(germanBuilder.length()>0)
		{
			temp = germanBuilder.toString();
			temp = temp.substring(0,temp.length()-2);
		}
		finalAppender.append("german:").append(temp).append(" | ");

		temp = " N/A";
		if(italianBuilder.length()>0)
		{
			temp = italianBuilder.toString();
			temp = temp.substring(0,temp.length()-2);
		}
		finalAppender.append("italian:").append(temp).append(" | ");

		temp = " N/A";
		if(portBuilder.length()>0)
		{
			temp = portBuilder.toString();
			temp = temp.substring(0,temp.length()-2);
		}
		finalAppender.append("portuguese:").append(temp).append(" | ");

		temp = " N/A";
		if(spanishBuilder.length()>0)
		{
			temp = spanishBuilder.toString();
			temp = temp.substring(0,temp.length()-2);
		}
		finalAppender.append("spanish:").append(temp+ " ");


		context.write(word, new Text(finalAppender.toString()));
   }
}
