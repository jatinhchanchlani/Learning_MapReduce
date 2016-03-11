package Dictionary;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class DictionaryMapper  extends Mapper<Text, Text, Text, Text> {
      // TODO define class variables for translation, language, and file name

	  private String language="";
	  private ArrayList<String> speechList = new ArrayList<String>();
      public void setup(Context context) {    	   
      // TODO determine the language of the current file by looking at it's name
		FileSplit fileSplit = (FileSplit)context.getInputSplit();
		String filename = fileSplit.getPath().getName();
		language = filename.split("\\.")[0];
		
		speechList.add("noun");
		speechList.add("verb");
		speechList.add("adverb");
		speechList.add("adjective");
		speechList.add("preposition");
		speechList.add("conjunction");
		speechList.add("pronoun");
		
      }

      public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
		// TODO instantiate a tokenizer based on a regex for the file
		String translations = value.toString();
		String recordKey = key.toString();
		
		if(recordKey.startsWith("#"))
		{
			return;
		}
		int i = translations.indexOf('[');
		int j = translations.indexOf(']');

		if(i<0)
		{
			return;
		}
		if(j!=translations.length()-1)
		{
			return;
		}
		if(!speechList.contains(translations.substring(i+1, j).toLowerCase()))
		{
			return;
		}
		recordKey = recordKey +": " + 
							translations.substring(i,translations.length());

		translations = translations.replace(";", ",");
		translations = translations.replace(", ", ",");
		translations = translations.replace(",", ", ");
		
		translations = language +": " + translations;
		
		if(translations.indexOf('[')>0)
		{
			translations = translations.substring(0,translations.indexOf('['));
		}

		context.write(new Text(recordKey), new Text(translations));


	  }
		
		// iterate through the tokens of the line, parse, transform and write to context
      
}
