import java.util.Comparator;

public class BinarySearchTree 
{

    /**
     * Each node holds four references: left, right, parent and element.
     */
    static class Node 
    {
        public Node left, parent, right;
        public WordFreq element;

        protected Node(WordFreq element) 
        {
            if (element == null) 
            {
                throw new IllegalArgumentException();
            }
            this.element = element;
        }

        protected void unlink() 
        {
            element = null;
            parent = left = right = null;
        }
    }
    
    // sum is the total number of words (not the dinstict words)
    private int sum;
    //minimun and maximum qord frequencies
    private WordFreq min;
    private WordFreq max;

    /**
     * Clears the tree sum, max and min variables in case the tree is emptied and re-filled.
     */
    public void clearStats() 
    {
        this.sum = 0;
        this.max = null;
        this.min = null;
    }

    /**
     * get methods.
     */
    public int getSum() 
    {
        return sum;
    }

    public WordFreq getMin() 
    {
        return min;
    }

    public WordFreq getMax() 
    {
        return max;
    }
    
    /**
     * Calculates the mean frequency used in the WordCounter implementation.
     */
    public float calculateMean() 
    {
        return (float) sum / size;
    }
 

     // Boolean variable to check the type of insertion 
     // If true,then insert as leaf, else insert as root
    private boolean insertion;

    /**
     * Method used to change insertion variable.
     */
    public void changeInsertion(boolean val) 
    {
        this.insertion = val;
    }
    
    /**
     * The root of the tree.
     */
    protected Node root;
    /**
     * The number of nodes in the tree.
     */
    protected int size;
    /**
     * The comparison function.
     */
    protected Comparator<WordFreq> cmp;

    /**
     * Default constructor.
     */
    public BinarySearchTree() 
    {
        this(new WordComparator());
    }

    /**
     * Parametrized constructor that uses a given comparison function.
     *
     * @param cmp The comparison function to use.
     */
    public BinarySearchTree(Comparator<WordFreq> cmp) 
    {
        this.size = 0;
        this.cmp = cmp;
        this.sum = 0;
        this.max = null;
        this.min = null;
        this.insertion = false;
    }

    /**
     * Returns the size of the tree.
     */
    public int size() 
    {
        return size;
    }

    /**
     * Finds the node a given element is stored at.
     *
     * @param element The element to search for.
     * @return The node that hosts element, or null if element wasn't found.
     */
    public WordFreq search(WordFreq element) 
    {
        Node p = root;
        
        while (p != null) 
        {
            // Compare element with the element in the current subtree
            int result = cmp.compare(element, p.element);
            if (result == 0) break;   //element found
            // Go left or right based on comparison result
            p = result < 0 ? p.left : p.right;
        }
        
        if (p == null) 
        {
            return null;
        } else if (p.element.getFreq() > calculateMean()) 
        {
            //if the word found has a frequency greater than the Mean frequency, insert it as the root
            changeInsertion(true); //switch insertion, to root insertion
            insert(remove(p));
            changeInsertion(false); //restore to leaf insertion
            return root.element;
        } else 
        {
            return p.element;
        }
    }

    /**
     * Inserts an element in the tree.
     *
     * @param element The element to insert
     */
    public void insert(WordFreq element) 
    {
        if (insertion) 
        {
             rootInsert(element);
        } else 
        {
            if (element == null) 
                throw new IllegalArgumentException();

            Node n = root;
            Node p = null;
            int result = 0;
            
            while (n != null) 
            {
                // Compare element with the element in the current subtree
                result = cmp.compare(element, n.element);
                if (result == 0) //word already exists
                {            
                    n.element.increase();
                    sum++;
                    //check for new max or min freq
                    if (n.element.getFreq() > max.getFreq()) 
                    {
                        max = n.element;
                    }
                    if (n.element.getFreq() < min.getFreq()) 
                    {
                        min = n.element;
                    }
                    return;
                }
                // Go left or right based on the comparison result
                // Keep a reference in the last non node encountered
                p = n;
                n = result < 0 ? n.left : n.right;
            }
            
            // Create and connect a new node
            Node node = new Node(element);
            node.parent = p;
            
            // The new node must be a left child of p
            if (result < 0) 
            {
                p.left = node;
            } // The new node must be a right child of p
            else if (result > 0) 
            {
                p.right = node;
            } // The tree is empty; root must be set
            else 
            {
                root = node;
            }
            
            //add to the total word count, the newly added word's frequency
            sum += node.element.getFreq();
            
            //double check for the min and max so as to avoid a NullPointerException
            if (min == null) 
            {
                min = node.element;
            } else if (min.getFreq() > node.element.getFreq()) 
            {
                min = node.element;
            }

            if (max == null) 
            {
                max = node.element;
            } else if (max.getFreq() < node.element.getFreq()) 
            {
                max = node.element;
            }
            ++size;
        }
    }
    
