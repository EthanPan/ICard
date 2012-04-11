package ethan.cn;

import java.io.Serializable;

public class VocabularyCard implements Serializable{
	private String word;
	private String trans;
	private String phonetic;
	private int target;
	private int progress;
	private int times;
	
	//setter and getter
	public void setWord(String word) {
		this.word = word;
	}
	public String getWord() {
		return word;
	}
	public void setTrans(String trans) {
		this.trans = trans;
	}
	public String getTrans() {
		return trans;
	}
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}
	public String getPhonetic() {
		return phonetic;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public int getProgress() {
		return progress;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public int getTarget() {
		return target;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getTimes() {
		return times;
	}
   //
	
	public void addProgress() {
		this.progress++;
		
	}
	public void subProgress() {
		this.progress--;
		if(progress < 0)
			progress = 0;
	}
	public void addTimes()
	{
		this.times++;
	}

	
}
