
import java.io.PrintStream;

/*
 * Map implementation methods are functioning according to the given
 * descriptions of the interface
 */
public class MyHashTable<K, V> implements Map<K, V> 
{
    /*
     * Node class of the Hash Table Contains the node's value, key and pointer
     * to the next node
     */

    private class Node<A> 
    {

        private A val;
        private K key;
        private Node<A> next;

        public Node(K k, A v) 
        {
            key = k;
            val = v;
            next = null;
        }

        public Node() 
        {
            this(null, null);
        }

        public A getVal() 
        {
            return this.val;
        }

        public void setVal(A v) 
        {
            this.val = v;
        }
    }
    //the actual hash table
    private Node<V>[] data;
    private int size;
    //"elements" is the number of elements added so far
    private int elements;

    /**
     * Hash table constructor.
     */
    public MyHashTable(int size) 
    {
        this.size = size / 5;
        this.elements = 0;
        data = new Node[size];
    }

    @Override
    public void clear() 
    {
        for (int i = 0; i < size; i++) 
        {
            data[i] = null;
        }
    }

    @Override
    public LinkedList<V> values() 
    {
        LinkedList<V> list = new LinkedList<V>();
        Node<V> temp;
        for (int i = 0; i < data.length; i++) 
        {
            temp = data[i];
            //check for any collisions and add them to the list
            while (temp != null) 
            {
                list.insertAtFront(temp.getVal());
                temp = temp.next;
            }
        }
        return list;
    }

    /**
     * Generates the key for the Hash Table using the hashCode method
     * Returns the absolute value because it may be negative due to overflow.
     */
    private int hash(K key) 
    {
        return Math.abs(key.hashCode() % size);
    }

    @Override
     public void put(K key, V value) 
    {
        //throw new exception if any argument is null
        if (key == null || value == null) 
            throw new IllegalArgumentException();

        int hash = hash(key); // Generate the hash
        
        Node<V> t = data[hash]; // Go to the table position indicated by the key
        
        while (t != null) 
        {
            // Check if a value with the same key already exists in the table
            if (t.key.equals(key)) 
            {
                break;
            }
            t = t.next;
        }
        
        if (t != null) 
        {
            // Since table is not null, we have found the key
            // Update the key contents (at data table position indicated by t)
            t.setVal(value);
        } else 
        {
            // Since t == null, the key is not already in the table
            // Add a new node at the head of the corresponding list containing the new key and its value
            Node<V> newNode = new Node<V>();
            newNode.key = key;
            newNode.val = value;
            //set new list head
            newNode.next = data[hash];
            data[hash] = newNode; 
            elements++;  // Count the newly added key
        }
    }

    @Override
    public void remove(K key) 
    {
        int hash = hash(key); //Find the hash
        if (data[hash] == null) //Could not find value associated to that key
        {
            System.err.println("Invalid key " + key + " specified for removal operation.");
            return;
        }
        if (data[hash].key.equals(key)) //Key found at head
        {
            // If the key is the first node on the list, remove and replace it with the next element
            data[hash] = data[hash].next;
            elements--; // Update number of items in the table
            return; //removal complete
        }
        
        //The element to be removed is not at the head of the list
        //That's why we keep two Node references; previous and current
        Node<V> prev = data[hash];
        Node<V> curr = prev.next;

        while (curr != null && !curr.key.equals(key)) 
        {
            curr = curr.next;
            prev = curr;
        }

        if (curr != null) //If true, we found the key
        {
            prev.next = curr.next; //Remove curr Node
            elements--;  // Refresh elements' number
        }
    }

    @Override
    public boolean containsKey(K key) 
    {
        int hash = hash(key);  //Get the hash of key in the data table
        Node<V> t = data[hash];  //Get the reference to the head of the list of Nodes
        
        while (t != null) 
        {
            if (t.key.equals(key)) //Key found, return true
            {
                return true;
            }
            t = t.next; //Key not found at this iteration, proceed to next element
        }
        return false; //Key was not found, return false
    }

    @Override
    public V getValue(K key) 
    {
        int hash = hash(key);
        Node<V> t = data[hash];  // Node referring to the desired value
        
        // Search for the value and return it
        while (t != null) 
        {
            if (t.key.equals(key)) 
            {
                return t.getVal();
            }
            t = t.next;  // Move on to next node in the list
        }
        //Value not found. Return null to indicate that the key is not in the table (and obviously in the list)
        return null;
    }

    @Override
    public boolean isEmpty() 
    {
        return (elements == 0);
    }

    @Override
    public int size() 
    {
        return elements;
    }
}