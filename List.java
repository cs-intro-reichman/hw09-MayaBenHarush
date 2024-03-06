/** A linked list of character data objects.
 *  (Actually, a list of Node objects, each holding a reference to a character data object.
 *  However, users of this class are not aware of the Node objects. As far as they are concerned,
 *  the class represents a list of CharData objects. Likwise, the API of the class does not
 *  mention the existence of the Node objects). */
public class List {

    // Points to the first node in this list
    private Node first;

    // The number of elements in this list
    private int size;
	
    /** Constructs an empty list. */
    public List() {
        first = null;
        size = 0;
    }

    /** Returns the number of elements in this list. */
    public int getSize() {
 	      return size;
    }

    /** Returns the first element in the list */
    public CharData getFirst() {
        return first.cp;
    }

    /** GIVE Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char chr) {
        CharData charData = new CharData(chr);
        Node nodeCurren = new Node(charData);
        nodeCurren.next = first; 
        first = nodeCurren;      
        size++;
    }
    
    /** GIVE Textual representation of this list. */
    public String toString() {

        if (this.size == 0) {
            return "()";
        }
        String ans = "(";
        Node currentNode = this.first;
        while (currentNode != null) {
            ans = ans + currentNode.cp.toString() + " ";
            currentNode = currentNode.next;
        }
        return ans.substring(0, ans.length() - 1) + ")";
    }

    /** Returns the index of the first CharData object in this list
     *  that has the same chr value as the given char,
     *  or -1 if there is no such object in this list. */
    public int indexOf(char chr) {
        int index = 0;
        Node nodeCurren = first;
        
        while (nodeCurren != null) {
            if (nodeCurren.cp.equals(chr)) {
                return index;
            }
            nodeCurren = nodeCurren.next;
            index++;
        }
        return -1;
    }

    /** If the given character exists in one of the CharData objects in this list,
     *  increments its counter. Otherwise, adds a new CharData object with the
     *  given chr to the beginning of this list. */
    public void update(char chr) {

        int index = indexOf(chr);

        if(index == -1){
            addFirst(chr);
        }

        else {
            Node nodeCurren = first;
            while (nodeCurren.cp.chr != chr){
                nodeCurren = nodeCurren.next;
            }
            nodeCurren.cp.count++;
        }
    }

    /** GIVE If the given character exists in one of the CharData objects
     *  in this list, removes this CharData object from the list and returns
     *  true. Otherwise, returns false. */
    public boolean remove(char chr) {
        Node nodeCurren = first;

        if (nodeCurren.cp.chr == chr) {
            first = nodeCurren.next;
            size--;
            return true;
        }
        while (nodeCurren.next != null) {
            if (chr == nodeCurren.next.cp.chr) {
                nodeCurren.next = nodeCurren.next.next;
                size--;
                return true;
            }
            nodeCurren = nodeCurren.next;
        }
        return false;
    }

    /** Returns the CharData object at the specified index in this list. 
     *  If the index is negative or is greater than the size of this list, 
     *  throws an IndexOutOfBoundsException. */
    public CharData get(int index) {

        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index is illegal " + index);
        }

        CharData[] newList = toArray();
        return newList[index];
    }

    /** Returns an array of CharData objects, containing all the CharData objects in this list. */
    public CharData[] toArray() {
	    CharData[] arr = new CharData[size];
	    Node current = first;
	    int i = 0;
        while (current != null) {
    	    arr[i++]  = current.cp;
    	    current = current.next;
        }
        return arr;
    }

    /** Returns an iterator over the elements in this list, starting at the given index. */
    public ListIterator listIterator(int index) {
	    // If the list is empty, there is nothing to iterate   
	    if (size == 0) return null;
	    // Gets the element in position index of this list
	    Node current = first;
	    int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        // Returns an iterator that starts in that element
	    return new ListIterator(current);
    }
}
