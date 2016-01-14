/*
 * Binary Heap structure
 */
 
import java.util.Arrays;
import java.util.PriorityQueue;

public class MyBinaryHeap<AnyType extends Comparable<? super AnyType>> {
	private static final int DEFAULT_SIZE = 16;
	private int currentSize;
	private AnyType[] heap;
	public MyBinaryHeap()
	{
		this(DEFAULT_SIZE);
	}
	public MyBinaryHeap(int size)
	{
		currentSize = 0;
		heap = (AnyType[]) new Comparable[ size + 1 ];
	}
	public MyBinaryHeap(AnyType[] items)
	{
		currentSize = items.length;
		heap = (AnyType[]) new Comparable[ currentSize*2 + 1 ];
		for(int i = 0; i < items.length; i++)
			heap[i+1] = items[i];
		buildHeap();
	}

	public boolean isEmpty()
	{
		return currentSize == 0;
	}
	public void makeEmpty()
	{
		currentSize = 0;
	}
	private void growArray(int newSize)
	{
		AnyType[] old = heap;
		heap = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
        	heap[ i ] = old[ i ];
	}
	public void insert(AnyType item)
	{
		if( currentSize == heap.length - 1 )
			growArray( heap.length * 2 + 1 );
		
		currentSize++;
		int hole = currentSize;
		heap[0] = item;
		percolateUp(hole);
	}
	private void percolateUp(int pos)
	{
		AnyType item = heap[0];
		for(; item.compareTo(heap[pos/2]) < 0; pos = pos/2)
			heap[pos] = heap[pos/2];
		heap[pos] = item;
	}
	public AnyType findMin()
	{
		if(currentSize == 0)
			return null;
		return heap[1];
	}
	public AnyType deleteMin()
	{
		if(currentSize == 0)
			return null;
		AnyType temp = heap[1];
		
		heap[1] = heap[currentSize];
		currentSize--;
		
		percolateDown(1);
		
		return temp;
	}
	
	private void percolateDown(int pos)
	{
		int child;
		AnyType temp = heap[pos];
		for(; pos*2 <= currentSize; pos = child)
		{
			child = pos*2;
			if(child != currentSize && 
					heap[child+1].compareTo(heap[child]) < 0)
				child++;
			if(heap[child].compareTo(temp) < 0)
				heap[pos] = heap[child];
			else
				break;
		}
		heap[pos] = temp;
	}
	public void buildHeap()
	{
		for(int i = currentSize / 2; i > 0; i--)
		{
			percolateDown(i);
		}
	}
	public String toString()
	{
		return Arrays.toString(heap);
	}
	
	/**
	 * Perform a min-heapsort of the heap
	 */
	public void minHeapsort()
	{
		buildHeap();
		//System.out.println(this);
		AnyType[] temp = (AnyType[]) new Comparable[ heap.length ];
		for(int i = 0; i < heap.length; i++)
		{
			//System.out.print(findMin() + " ");
			temp[i] = findMin();
			deleteMin();
		}
		heap = temp;
		System.out.println("HEAPSORTED: " + this);
	}
}
