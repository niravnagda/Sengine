package database;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SortLines{

	public static void sort(List<IndexItem> list) {
		// TODO Auto-generated method stub
		Collections.sort(list, new LinesConparator());
	}

}

//Implements of interface Comparator
class LinesConparator implements Comparator<IndexItem> {
	
	@Override
	public int compare(IndexItem o1, IndexItem o2)
	{
		// TODO Auto-generated method stub
		String s1 = o1.getSentence(), s2 = o2.getSentence();
		
		for(int i = 0;i != s1.length() && i != s2.length();i++)
		{
			//transfer char to askii
			int ch1 = s1.charAt(i), ch2 = s2.charAt(i);
			
			//Gaurantee every char is lower case. they are comparable
			if(ch1 >= 'A' && ch1 <= 'Z')
			{
				ch1 += 32;
			}
			
			if(ch2 >= 'A' && ch2 <= 'Z')
			{
				ch2 += 32;
			}
			
			//compare every char
			if(ch1 != ch2)
			{
				return ch1 - ch2;
			}
			
		}
		
		//return the one longer
		if(s1.length() != s2.length())
		{
			return s1.length() - s2.length();
		}
		
		return 0;
	}
				

}