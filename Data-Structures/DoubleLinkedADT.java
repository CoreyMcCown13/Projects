
public class DoubleLinkedADT {
	Node first;
	Node last;
	int size;

	public static void main (String[] args)
	{
		DoubleLinkedADT thisDoubleLink = new DoubleLinkedADT();
		thisDoubleLink.add(0);
		thisDoubleLink.add(1);
		thisDoubleLink.add(2);
		thisDoubleLink.add(3);
		thisDoubleLink.add(4);
		thisDoubleLink.printList();
		thisDoubleLink.swap();
		thisDoubleLink.printList();
	}
	public DoubleLinkedADT()
	{
		first = null;
		last = null;
		size = 0;
	}

	public void swap ()
	{
		Node current = first;
		Node currentNext = current.next;
		Node currentPrev = null;

		do {
			current.next=currentNext.next; 
			currentNext.next=current; 

			if(currentPrev == null) {
				currentPrev=currentNext; 
				first=currentPrev; 
				currentPrev=currentPrev.next; 
			} else {
				currentPrev.next = currentNext; 
				currentPrev = currentPrev.next.next; 
			}

			current = current.next;

			if(current == null) break; 

			currentNext = currentNext.next.next.next; 

		} while(currentNext != null && currentNext != current);	
	}

	public void add(int val)
	{
		Node newData = new Node(val);
		Node current;
		if(size == 0)
		{
			first = newData;
			last = first;
			size++;
		}
		else
		{
			current = last;
			current.next = newData;
			last = newData;
			newData.previous = current;
			size++;
		}
	}

	public void printList()
	{

		Node current;
		if(first != null)
		{
			current = first;
			while(current.next != null)
			{
				System.out.print(current.val + ", ");
				current = current.next;
			}
			System.out.println(current.val);
		}

	}
	public void insert(int val, int index)
	{
		Node newData = new Node(val);
		Node current, temp;
		int currentIndex;
		if(size == 0 || index >= size)
		{
			add(val);
		}
		else
		{
			if(index < 0)
				index = 0;
			if(index < size/2)//start at beginning
			{
				current = first;
				currentIndex = 0;
				while(current.next != null && currentIndex < index)
				{
					current = current.next;
					currentIndex++;
				}
				//node 1, node 2
				temp = current.next;//pointer to node 2
				current.next = newData;//node 1 now points to newData
				newData.next = temp;//newData now points to node 2
				newData.previous = current;
				temp.previous = newData;
				//node 1, newData, node 2
			}
			else//start at end
			{
				current = last;
				currentIndex = size-1;
				while(current.previous != null && currentIndex > index)
				{
					current = current.previous;
					currentIndex++;
				}
				//node 1, node 2
				temp = current.next;//pointer to node 2
				current.next = newData;//node 1 now points to newData
				newData.next = temp;//newData now points to node 2
				newData.previous = current;
				temp.previous = newData;
				//node 1, newData, node 2
			}


		}
	}
	public int delete(int index)
	{
		Node current, temp;
		int currentIndex;
		if(first == null || index < 0)
		{
			return -1;
		}
		else
		{
			current = first;
			currentIndex = 0;
			while(current.next != null && currentIndex < index)
			{
				current = current.next;
				currentIndex++;
			}
			if(currentIndex == index)
			{
				//node 1, node 2, node 3
				//node 1 = current
				int tempVal = current.next.val;
				temp = current.next.next;
				//temp = node 3

				current.next = temp;
				//the node after current = node 3
				return tempVal;
			}
			else
				return -1;
		}
	}
	public int find(int val)
	{
		Node current;
		int position;
		if(first == null)
		{
			return -1;
		}
		else
		{
			current = first;
			position = 0;
			while(current.next != null)
			{
				if(current.val != val)
				{
					current = current.next;
					position++;
				}
				else
					return position;
			}
			return -1;
		}
	}
	private class Node{
		public Node next, previous;
		public int val;
		public Node(int v)
		{
			val = v;
			next = null;
			previous = null;
		}
	}
}
