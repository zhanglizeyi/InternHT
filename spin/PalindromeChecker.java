/*
 *Name: Meiyi He
 *Login: cs11fawq
 *PID: A91041817
 *Date: Nov.09.2015
 *File Name: PalindromChecker.java
 *This is a file to check palindrom phrase
 */
public class PalindromeChecker{
	

  /**
   *Method Name: filter
   *Purpose: filter out the phrase that user input
   *@param phrase takes in user's input string
   *@return String after filter out invalid characters, return the correct
   */
	public static String filter(String phrase){

		String validString = "0123456789abcdefghijklmnopqrstuvwxyz"
						 									        + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] valid = validString.toCharArray();
    //convert all valid string to char array

		char[] phraseArray = phrase.toString().toLowerCase().toCharArray();
    //convert all user input phrase to lower case and then char array

		StringBuilder resultPhrase = new StringBuilder();
    //create a new string after filter out special character

		for(char c : phraseArray){
			for(char a : valid){
				//use foreach loop to compare the valid string with input string

        if(c == a){
					resultPhrase.append(a);
          //if any of the input match valid string, then append
				}
			}
		}

		return resultPhrase.toString();
    //return the string after filtered
	}//end of filter method



  /**
   *Method Name: isPalindrome
   *Purpose:check palindrome by using 3 parameters
   *@param phrase the input string
   *@param low starting character to check
   *@param high ending character to check
   *@return boolean if palindrome, return true, else false
   */
	public static boolean isPalindrome(String phrase, int low, int high){
		
		return(high-low<1) || (phrase.charAt(low) == phrase.charAt(high) &&
			isPalindrome(phrase, low+1, high-1));
    //if less than 1 character, return true
    //or recursively call the method untill reach to the middle one
	}


  /**
   *Method Name: isPalindrome
   *Purpose: check the palindrome by using 1 parameter
   *@param phrase the input string
   *@return boolean, if palindrome then true, else false
   */
	public static boolean isPalindrome(String phrase){


		if(phrase.length()==0 || phrase.length()==1){
			//if length of string is 0 or 1, then true without checking
      return true;
		} 

		if(phrase.charAt(0) == phrase.charAt(phrase.length()-1)){
      //if the length longer than 1, check the first with the end

			return isPalindrome(phrase.substring(1, phrase.length()-1));
      //recursively checking the substring of the original by
      //moving one index from the start and moving one index from the end
      //until there is only 1 or 0 left
		}else{

			return false;
      //if not equal then return false
		}

	}//end of the method


}
