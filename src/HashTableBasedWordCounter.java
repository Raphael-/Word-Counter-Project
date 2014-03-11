
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/*
 * WordCounter implementation Methods are functioning according to the given
 * descriptions of the interface WordCounter
 */
public class HashTableBasedWordCounter implements WordCounter 
{
    //stop words stored in a linked list
    private LinkedList<String> stopWords;
    //the hash table containing the WordFreq objects
    private MyHashTable<String, WordFreq> ht;
    
    private int totalWords;
    private WordFreq min;
    private WordFreq max;

    /**
     * Constructor.
     */
    public HashTableBasedWordCounter(int size) 
    {
        stopWords = new LinkedList<String>();
        ht = new MyHashTable<String, WordFreq>(size);
        totalWords = 0;
        min = null;
        max = null;
    }

    @Override
    public void addStopWord(String word) 
    {
        stopWords.insertAtBack(word.toLowerCase());
    }

    @Override
    public void removeStopWord(String word) 
    {
        if (stopWords.removeSelected(word.toLowerCase()) == null) 
            System.err.println("Invalid stop word specified for removal.");
    }

    @Override
    public void load(String path) throws IOException 
    {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("./" + path));
            String line;
            
            while ((line = in.readLine()) != null) {
                line = line.replace("\"", "");//remove double quotes
                line = line.replaceAll("[\\p{Punct}&&[^']]", ""); //remove all punctuation(except ')
                line = line.replaceAll("\\s+", " "); //remove extra spaces and replace them with a single one
                
                for (String s : line.split(" ")) //split the line into tokens,delimiter will be a space
                {   
                    // if the token does not contain a number and is not a stopword, add it to the tree (in lowercase)
                    if (!s.matches(".*[0-9].*") && !stopWords.contains(s.toLowerCase())) 
                    {
                        //check if the key has been inserted in the hash table
                        if (!ht.containsKey(s.toLowerCase())) 
                        {
                            WordFreq temp = new WordFreq(s.toLowerCase());
                            ht.put(s.toLowerCase(), temp);
                            totalWords++;
                            if (min == null) min = temp;
                            else if (min.getFreq() > temp.getFreq()) min = temp;
                            if (max == null) max = temp;
                            else if (max.getFreq() < temp.getFreq()) max = temp;
                        } else 
                        {
                            //key already exists
                            WordFreq temp = ht.getValue(s.toLowerCase());
                            //increase word's frequency
                            temp.increase(); 
                            //add it to the table
                            ht.put(s.toLowerCase(), temp);
                            totalWords++;
                            if (max.getFreq() < temp.getFreq()) max = temp;
                            if (min.getFreq() > temp.getFreq()) min = temp;
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) in.close();
        }
    }

    @Override
    public int getTotalWords() 
    {
        return totalWords;
    }

    @Override
    public int getDistinctWords() 
    {
        return ht.size();
    }

    @Override
    public int getFrequency(String word) 
    {
        return ht.getValue(word) == null ? 0 : ht.getValue(word).getFreq();
    }

    @Override
    public String getMinimumFrequency() 
    {
        return "Minimum : " + min;
    }

    @Override
    public String getMaximumFrequency() 
    {
        return "Maximum : " + max;
    }

    @Override
    public double getMeanFrequency() 
    {
        return (float) totalWords / ht.size();
    }

    @Override
    public void printAlphabetically(PrintStream ps) 
    {
        //sorts the table utilizing the MergeSort method of LinkedList
        LinkedList<WordFreq> values = ht.values();
        values.MergeSort(values.getHead(), new WordComparator());
        values.print(ps);
    }

    @Override
    public void printByFrequency(PrintStream ps) 
    {
        //sorts the table utilizing the MergeSort method of LinkedList
        LinkedList<WordFreq> values = ht.values();
        values.MergeSort(values.getHead(), new FreqComparator());
        values.print(ps);
    }
}
