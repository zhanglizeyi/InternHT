//add package share with other class 

import java.util.*;

public class Spider {
	
	private static final int MAX_PAGES_TO_SEARCH = 10;
	private Set<String> pagesVisited = new HashSet<>();
	//set contains unique entries and no duplicates
	//all pages visited will be unique
	private List<String> pagesToVist = new LinkedList<>();
	//all the collect all the URLs on that page and add them to end of list

	/** (2) 
	 * return to the next URL to visit
	 * the method won't return a URL that has already been visited
	 * 
	 * @return
	 */
	private String nextUrl(){
		String nextUrl;
		do{
			nextUrl = this.pagesToVist.remove(0);	
		}while(this.pagesToVist.contains(nextUrl));
		
		this.pagesVisited.add(nextUrl);
		
		return nextUrl;
	}
	
	/*
	 * main launching point for the spider functionality
	 * 
	 * Internally creates spider leg object(s)
	 * make an http request and parse the response(web page)
	 *  
	 *  @param url
	 *  the starting point of the spider
	 *  
	 *  @param searchWord
	 *  	the word or string that you are searching for
	 */
	public void search(String url, String serachWord){
		while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH){
			
			String currentUrl;
			SpiderLeg leg = new SpiderLeg();
			
			if(this.pagesToVist.isEmpty()){
				currentUrl = url;
				this.pagesVisited.add(url);
			}else{
				currentUrl = this.nextUrl();
			}
			leg.crawl(currentUrl); //Lots of stuff happening here.
			
			boolean success = leg.serachForWord("AI");
			
			if(success){
				System.out.println(String.format("**Success** Word %s found at %s", "AI", currentUrl));
				break;
			}
			this.pagesToVist.addAll(leg.getLinks());
		}
		
		System.out.println("\n **Done** Visited " + this.pagesVisited.size() + "web page(s)");
	}
	
	
	public static void main(String[] args){
		
	}
	
}
