/* 
 * Insertion Sort implementation. Displays each stage of the sort.
 */
 
import java.util.Arrays;

public class InsertionSort {
	public static void main(String[] args) {
		int[] input = { 3,1,4,1,5,9,2,6,5 };
		System.out.println("Insertion Sorting:\n\t" + Arrays.toString(input) +"\n");
		insertionSorter(input);
	}

	public static void insertionSorter(int[] numbers) {
		for (int i = 1; i < numbers.length; i++) {
			int thisNumber = numbers[i];
			int k = i - 1;

			while ((k > -1) && (numbers [k] > thisNumber)) {
				numbers [k+1] = numbers [k];
				k--;
			}

			numbers[k+1] = thisNumber;
			displayNums(numbers);
		}
	}

	private static void displayNums(int[] numbers) {

		for (int i = 0; i < numbers.length; i++) {
			if(i == numbers.length - 1)
				System.out.print(numbers[i]);
			else
				System.out.print(numbers[i] + ", ");
		}
		System.out.println();
	}
}
