package Autofill;

import java.util.ArrayList;


import database.GrabData;
import database.SearchShift;

public class Cyberminer
{
	/* the array list to store the indices, the indices have
	 * two attributes: shift string and the index of the descriptor
	 * generating the shift string.
	 */
	private static ArrayList< SearchShift > shiftinfo;

	/* use at most 2 lookahead words to construct hint string */ 
	private static int prefixlookahead = 2;
	
	/* use 0 lookahead word to construct hint string */
	private static int wordlookahead = 0;
	
	//get at most 5 hint strings
	private static int maxhintcount = 5;
	
	
	
	/* this is only a method for testing.
	 * read the shift indices from a file into
	 * the array list "shiftinfo"
	 */
/*	public static boolean readIndex(File indexfile)
	{
		BufferedInputStream in;
		IndexItem temp;
		ArrayList< IndexItem > temparray = new ArrayList< IndexItem >();
		
		try 
		{
			in = new BufferedInputStream(new FileInputStream(indexfile));
			Scanner filescanner = new Scanner(in);
			
			int index;
			String shift;
			
			while(filescanner.hasNextLine())
			{
				index = Integer.valueOf(filescanner.nextLine()).intValue();
				shift = filescanner.nextLine();
				
				temp = new IndexItem(shift, index);
				
				temparray.add(temp);
			}
	
			shiftinfo = temparray;
			
			filescanner.close();
			
			try
			{
				in.close();
				return true;
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
//					e.printStackTrace();
				System.out.println("Error: file input/output.");
				return false;
			}
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Error: can not open the file.");
			return false;
		}
	}*/
	

	/* initialize the "shiftinfo" array, get all the shift indices
	 * from the database into the array, this method should be invoked
	 * before using the method "getHintList" and only be invoked once(the data
	 * stored in the "shiftinfo" is considered to be valid during the search engine's
	 * lifetime).
	 */
	public static ArrayList<SearchShift> initShiftInfo()
	{
		GrabData grabdata = new GrabData();
		
		shiftinfo = (ArrayList<SearchShift>) grabdata.SearchShift();
		return shiftinfo;
	}
	
	
	
	/* methods that are related to autofilling */
	
	
	/* binary search the index of the first shift string that is greater than
	 * the user input string in the array "shiftinfo".
	 * Return the array index, or -1 if all shift strings are smaller than
	 * or equal to the user input.
	 */
	private static int getStartArrayIndex(String userinput)
	{
		int l = 0, r = shiftinfo.size() - 1, mid;
		String shift;
		SearchShift item;
		
		while(l < r)
		{
			mid = (l + r) / 2;
			
			item = shiftinfo.get(mid);
			shift = item.getDescription();
			
			if(userinput.compareTo(shift) > 0)
			{
				l = mid + 1;
			}
			else
			{
				r = mid;
			}
		}
		
		item = shiftinfo.get(l);
		shift = item.getDescription();
		if(userinput.compareTo(shift) > 0)
		{
			return -1;
		}
		return l;
	}
	
	/* get the hint string from the shift string,
	 * the prefix length is the length of the user input string
	 */
	private static String getHintString(String shift, int prefixlength, int lookaheadcount)
	{
		String hintstring = shift.substring(0, prefixlength);
		
		int ct = 0;
		char ch;
		for(int i = prefixlength;shift.charAt(i) != '$';i++)
		{
			ch = shift.charAt(i);
			if(ch == ' ')
			{
				ct++;
				if(ct > lookaheadcount)
				{
					break;
				}
			}
			
			hintstring += ch;
		}
		
		return hintstring;
	}
	
	/* check if the user input is the prefix of the shift string.
	 * Return value: true, it is the prefix; false, it is not.
	 */
	private static boolean checkIfPrefix(String userinput, String shift)
	{
		int startindex = shift.indexOf(userinput);
		if(startindex != 0)
		{
			return false;
		}
		return true;
	}
	
	/* check if the user input is an expression
	 * with "AND"/"OR"/"NOT" expressions.
	 * Return value: true, it is an expression; false, it is not.
	 */
	private static boolean checkIfExpression(String userinput)
	{
		if(userinput.indexOf("AND ") == 0 ||
				userinput.indexOf("OR ") == 0 ||
				userinput.indexOf("NOT ") == 0)
		{
			return true;
		}
		
		if(userinput.indexOf(" AND ") != -1 ||
				userinput.indexOf(" OR ") != -1 ||
				userinput.indexOf(" NOT ") != -1)
		{
			return true;
		}
		
		return false;
	}
	
	/* check if a hint string is already in the arraylist "hintlist".
	 * Return value: true, it is in the list; false, it is not.
	 */
	private static boolean checkIfHintExist(String hintstring, ArrayList< String > hintlist)
	{
		String liststring;
		for(int i = 0;i != hintlist.size();i++)
		{
			liststring = hintlist.get(i);
			if(hintstring.equals(liststring) == true)
			{
				return true;
			}
		}
		return false;
	}
	
