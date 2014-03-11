import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/*
 * WordCounter implementation Methods are functioning according to the given
 * descriptions of the interface WordCounter
 */
public class BinaryTreeBasedWordCounter implements WordCounter 
{
    //stop words stored in a linked list
    private LinkedList<String> stopWords;
    //the binary tree containing the words
    public BinarySearchTree bst;

    /**
     * Constructor.
     */
    public BinaryTreeBasedWordCounter() 
    {
        stopWords = new LinkedList<String>();
        bst = new BinarySearchTree(new WordComparator());
    }

    @Override
    public void addStopWord(String word) 
    {
        stopWords.insertAtBack(word);
    }

    @Override
    public void removeStopWord(String word) 
    {
        if (stopWords.removeSelected(word) == null) 
            System.err.println("Invalid stop word specified for removal.");
    }

    @Override
    public void load(String path) throws IOException 
    {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("./" + path));
            String line;
            while ((line = in.readLine()) != null) 
            {
                line = line.replace("\"", "");
                line = line.replaceAll("[\\p{Punct}&&[^']]", ""); //replace all punctuation(except ') with a space
                line = line.replaceAll("\\s+", " "); //remove extra spaces and replace them with a single one.
                for (String s : line.split(" ")) //split the line into tokens, delimiter will be a space
                {
                    //if the token does not contain a number and is not a stopword, add it to the tree (in lowercase)
                    if (!s.matches(".*[0-9].*") && !stopWords.contains(s)) 
                    {
                        bst.insert(new WordFreq(s.toLowerCase()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    @Override
    public int getTotalWords() 
    {
        return bst.getSum();
    }

    @Override
    public int getDistinctWords() 
    {
        return bst.size();
    }

    @Override
    public int getFrequency(String word) 
    {
        WordFreq t = bst.search(new WordFreq(word));
        if (t == null) return 0;
        return t.getFreq();
    }

    @Override
    public String getMinimumFrequency() 
    {
        return "Minimum : " + bst.getMin().toString();
    }

    @Override
    public String getMaximumFrequency() 
    {
        return "Maximum : " + bst.getMax().toString();
    }

    @Override
    public double getMeanFrequency() 
    {
        return bst.calculateMean();
    }

    @Override
    public void printAlphabetically(PrintStream ps) 
    {
        ps.append(bst.toString());
        ps.flush();
    }

    @Override
    public void printByFrequency(PrintStream ps) 
    {
        //use a temporary BinarySearchTree with a different comparator to sort by frequency
        BinarySearchTree temp = new BinarySearchTree(new FreqComparator());
        while (bst.size() > 0) 
        {
            temp.insert(bst.removeRoot());
        }
        bst.clearStats();
        ps.append(temp.toString());
        ps.flush();
        //restore the tree to its original order
        while (temp.size() > 0) 
        {
            bst.insert(temp.removeRoot());
        }

    }
}
