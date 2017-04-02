import java.util.*;

public class Spider {
	
	private static final int MAX_PAGES_TO_SEARCH = 10;
	private Set<String> pagesVisited = new HashSet<>();
	//set contains unique entries and no duplicates
	//all pages visited will be unique
	private List<String> pagesToVist = new LinkedList<>();
	//all the collect all the URLs on that page and add them to end of list

	private String nextUrl(){
		String nextUrl;
		do{
			nextUrl = this.pagesToVist.remove(0);	
		}while(this.pagesToVist.contains(nextUrl));
		
		this.pagesVisited.add(nextUrl);
		
		return nextUrl;
	}
	
	
	public static void main(String[] args){
		
	}
	
}
