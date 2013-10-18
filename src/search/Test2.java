/**      
* Test2.java Create on Dec 1, 2012     
*      
* Copyright (c) Dec 1, 2012 by AsattePress      
*      
* @author Ethan.Li</a>     
* @version 1.0 
*     
*/ 
package search;

import java.util.ArrayList;

import database.SearchResult;

/**
 * @author Ethan.Li
 *
 */
public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Search s = new Search();
		ArrayList<SearchResult> result = s.sendback("UT");
		for (SearchResult sr : result){
			System.out.println(sr.getUrl());
		}

	}

}
