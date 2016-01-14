public class SingleLinkedADT {
	Node first;

	public static void main (String[] args)
	{
		SingleLinkedADT thisSingleLink = new SingleLinkedADT();
		thisSingleLink.add(1);
		thisSingleLink.add(2);
		thisSingleLink.add(3);
		thisSingleLink.add(4);
		System.out.println("Added 4 numbers...");
		thisSingleLink.printList();
		System.out.println("Running swap...");
		thisSingleLink.swap();
		thisSingleLink.printList();
		System.out.println("Lazy delete index 2:");
		thisSingleLink.lazyDelete(2);
		thisSingleLink.printList();
	}

	public SingleLinkedADT()
	{
		first = null;
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
		if(first == null)
		{
			first = newData;
		}
		else
		{
			current = first;
			while(current.next != null)
			{
				current = current.next;
			}
			current.next = newData;
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
				//Only display it if it is not deleted.
				if(!current.deleted) System.out.print(current.val + ", ");
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
		if(first == null)
		{
			first = newData;
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
			//node 1, node 2
			temp = current.next;//pointer to node 2
			current.next = newData;//node 1 now points to newData
			newData.next = temp;//newData now points to node 2
			//node 1, newData, node 2
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
	
	public void lazyDelete(int index)
	{
		Node current;
		int currentIndex;
		if(first == null || index < 0)
		{
			System.out.println("ERR: No entries or invalid argument.");
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
				current.deleted = true;
				System.out.println("Lazy deleted index: " + index);
			}
			else System.out.println("ERR: No entries or invalid argument.");
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
		public Node next;
		public int val;
		public boolean deleted = false;
		public Node(int v)
		{
			val = v;
			next = null;
		}
	}
}
