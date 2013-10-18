package getkeywords;
import java.util.ArrayList;


public class Test1 {

	public static void main(String[] args){
		String s1 = "a          AND c ORbNOTdNOTe";
		String s2 = "NOTa";
		String s3 = "";
		
		Result r = StringMatcher.getKeyWords(s1 );
		if(r.orWords.size()==0){
			System.out.println("orwords size == 0");
		}else{
			print(r);
		}
	
	}

	private static void print(Result r) {
		System.out.println("OR words");
		for(ArrayList<String> al: r.orWords){
			for(String a: al){
				System.out.print(a+ " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("NOT words:");
		for(String s: r.notWords){
			System.out.println(s+" ");
		}
	}
}
