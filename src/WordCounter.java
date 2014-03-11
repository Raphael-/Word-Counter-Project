import java.io.IOException;
import java.io.PrintStream;


public interface WordCounter{
	
	/*
	 * adds a word to the list of stop words
	 */
	public void addStopWord(String word);
		
	/*
	 * removes a word from the list of stop words
	 */
	public void removeStopWord(String word);
	
	/*
	 * loads the text file located in the specified path to the disk. 
	 * All words are in lower case, punctuation marks are removed from words and 
	 * stop words are ignored.
	 */
	public void load(String path) throws IOException;
	
	/*
	 * Returns the total number of words parsed by the WordCounter
	 * Stop words are not included
	 */
	public int getTotalWords();
	
	/*
	 * Returns the number of distinct words found in the text file
	 */
	public int getDistinctWords();
	
	/*
	 * Return the number of occurrences of the given word. 
	 * If the word is not found, it returns 0 
	 */
	public int getFrequency(String word);
	
	/*
	 * Returns the word with the minimum frequency
	 */
	public String getMinimumFrequency();
	
	/*
	 * Returns the word with the maximum frequency
	 */	
	public String getMaximumFrequency();
	
	/*
	 * Returns the average frequency of the words in the file
	 */
	public double getMeanFrequency();
	
	/*
	 * Prints the words in alphabetical order
	 */
	public void printAlphabetically(PrintStream ps);
	
	/*
	 * Prints the words sorted based on their frequency
	 */
	public void printByFrequency(PrintStream ps);

}
