import java.util.ArrayList;
import java.util.Scanner;

/* 
 * SELECTION ALGORITHM 6A
 * 
 * For simplicity, we assume that we are interested in finding the kth smallest element. The
 * algorithm is simple. We read the N elements into an array. We then apply the buildHeap
 * algorithm to this array. Finally, we perform k deleteMin operations. The last element
 * extracted from the heap is our answer. It should be clear that by changing the heap-order
 * property, we could solve the original problem of finding the kth largest element.
 * 
 */

public class AlgSixA {
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String intEntry = "";
		ArrayList<Integer> theInts = new ArrayList<Integer>();

		System.out.println("Enter integers to parse. Type \"quit\" to quit entering integers.");
		System.out.println("=================================================================");
		int i = 1;
		while(!intEntry.equalsIgnoreCase("quit"))
		{
			System.out.print("Enter integer #" + i + ": ");
			intEntry = scanner.nextLine();
			if(!intEntry.equalsIgnoreCase("quit"))
			{
				if(isInteger(intEntry))
				{
					if(!theInts.contains(Integer.parseInt(intEntry)))
					{
						theInts.add(Integer.parseInt(intEntry));
						i++;
					} else
						System.out.println("ERROR: Duplicate entry.");
				} else 
					System.out.println("ERROR: Non-integer entered.");
			}
		}

		//Add all integers to the heap
		MyBinaryHeap<Integer> thisHeap = new MyBinaryHeap<Integer>();
		for(int thisInt : theInts)
			thisHeap.insert(thisInt);

		//Find what value user wants
		int k;		
		String kthValue = "";
		System.out.println();
		while(!isInteger(kthValue))
		{
			System.out.print("Find what lowest value?\n  > ");
			kthValue = scanner.nextLine();
		}

		//Perform k deleteMins to find value
		k = Integer.parseInt(kthValue);
		for(i = 0; i < (k - 1); i++)
			thisHeap.deleteMin();

		//Display result
		System.out.println("The " + k + " lowest value is: " + thisHeap.findMin());
	}

	//Regex expression to determine if a string is an int. Used for verifying inputs.
	private static boolean isInteger(String x)
	{
		return x.matches("^-?\\d+$");
	}
}
