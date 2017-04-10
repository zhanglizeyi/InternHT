import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;


public class webCrawler{

	private static final String fileName = "result.txt";

	public static void main(String[] args){

		//Time Out Connection after 500 miliseconds
		System.setProperty("sun.net.client.defaultConnectTimeout", "500");
		System.setProperty("sun.net.client.defaultReadTimeout", "1000");

		//initial web page
		String s = args[0];

		//list of web pages to be examined
		Queue<String> q = new LinkedList<String>();
		q.add(s);

		//set of examined web pages
		List<String> marked = new LinkedList<String>();
		marked.add(s);

		//read
		FileInputStream fis = null;

		//breadth first search crawl of web
		while(!q.isEmpty()){
			String str = q.poll();
			System.out.println(str);
			
			try{
				//file to write
				FileWriter fw = new FileWriter(fileName);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(str);
			}catch(IOException e){
				System.out.println("[could not open "+ str +" ]");
				continue;
			}

			try{
				fis = new FileInputStream(fileName);
			}catch(FileNotFoundException fe){
				System.out.println("File not found: " + fe);
			}
			InputStreamReader input = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(input);
			String data;
			String res = new String();
			try{
				while((data = br.readLine()) != null){
					res = res.concat(data+" ");
					System.out.println(res);
				}
			}catch(IOException e){
				System.out.println("IOException: "+e);
			}

		/*
			Find links of the form: http://xxx.yyy.zzz
			\\w+ for one or more alpha-numeric characters
			\\. for dot
			could take first two statments out of loop
		*/
			String reg = "http://(\\w+\\.)+(\\w+)";
			Pattern pat = Pattern.compile(reg);

			Matcher matcher = pat.matcher(res);

			//find and print all matches

			while(matcher.find()){
				String w = matcher.group();
				if(!marked.contains(w)){
					q.add(w);
					marked.add(w);
				}
			}
		}
	}
}