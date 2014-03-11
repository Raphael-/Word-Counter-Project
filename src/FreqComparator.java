import java.util.Comparator;

/*
 * Comparator for the BinarySearchBasedWordCounter
 * Compares the WordFreq objects by their frequencies
 * Even if the frequencies are equal, the return value is -1 (duplicates are allowed in the tree) 
 */
final class FreqComparator implements Comparator<WordFreq>
{
	public int compare(WordFreq a, WordFreq b) 
	{
		if(a.getFreq()<=b.getFreq())
			return -1;
		
		else
			return 1;
	}

}
