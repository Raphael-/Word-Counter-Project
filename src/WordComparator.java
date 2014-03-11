import java.util.Comparator;

/*
 * Comparator for the BinarySearchBasedWordCounter
 * Compares the WordFreq objects by their "word" Strings
 */
final class WordComparator implements Comparator<WordFreq> 
{
	public int compare(WordFreq a, WordFreq b) 
	{
		return a.getWord().compareTo(b.getWord());
	}

}
