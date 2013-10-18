/**      
 * search.java Create on Dec 1, 2012     
 *      
 * Copyright (c) Dec 1, 2012 by AsattePress      
 *      
 * @author Ethan.Li</a>     
 * @version 1.0 
 *     
 */
package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.SearchShift;
import database.GrabData;
import getkeywords.Result;

/**
 * @author Ethan.Li
 * 
 */
public class SearchCircularShift {
	public ArrayList<SearchShift> r;
	public Set<SearchShift> rr;
	public Result result;

	public SearchCircularShift(Result r) {
		this.result = r;
	}

	public ArrayList<SearchShift> getNotResult(Result result) {
		ArrayList<SearchShift> notr = new ArrayList<SearchShift>();

		List<SearchShift> searchshift = new ArrayList<SearchShift>();
		GrabData temp = new GrabData();
		searchshift = temp.SearchShift();

		for (String s : result.notWords) {
			for (int i = 0; i < searchshift.size(); i++) {
				if (check(s, searchshift.get(i).getDescription())) {
					notr.add(searchshift.get(i));
				}
			}
		}
		return notr;
	}

	public ArrayList<SearchShift> getOrResults(Result result) {
		r = new ArrayList<SearchShift>();
		rr = new HashSet<SearchShift>();

		List<SearchShift> searchshift = new ArrayList<SearchShift>();
		GrabData temp = new GrabData();
		searchshift = temp.SearchShift();

		if (result.orWords.size()!=0) {
			for (ArrayList<String> al : result.orWords) {
				ArrayList<SearchShift> tmp = new ArrayList<SearchShift>();
				for (int i = 0; i < searchshift.size(); i++) {
					if (check(al.get(0), searchshift.get(i).getDescription())) {
						tmp.add(searchshift.get(i));
					}
				}

				for (int i = 1; i < al.size(); i++) {
					Set<String> hash = new HashSet<String>();
					for (int j = 0; j < searchshift.size(); j++) {
						if (check(al.get(i), searchshift.get(j)
								.getDescription())) {
							hash.add(searchshift.get(j).getIndex());
						}
					}
					ArrayList<SearchShift> aa = new ArrayList<SearchShift>();
					for (SearchShift ss : tmp) {
						if (hash.contains((String) ss.getIndex())) {

							aa.add(ss);
						}
					}
					tmp = new ArrayList<SearchShift>();
					tmp.addAll(aa);
				}
				r.addAll(tmp);
			}
		}else{
			r.addAll(searchshift);
		}
		for (SearchShift ss : r) {
			if (!rr.contains(ss)) {
				rr.add(ss);
			}
		}
		
		r = new ArrayList<SearchShift>();
		for (SearchShift ss : rr) {
			r.add(ss);
		}
		return r;

	}

	public ArrayList<SearchShift> getResult() {
		ArrayList<ArrayList<String>> t = this.result.orWords;
		
		Set<String> hash = new HashSet<String>();
		ArrayList<SearchShift> not = getNotResult(this.result);
		
		ArrayList<SearchShift> or = getOrResults(this.result);
	
		for (SearchShift ss : not) {
			hash.add(ss.getIndex());
		}
		ArrayList<SearchShift> aa = new ArrayList<SearchShift>();
		
		for (SearchShift ss : or) {
			if (!hash.contains((String) ss.getIndex())) {
				aa.add(ss);
			}
		}
		return aa;
	}

	public boolean check(String a, String b) {
		
		String temp = " ";
		String s[] = b.split(temp);
		return a.equals(s[0]);
	}
}