    /**
     * Recursively adds an element in the root of tree by doing rotations.
     */
    private Node rootInsert(Node p, WordFreq element, Node parent) 
    {
        if (p == null) 
        {
            Node node = new Node(element);
            node.parent = parent;
            sum+=node.element.getFreq();
            ++size;
            return node;
        }

        int result = cmp.compare(element, p.element);
        
        if (result == 0) 
        {
            p.element.increase();
            sum++;
            return p;
        }
        if (result < 0) 
        {
            p.left = rootInsert(p.left, element, p);
            p = rotateRight(p);
        } else 
        {
            p.right = rootInsert(p.right, element, p);
            p = rotateLeft(p);
        }
        return p;
    }

    public void rootInsert(WordFreq element) 
    {
        root = rootInsert(root, element, null);
        //a check for a new min/max is necessary here, 
        //because the inserted element may be already existing in the tree
            if (min == null) 
            {
                min = root.element;
            } else if (min.getFreq() > root.element.getFreq()) 
            {
                min = root.element;
            }

            if (max == null) 
            {
                max = root.element;
            } else if (max.getFreq() < root.element.getFreq()) 
            {
                max = root.element;
            }

    }

    /**
     * Performs a left rotation.
     *
     * @param pivot The node to rotate.
     */
    private Node rotateLeft(Node pivot) 
    {
        Node parent = pivot.parent;
        Node child = pivot.right;
        if (parent == null) 
        {
            root = child;
        } else if (parent.left == pivot) 
        {
            parent.left = child;
        } else 
        {
            parent.right = child;
        }
        child.parent = pivot.parent;
        pivot.parent = child;
        pivot.right = child.left;
        if (child.left != null) 
        {
            child.left.parent = pivot;
        }
        child.left = pivot;
        return child;
    }

    /**
     * Performs a right rotation.
     *
     * @param pivot The node to rotate.
     */
    private Node rotateRight(Node pivot) 
    {
        Node parent = pivot.parent;
        Node child = pivot.left;
        if (parent == null) {
            root = child;
        } else if (parent.left == pivot) 
        {
            parent.left = child;
        } else 
        {
            parent.right = child;
        }
        child.parent = pivot.parent;
        pivot.parent = child;
        pivot.left = child.right;
        if (child.right != null) 
        {
            child.right.parent = pivot;
        }
        child.right = pivot;
        return child;
    }

    /**
     * Removes a given node from the tree.
     *
     * @throws NullPointerException if p is null.
     * @param p The node to remove.
     * @return WordFreq object of the node that was removed.
     */
    private WordFreq remove(Node p) 
    {
        WordFreq temp = p.element;
        // If p has two children find its successor, then remove it
        if (p.left != null && p.right != null) 
        {
            Node succ = succ(p);
            p.element = succ.element;
            p = succ;
        }
        Node parent = p.parent;
        Node child = p.left != null ? p.left : p.right;
        // The root is being removed
        if (parent == null) 
        {
            root = child;
        } // Bypass p
        else if (p == parent.left) 
        {
            parent.left = child;
        } else 
        {
            parent.right = child;
        }
        if (child != null) 
        {
            child.parent = parent;
        }
        // Dispose p
        p.unlink();
        --size;
        return temp;
    }

    public WordFreq removeRoot() 
    {
        return remove(root);
    }

    /**
     * Finds the inorder successor of a ginen node.
     *
     * @param q The node whose successor to find
     * @throws NullPointerException if q is null
     * @return The successor of q, or null if q is the last node
     */
    private Node succ(Node q) 
    {
        // The successor is the leftmost leaf of q�s right subtree
        if (q.right != null) 
        {
            Node p = q.right;
            while (p.left != null) 
            {
                p = p.left;
            }
            return p;
        } // The successor is the nearest ancestor on the right
        else 
        {
            Node p = q.parent;
            Node ch = q;
            while (p != null && ch == p.right) 
            {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private String toStringR(Node h) 
    {
        if (h == null) 
        {
            return "";
        }
        String s = toStringR(h.left);
        s += h.element.toString() + "\n";
        s += toStringR(h.right);
        return s;
    }

    public String toString() 
    {
        return toStringR(root);
    }
}