package getkeywords;
import java.util.ArrayList;

public class Result {
	public ArrayList<String> notWords;
	public ArrayList<ArrayList<String>> orWords;
	public Result(){
		notWords = new ArrayList<String>();
		orWords = new ArrayList<ArrayList<String>>();
	}
}
