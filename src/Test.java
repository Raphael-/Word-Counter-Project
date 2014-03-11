import java.io.IOException;


public class Test {

	public static void main(String args[]) throws IOException
	{
		System.out.println("=====BinarySearchTree Test====");
		BinaryTreeBasedWordCounter b=new BinaryTreeBasedWordCounter();
		b.load("text.txt");
		System.out.println("==========Alphabetically===============");
		b.printAlphabetically(System.out);
		System.out.println("============By Frequency=============");
		b.printByFrequency(System.out);
		System.out.println("Mean Freq : "+b.getMeanFrequency());
		System.out.println("Maximum : "+b.getMaximumFrequency());
		System.out.println("Minimum : "+b.getMinimumFrequency());
		System.out.println("Total : "+b.getTotalWords());
		System.out.println("Distinct : "+b.getDistinctWords());
		System.out.println("=====HashTable Test====");
		HashTableBasedWordCounter hb=new HashTableBasedWordCounter(200);
		hb.load("text.txt");
		System.out.println("==========Alphabetically===============");
		b.printAlphabetically(System.out);
		System.out.println("============By Frequency=============");
		hb.printByFrequency(System.out);
		System.out.println("Mean Freq : "+hb.getMeanFrequency());
		System.out.println("Maximum : "+hb.getMaximumFrequency());
		System.out.println("Minimum : "+hb.getMinimumFrequency());
		System.out.println("Total : "+hb.getTotalWords());
		System.out.println("Distinct : "+hb.getDistinctWords());
	}
}
