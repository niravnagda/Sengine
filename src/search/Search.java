/**      
 * sendall.java Create on Dec 1, 2012     
 *      
 * Copyright (c) Dec 1, 2012 by AsattePress      
 *      
 * @author Ethan.Li</a>     
 * @version 1.0 
 *     
 */
package search;

import getkeywords.Result;
import getkeywords.StringMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import database.GrabData;
import database.SearchResult;
import database.SearchShift;

/**
 * @author Ethan.Li
 * 
 */
public class Search {

	public ArrayList<SearchResult> sendback(String searchstring) {
		Result keywords = StringMatcher.getKeyWords(searchstring);

		/*
		 * System.out.println("Key words"); ArrayList<ArrayList<String>> tmp =
		 * keywords.orWords; for(ArrayList<String> a : tmp){ for(String s: a){
		 * System.out.print(s); } System.out.println(); }
		 */
		SearchCircularShift scs = new SearchCircularShift(keywords);
		ArrayList<SearchShift> midResult = scs.getResult();

		List<SearchResult> searchresult = new ArrayList<SearchResult>();
		List<SearchResult> finalresult = new ArrayList<SearchResult>();
		GrabData temp = new GrabData();
		searchresult = temp.SearchResult();

		for (SearchResult sr : searchresult) {
			for (SearchShift ss : midResult) {
				System.out.println(ss.getIndex());
				if (sr.getIndex().equals(ss.getIndex())) {
					finalresult.add(sr);
				}
			}
		}
		HashSet<SearchResult> h = new HashSet<SearchResult>(finalresult);
		finalresult.clear();
		finalresult.addAll(h);
		return (ArrayList<SearchResult>) finalresult;

	}

	public static ArrayList<SearchResult> returnSort(
			ArrayList<SearchResult> result) {
		Map<String, SearchResult> map = new HashMap<String, SearchResult>();
		for (SearchResult sr : result) {
			map.put(sr.getUrl(), sr);
		}
		ArrayList<Entry<String, SearchResult>> infoIds = new ArrayList<Map.Entry<String, SearchResult>>(
				map.entrySet());

		Collections.sort(infoIds,
				new Comparator<Map.Entry<String, SearchResult>>() {
					public int compare(Map.Entry<String, SearchResult> o1,
							Map.Entry<String, SearchResult> o2) {
						return (o1.getKey()).toString().compareTo(o2.getKey());
					}
				});
		ArrayList<SearchResult> r = new ArrayList<SearchResult>();
		for (Entry<String, SearchResult> s : infoIds) {
			r.add(s.getValue());
		}
		return (ArrayList<SearchResult>) r;
	}
}
