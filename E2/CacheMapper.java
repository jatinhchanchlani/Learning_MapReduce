package Dictionary;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;



public class CacheMapper  extends Mapper<LongWritable, Text, Text, Text> {
	String fileName=null, language=null;
	public Map<String, String> latinMap = new HashMap<String, String>();
	private BufferedReader brReader;
	private ArrayList<String> speechList = new ArrayList<String>();

	public void setup(Context context) throws IOException, InterruptedException{
		// TODO: determine the name of the additional language based on the file name    	  
		// TODO: OPTIONAL: depends on your implementation -- create a HashMap of translations (word, part of speech, translations) from output of exercise 1

		speechList.add("noun");
		speechList.add("verb");
		speechList.add("adverb");
		speechList.add("adjective");
		speechList.add("preposition");
		speechList.add("conjunction");
		speechList.add("pronoun");


		@SuppressWarnings("deprecation")
		Path[] cacheFilesLocal = DistributedCache.getLocalCacheFiles(context
				.getConfiguration());


		for (Path eachPath : cacheFilesLocal) {

			language = eachPath.getName().toString().trim().split("\\.")[0];
			loadTranslationHashMap(eachPath, context);

		}
	}
	private void loadTranslationHashMap(Path filePath, Context context) throws IOException {

		String strLineRead = "";

		try {
			brReader = new BufferedReader(new FileReader(filePath.toString()));

			// Read each line, split and load to HashMap
			while ((strLineRead = brReader.readLine()) != null) {


				String [] line = strLineRead.split("\\t");
				String recordKey = line[0];
				

				if(recordKey.startsWith("#"))
				{
					continue;
				}
				
				String translations = line[1];
				int i = translations.indexOf('[');
				int j = translations.indexOf(']');

				if(i<0)
				{
					continue;
				}
				if(j!=translations.length()-1)
				{
					continue;
				}
				if(!speechList.contains(translations.substring(i+1, j).toLowerCase()))
				{
					continue;
				}

				recordKey = recordKey +": "+ 
						translations.substring(i,translations.length());
				
				translations = translations.replace(';', ',');

				translations = translations.replace(";", ",");
				translations = translations.replace(", ", ",");
				translations = translations.replace(",", ", ");
		
				translations = " "+language +": " + translations;
		
				if(translations.indexOf('[')>0)
				{
					translations = translations.substring(0,translations.indexOf('['));
				}

				latinMap.put(recordKey, translations);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (brReader != null) {
				brReader.close();

			}

		}

	}
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// TODO: perform a map-side join between the word/part-of-speech from exercise 1 and the word/part-of-speech from the distributed cache file

		String mapperLine = value.toString();
		if (mapperLine.length() > 0)
		{
			String[] temp = mapperLine.split("\\t");
			String word = temp[0];
			String translations = temp[1]; 
			if(latinMap.containsKey(word))
			{
				translations = translations + "|" + latinMap.get(word);
			}
			else
			{
				translations = translations + "| " + "latin: N/A";
			}
			context.write(new Text(word), new Text(translations));

		}

		
		
		// TODO: where there is a match from above, add language:translation to the list of translations in the existing record (if no match, add language:N/A
	}

}
