import java.util.*;

public abstract class craw {
	
	public abstract void crawl(String nextURL);
	
	public abstract boolean searchForWord(String word);
	
	public abstract List<String> getLinks();
}
