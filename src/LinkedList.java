import java.io.PrintStream;
import java.util.Comparator;

public class LinkedList<T> 
{
	// class to represent one node in a list
	private class Node<T> 
        {
		// package access members; List can access these directly
		T data;
		Node<T> nextNode;

		/**
		 * constructor creates a ListNode that refers to data
		 */
		Node(T data) 
                {
			this(data, null);
		}

		/**
		 * constructor creates ListNode that refers to data and to next ListNode
		 */
		Node(T data, Node<T> node) 
                {
			this.data = data;
			nextNode = node;
		}
		/**
		 * No arguments constructor
		 */
		private Node()
		{
			this(null);
		}
		/**
		 * return reference to data
		 * 
		 * @return
		 */
		T getObject() {
			return data;
		}

		/**
		 * return reference to next node in list
		 */
		Node<T> getNext() {
			return nextNode;
		}

	} // end inner class Node

	private Node<T> firstNode;
	private Node<T> lastNode;
	private String name; // string like "list" used in printing
	private int size;

	/**
	 * constructor creates empty List with "listName" as the name
	 */
	public LinkedList() {
		this("list");
	}

	/*
	 * constructor creates an empty List with a name
	 */
	public LinkedList(String listName) {
		name = listName;
		firstNode = lastNode = null;
		size = 0;
	}
	
	public Node<T> getHead()
	{
		return this.firstNode;
	}
	
	public T getFirst() 
	{
		return firstNode.data;
	}

	/**
	 * Searches the linked list to find if the given value exists.
	 */
	public boolean contains(T val) 
        {
		Node<T> n = firstNode;
		while (n != null) 
                {
			if (val.equals(n.getObject()))
				return true;
			n = n.nextNode;
		}
		return false;
	}

	/**
	 * Inserts a new item at the front of the list
	 */
	public void insertAtFront(T insertItem) {
		Node<T> node = new Node<T>(insertItem);

		if (isEmpty()) // firstNode and lastNode refer to same object
			firstNode = lastNode = node;
		else { // firstNode refers to new node
			node.nextNode = firstNode;
			firstNode = node;
			// you can replace the two previous lines with this line: firstNode
			// = new ListNode( insertItem, firstNode );
		}
		size++;
	} // end method insertAtFront

	/**
	 * insert item at end of List
	 */
	public void insertAtBack(T insertItem) {
		Node<T> node = new Node<T>(insertItem);

		if (isEmpty()) // firstNode and lastNode refer to same Object
			firstNode = lastNode = node;
		else { // lastNode's nextNode refers to new node
			lastNode.nextNode = node;
			lastNode = node;
			// you can replace the two previous lines with this line: lastNode =
			// lastNode.nextNode = new ListNode( insertItem );
		}
		size++;
	} // end method insertAtBack

	/**
	 * remove first node from List throws EmptyListException if the list is
	 * empty
	 */
	public T removeFromFront() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		T removedItem = firstNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else
			firstNode = firstNode.nextNode;
		size--;
		return removedItem; // return removed node data
	} // end method removeFromFront

	/**
	 * remove last node from List throws EmptyListException if the list is empty
	 */
	public T removeFromBack() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		T removedItem = lastNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else // locate new last node
		{
			Node<T> current = firstNode;

			// loop while current node does not refer to lastNode
			while (current.nextNode != lastNode)
				current = current.nextNode;

			lastNode = current; // current is new lastNode
			current.nextNode = null;
		} // end else
		size--;
		return removedItem; // return removed node data
	} // end method removeFromBack

	/**
	 * Remove node which contains the given value 
	 * @throws EmptyListException if the list is empty
	 */
	public T removeSelected(T val) throws EmptyListException 
        {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);
		T target = null;
		if (val.equals(firstNode.getObject())) 
                {
			target = firstNode.getObject();
			firstNode = firstNode.getNext();
			return target;
		}
		Node<T> a = firstNode;
		Node<T> b = null;

		while ((b = a.getNext()) != null) 
                {
			if (val.equals(b.getObject())) {
				target = b.getObject();
				a.nextNode = b.getNext();
				return target;
			}
			a = b;
		}
		return target;
	}

	/**
	 * determine whether list is empty
	 */
	public boolean isEmpty() 
        {
		return firstNode == null; // return true if List is empty
	} // end method isEmpty

	public int getSize() 
        {
		return size;
	}

	/**
	 * output List contents
	 */
	public void print(PrintStream ps) 
        {
		if (isEmpty()) {
			ps.append("Empty "+ name);
			ps.flush();
			return;
		} // end if

		Node<T> current = firstNode;

		// while not at end of list, output current node's data
		while (current != null) {
			ps.append(current.data+"\n");
			current = current.nextNode;
		} // end while

	} // end method print

        /**
         * MergeSort and merge methods (from Robert Sedgewick's book, pages 392-393).
         * 
         */
        
	public Node<T> MergeSort(Node<T> c,Comparator<T> cmp) 
        {
		if (c == null || c.nextNode == null)
			return c;
		Node<T> a = c;
		Node<T> b = c.nextNode;
		while ((b != null) && (b.nextNode != null)) {
			c = c.nextNode;
			b = (b.nextNode).nextNode;
		}
		b = c.nextNode;
		c.nextNode = null;
		return merge(MergeSort(a,cmp), MergeSort(b,cmp),cmp);

	}

	private Node<T> merge(Node a, Node b,Comparator cmp) 
        {
		Node<T> dummy = new Node<T>();
		Node<T> head = dummy;
		Node<T> c = head;
		while ((a != null) && (b != null)) {
			if (cmp.compare(a.data , b.data)<=0) {
				c.nextNode = a;
				c = a;
				a = a.nextNode;
			} else {
				c.nextNode = b;
				c = b;
				b = b.nextNode;
			}
		}
		c.nextNode = (a == null) ? b : a;
		return head.nextNode;
	}
}
