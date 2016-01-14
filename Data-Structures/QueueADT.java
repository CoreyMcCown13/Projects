import java.util.Iterator;
import java.util.NoSuchElementException;

 /*
 * Create a linked list version of a QueueADT (myQueue)
 * Implement it to allow for generic types (just like myStack that we did in class)
 * Store a first/last node to allow for easy constant time methods
 * Write a short testMyQueue class to test your Queue
 * 
 * Referencing http://docs.oracle.com/javase/7/docs/api/java/util/Queue.html for needed methods...
 *
 */

public class QueueADT<Item> implements Iterable<Item> {
	private int elementNo;       // number of elements on queue
	private Node<Item> first;    // beginning of queue
	private Node<Item> last;     // end of queue

	// Helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}

	public QueueADT() {
		first = null;
		last  = null;
		elementNo = 0;
	}

	public boolean isEmpty() {
		//If the first element is empty, the entire queue is empty.
		return first == null;
	}

	public int size() {
		//Simply return the elementNo variable
		return elementNo;     
	}


	public Item peek() {
		if (!isEmpty()) 
			return first.item;
		else
			return null;
	}
	
	public Item poll() {
		if (!isEmpty()) 
			return last.item;
		else if(first.item != null)
			return first.item;
		else
			return null;
	}

	public void enqueue(Item item) {
		Node<Item> temp = last;
		last = new Node<Item>();
		last.item = item;
		last.next = null;
		
		if (isEmpty()) 
		{
			//If it is empty, this element will be the first
			first = last;
		} else {
			//If it is not empty, this element will be the last
			temp.next = last;
		}
		
		elementNo++;
	}

	public Item dequeue() {
		if (isEmpty()){
			//There are no items in the queue
			System.out.println("No items in queue");
			return null;
		} else {
			Item temp = first.item;
			first = first.next;
			elementNo--;
			return temp;
		}
	}

	public String toString() {
        StringBuilder s = new StringBuilder();
        int i = 0;
        for (Item item : this){
        	i++;
        	if(i < this.size())
        		s.append(item + ", ");
        	else 
        		s.append(item + ".");
        }
        return s.toString();
    } 
	
	public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);  
    }
	
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { 
        	return current != null;                     
        }
        
        public void remove() { 
        	 
        }

        public Item next() {
            if (!hasNext())
            {
            	System.out.println("No more items!");
            	return null;
            } else {
	            Item item = current.item;
	            current = current.next; 
	            return item;
            }
        }
    }

	public static void main(String[] args) {
		QueueADT<String> thisQueue = new QueueADT<String>();
		System.out.println("Entering 5 values in the queue.");
		thisQueue.enqueue("One");
		thisQueue.enqueue("Two");
		thisQueue.enqueue("Three");
		thisQueue.enqueue("Four");
		thisQueue.enqueue("Five");
		System.out.println("Values in queue: " + thisQueue.size() + "\nThis Queue: " + thisQueue);
		
	}
}
