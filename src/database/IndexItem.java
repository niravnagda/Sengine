package database;
/*
 * Java beans for shifted lines
 */

public class IndexItem{
	
	private String sentence;
	private int index;
	private int offset;
	
	IndexItem() {
		this.setSentence(null);
		this.setIndex(0);
		this.setOffset(0);
	}
	
	IndexItem(String sen, int ind, int off) {
		this.setSentence(sen);
		this.setIndex(ind);
		this.setOffset(off);
	}
	
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
