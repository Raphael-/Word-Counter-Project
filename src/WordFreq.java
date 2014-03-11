/*
 * WordFreq objects contain the word itself and its frequency
 */
public class WordFreq 
{
	private int freq;
	private String word;
	
        /**
         * Two constructors.
         */
	public WordFreq(String word)
	{
		this(word,1);
	}
	
	public WordFreq(String word,int freq)
	{
		this.word=word;
		this.freq=freq;
	}
	
        /**
         * get methods.
         */
	public String getWord()
	{
		return this.word;
	}
	
	public int getFreq()
	{
		return this.freq;
	}
	
	public void increase()
	{
		this.freq++;
	}
	
	public String toString()
	{
		return "Word : "+this.word+" Frequency : "+this.freq;
	}
	
}
