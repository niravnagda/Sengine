package database;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class IndexBuilder {
	/*
	 * arguments
	 */
	
	
	private static String line;      //store a line to process
	private static int ct;           //characters we already read from the file
	private static int strlength;    //length of the original String "line"
	private static int offset;       //the index of the character in the String "line"   
	private static boolean blank;    //whether the last character in line is blank or not
	
	
	/*
	 * public methods
	 */
	
	public static List<IndexItem> execute(String instring)  //build the indices from a String
	{
		ct = offset = 0;
		blank = true;

		List<IndexItem> shiftedLines=new ArrayList<IndexItem>();
		IndexItem temp;
			
		int beginix = ct;
		//read next sentence
		while(getNextSentence(instring) != -1)
		{
			
			strlength = line.length();
			line = line + " " + line;
			
			String shiftstr;
			//reconstruct string with its properties,current string,begin index, offset
			while((shiftstr = getNextShifString()).equals("") == false)
			{
				//record index offser and shifter to an object
				temp = new IndexItem(shiftstr, beginix, offset);
				shiftedLines.add(temp);
			}
			
			beginix = ct;
			offset = 0;
			blank = true;
		}
		
		return shiftedLines;	
	}
	
	public static List<IndexItem> execute(File file)  //build the indices from a file
	{
		ct = offset = 0;
		blank = true;
		
		BufferedInputStream in;
		List<IndexItem> shiftedLines = new ArrayList<IndexItem>();
		IndexItem temp;
		
		try 
		{
			in = new BufferedInputStream(new FileInputStream(file));
			
			int beginix = ct;
			//read sentence which is not blank
			while(getNextSentence(in) != -1)
			{
				strlength = line.length();
				line = line + " " + line;
				
				String shiftstr;
				while((shiftstr = getNextShifString()).equals("") == false)
				{
//					System.out.println(shiftstr + "\t\tbeginix: " + beginix + "\toffset: " + offset);
					temp = new IndexItem(shiftstr, beginix, offset);
					shiftedLines.add(temp);
				}
				
				beginix = ct;
				offset = 0;
				blank = true;
				
//				System.out.println('\n');
			}
			
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("Error: file input/output.");
			}
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Error: can not open the file.");
		}
		
		return shiftedLines;
		
	}
	
	/*
	 * private method
	 */
	
	private static int getNextSentence(String instring)  //read one sentence from the "instring"
	{
		line = "";
		char ch;
		int r = 0;
		//read  sentence 
		
		while(ct < instring.length() && (ch = instring.charAt(ct)) != '\n')
		{
			if(ch != '\r')
			{
				line += ch;
			}
			ct++;
		}
		
	    ct++;
	    
	    if(line.equals("") == true)
	    {
	    	r = -1;
	    }
	    
	    return r;
	}

	private static int getNextSentence(BufferedInputStream in)  //read one sentence from the input stream "in"
 	{
		line = "";
		int ch;
		int r = 0;
	    try 
	    {
	    	//record offset for each sentence
			while((ch = in.read()) != '\n' && ch != -1)
			{
				if(ch != '\r')
				{
					line += (char)ch;
				}
				ct++;
			}
		} 
	    catch (IOException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ct++;
	    
	    if(line.equals("") == true)
	    {
	    	r = -1;
	    }
	    
	    return r;
	}
	//restructure,get next sentence.
	private static String getNextShifString() 
	{
		for(;offset < strlength;offset++)
		{
			//record offset for each word
			if(line.charAt(offset) != ' ')
			{
				//tell whether this char is the first letter
				if(blank == true)
				{
					blank = false;
					return line.substring(offset, offset + strlength);
				}
			}
			else
			{
				blank = true;
			}
		}
		
		return "";
	}
	
}
