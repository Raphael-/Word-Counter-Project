import java.io.PrintStream;
import java.util.Comparator;

/*
 * Interface for a simple Map data structure that maps keys of type K
 * to values of type V
 */
public interface Map<K, V> 
{
	
	/*
	 * Inserts the given value associated with the specified key
	 */
	public void put(K key, V value);
	
	/*
	 * Removes the item that is associated with the specified key.
	 * Does nothing if not item is found
	 */
	public void remove(K key);
	
	/*
	 * Returns true if an item with the specified key is inserted in the map
	 */
	public boolean containsKey(K key);
	
	/*
	 * Returns the value associated with the specified key, or null if not found
	 */
	public V getValue(K key);
	
	/*
	 * Returns true in the map is empty
	 */
	public boolean isEmpty();
	
	/*
	 * Returns the number of items currently inserted
	 */
	public int size();
	
	/*
	 * Empties the data structure
	 */
	public void clear();
	
	/*
	 * Returns a list that contains all the items inserted in the map. Adjust this method
	 * so that it matches the list implementation you are using.
	 */
	LinkedList<V> values();

}
