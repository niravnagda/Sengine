package getkeywords;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringMatcher {

	public static ArrayList<String> remainStrings;
	public static ArrayList<ArrayList<String>> orStrings;
	public static ArrayList<String> notStrings;
	
	/*
	 * split NOT strings
	 * get remained Strings
	 * 
	 */
	private static ArrayList<String> splitNotString(String input){
		ArrayList<String> remain =
				new ArrayList<String>();
		String[] results = input.split("NOT");
		if(results.length>1){
			remain.add(results[0].trim());
			for(int i=1; i<results.length; ++i){
				String s = results[i].trim();
				Pattern p1 = Pattern.compile("AND");
				Pattern p2 = Pattern.compile(" OR ");
				Matcher m1 = p1.matcher(s);
				Matcher m2 = p2.matcher(s);
				if(!m1.find() && !m2.find()){
					StringTokenizer st = new StringTokenizer(s);
					while(st.hasMoreTokens()){
						notStrings.add(st.nextToken().trim());
					}
				}else{
					remain.add(s.trim());
				}
			}
		}else{
			remain.add(results[0].trim());
		}
		return remain;
	}
	
	
	//get simple OR strings
	private static ArrayList<String> splitOrString(ArrayList<String> strs){
		ArrayList<String> remain =
				new ArrayList<String>();
		ArrayList<String> orPart = 
				new ArrayList<String>();
		for(String s: strs){
			s = s.trim();
			String[] results = s.split("OR");
			for(String a: results){
				a = a.trim();
				Pattern p = Pattern.compile("AND");
				Matcher m = p.matcher(a);
				if(m.find()){
					remain.add(a.trim());
				}else{
					orPart.add(a.trim());
				}
			}
		}
		for(String s: orPart){
			s = s.trim();
			ArrayList<String> al =
					new ArrayList<String>();
			al.add(s.trim());
			orStrings.add(al);
		}
		return remain;
	}
	
	//split strings in AND part
	private static void splitAndString(ArrayList<String> strs){
		for(String s:strs){
			ArrayList<String> andStrings = 
					new ArrayList<String>();
			String[] result = s.split("AND");
			for(String a: result){
				andStrings.add(a.trim());
			}
			orStrings.add(andStrings);
		}
	}
	
	public static Result getKeyWords(String s){
		s = s.trim();
		remainStrings = new ArrayList<String>();
		orStrings = new ArrayList<ArrayList<String>>();
		notStrings = new ArrayList<String>();
		ArrayList<String> remain =
				new ArrayList<String>();
		remain = splitNotString(s);
		remain = splitOrString(remain);
		splitAndString(remain);
		
		Result result = new Result();
		result.notWords = notStrings;
		result.orWords = orStrings;
		
		return result;
	}
}
