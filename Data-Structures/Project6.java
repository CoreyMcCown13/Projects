import java.util.Iterator;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/*
 * Write a program that asks a user for a list of unique names, 
 * put each name into a TreeMap, using the name as the key, 
 * and the number of vowels in the name as the value. 
 * Traverse the map using an iterator to print out all the key/value pairs.
 * 
 */

public class Project6 {
	public static void main(String args[])
	{
		Scanner scanner = new Scanner(System.in);
		String nameEntry = "";
		ArrayList<String> theNames = new ArrayList<String>();

		System.out.println("Enter names to log. Type \"quit\" to quit entering new names.");
		System.out.println("=============================================================");
		int i = 1;
		while(!nameEntry.equalsIgnoreCase("quit"))
		{
			System.out.print("Enter name #" + i + ": ");
			nameEntry = scanner.nextLine().toUpperCase();
			if(!nameEntry.equalsIgnoreCase("quit"))
			{
				if(!theNames.contains(nameEntry))
				{
					theNames.add(nameEntry);
					i++;
				} else
					System.out.println("ERROR: Name already entered!");
			}
		}

		System.out.println("\n=============================================================");
		System.out.println("Names logged:");
		
		//Add all values to treemap
		TreeMap<String, Integer> treemap = new TreeMap<String, Integer>();
		for(String thisName : theNames)
			treemap.put(thisName, vowelNo(thisName));

		//Set up iterator
		Set<Entry<String, Integer>> theSet = treemap.entrySet();
		Iterator<Entry<String, Integer>> iterator = theSet.iterator();
		i = 1;
		
		//Display all names
		while(iterator.hasNext()) {
			Entry<String, Integer> thisEntry = iterator.next();
			System.out.println("ENTRY #" + i + " - Name (KEY): " + thisEntry.getKey() + " - Vowels (VALUE): " + thisEntry.getValue());
			i++;
		}
	}

	private static int vowelNo(String input)
	{
		String lower = input.toLowerCase();
		int vowelCount = 0;

		for (int i = 0; i < lower.length(); i++) {
			if(lower.charAt(i) == 'a' || lower.charAt(i) == 'e' || lower.charAt(i) == 'i' || lower.charAt(i) == 'o' || lower.charAt(i) == 'u')
				vowelCount++;
		}
		return vowelCount;
	}
}