	/* try to calculate the hint list by the input string, which is
	 * the original user input string or the last word of the
	 * original user input string.
	 * return the list. If we can't find any hints, return an empty list.
	 */
	private static ArrayList< String > tryToGetHintListFrom(String input, int lookaheadcount)
	{
		ArrayList< String > hintlist = new ArrayList< String >();
		
		int startindex = getStartArrayIndex(input);
		int hintct = 0, prefixlength = input.length();
		int i = startindex;
		String shift, hintstring;
		
		while(i != -1 && hintct < maxhintcount)
		{
			shift = shiftinfo.get(i++).getDescription();
			
			if(checkIfPrefix(input, shift) == false)
			{
				break;
			}
			
			hintstring = getHintString(shift, prefixlength, lookaheadcount);
			
			if(checkIfHintExist(hintstring, hintlist) == false)
			{
				hintlist.add(hintstring);
				hintct++;
			}
		}
		
		return hintlist;
		
	}
	
	/* return the begin index of the last word in the user input string
	 * if the last character is not a space, otherwise, return -1.
	 */
	private static int getLastUserInputWordBegin(String userinput)
	{
		if(userinput.charAt(userinput.length() - 1) == ' ')
		{
			return -1;
		}
		
		int beginix = 0;
		for(int i = userinput.length() - 1;i >= 0;i--)
		{
			if(userinput.charAt(i) == ' ')
			{
				beginix = i + 1;
				break;
			}
		}
		return beginix;
	}
	
	/* Process the user input to get a formatted user input,
	 * first we obtain all the words in the user input and then
	 * generate a formatted input with only one space between each
	 * two words and no leading spaces.
	 * return the formatted input or an empty string when there are
	 * only spaces in the user input.
	 */
	private static String getFormattedUserinput(String userinput)
	{
		int startix, i;
		for(i = 0;i != userinput.length();i++)
		{
			if(userinput.charAt(i) != ' ')
			{
				break;
			}
		}
		startix = i;
		
		if(startix == userinput.length())
		{
			return "";
		}
		
		userinput = userinput.substring(startix);
		
		String[] strarray = userinput.split(" +"); //split the user input
		
		String formatinput = strarray[0];
		for(i = 1;i != strarray.length;i++)
		{
			formatinput += " " + strarray[i];
		}
		
		return formatinput;
	}
	
	/* calculate the hint list by the user input string.
	 * return the list. If the input is an expression or we
	 * cannot find any hints according to the entire user input,
	 * we only try to find hints for the last word of the string;
	 * if we can't find any hints, return an empty list.
	 */
	public static ArrayList< String > getHintList(String userinput)
	{
		ArrayList< String > hintlist = new ArrayList< String >();

		if(userinput.length() == 0)
		{
			return hintlist;
		}
		
		String formatinput = getFormattedUserinput(userinput);
		
		if(formatinput.length() == 0)
		{
			return hintlist;
		}
		
		if(checkIfExpression(formatinput) == false)
		{
			hintlist = tryToGetHintListFrom(formatinput, prefixlookahead);
			
			if(hintlist.size() != 0)
			{
				return hintlist;
			}
		}
		
		int beginix = getLastUserInputWordBegin(formatinput);
		String lastword;
		
		if(beginix == -1 || beginix == 0)
		{
			return hintlist;
		}
		
		lastword = formatinput.substring(beginix);
		
		hintlist = tryToGetHintListFrom(lastword, wordlookahead);
		
		String prefixstring = formatinput.substring(0, beginix);
		
		for(int i = 0;i != hintlist.size();i++)
		{
			String hintstring = hintlist.get(i);
			hintstring = prefixstring + hintstring;
			
			hintlist.remove(i);
			hintlist.add(i, hintstring);
		}
		
		return hintlist;
	}
	
	/* main */
/*	public static void main(String args[])
	{
		Scanner scanner = new Scanner(System.in);
		
		String indexfilename = "";
		
		System.out.print("Please enter name of the shift index file: ");
		indexfilename = scanner.nextLine();
		
		if(Cyberminer.readIndex(new File(indexfilename)) == false)
		{
			scanner.close();
			return;
		}
		
		System.out.println();
		
		SearchShift item;
		for(int i = 0;i != shiftinfo.size();i++)
		{
			item = shiftinfo.get(i);
			System.out.println(item.getIndex());
			System.out.println(item.getDescription());
			System.out.println();
		}
		
		System.out.println();
		
		String userinput;
		ArrayList< String > hintlist;
		int turn = 10;
		for(int ix = 0;ix != turn;ix++)
		{
			System.out.print("turn " + ix + "\n\n");
			
			System.out.println("Please enter the user input:");
			userinput = scanner.nextLine();
			
			hintlist = getHintList(userinput);
			System.out.print("\nThe hint strings are:\n");
			if(hintlist.size() == 0)
			{
				System.out.println("No hint strings");
			}
			else
			{
				for(int i = 0;i != hintlist.size();i++)
				{
					System.out.println(hintlist.get(i));
				}
			}
			System.out.println();
		}
		
		scanner.close();
	}*/
}