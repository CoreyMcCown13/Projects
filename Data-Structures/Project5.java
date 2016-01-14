import java.io.File;
import java.util.ArrayList;

/*
 * Using the linked list implementation for a general tree, 
 * create a Java program to store a folder structure in a tree, 
 * then print the tree. Include a space for each depth the folder is in the tree.
 * 
*/

public class Project5 {
	public static void main (String[] args)
	{
		File directory = new File("P:\\Traverse Me\\");
		ArrayList<String> thePaths = new ArrayList<String>(getDirectories(directory));
		AvlTree<String> theTree = new AvlTree<String>();
		BinarySearchTree<String> thisTree = new BinarySearchTree<String>();
		LinkedListTree<String> tree = new LinkedListTree<String>();
		
		System.out.println("Adding files...");
		for(String thisString : thePaths)
		{
			theTree.insert(thisString);
			thisTree.insert(thisString);
			tree.insert(thisString);
			System.out.println("Added: " + thisString);
		}
		
		System.out.println("\nAVL Tree:");
		theTree.printTree();
		System.out.println("\nBinary Search Tree:");
		thisTree.printTree();
		System.out.println("\nLinkedList Tree:");
		tree.printTree();
		

	}
	
	//Function to find all files/directories recursively
	public static ArrayList<String> getDirectories (File f)
	{
		ArrayList<String> paths = new ArrayList<String>();
		
		if(f.isDirectory()){
			for(File sub : f.listFiles())
			{
				if(sub.isDirectory())
					paths.addAll(getDirectories(sub));
				else
					paths.add(sub.toString());
			}
		}		
		return paths;
	}
}
