import java.util.LinkedList;
import java.util.Queue;

/*
 * This was a problem given in my Data Structures course. The modifications requested are below:
 * 		o) Added level-order traversal (levelOrderPrint() method) - Question 4.41
 * 		o) Added lazy deletion - Question 4.16
 * 		o) Added Tree Comparison - Question 4.46
 *  
 */

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
	/**
	 * Construct the tree.
	 */
	public BinarySearchTree( )
	{
		root = null;
	}

	/*
	 * Question 4.41
	 */
	public void levelOrderPrint() {  
		Queue<BinaryNode> tempQueue = new LinkedList<BinaryNode>();  
		tempQueue.add(root);  

		while(!tempQueue.isEmpty())  
		{  
			BinaryNode thisNode = tempQueue.poll();  
			System.out.println("Value: " + thisNode.element + "\tHeight: " + height(thisNode));

			if(thisNode.left != null)  
				tempQueue.add(thisNode.left);  

			if(thisNode.right != null)  
				tempQueue.add(thisNode.right);  
		}  
	}  
	
	/*
	 * Question 4.46
	 */
	public boolean compareTrees(BinarySearchTree a, BinarySearchTree b)
	{
		return compareTreesHelper(a.root, b.root);
	}

	/*
	 * Question 4.46
	 * Compares trees in linear runtime.
	 */
	private boolean compareTreesHelper(BinaryNode a, BinaryNode b)
	{
		if(a == null && a == null)
			return true;
		else if((a == null && b != null) || (a != null && b == null))
			return false;
		else
			return ( compareTreesHelper(a.left, b.left) && compareTreesHelper(a.right, b.right) );
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * @param x the item to insert.
	 */
	public void insert( AnyType x )
	{
		root = insert( x, root );
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * @param x the item to remove.
	 */
	public void remove( AnyType x )
	{
		root = remove( x, root );
	}
	
	/**
	 * Question 4.16
	 * Lazy Delete. Nothing is done if x is not found.
	 * @param x the item to remove.
	 * -Corey McCown
	 */
	public void lazyDelete( AnyType x )
	{
		root = lazyDelete( x, root );
	}
	
	/**
	 * Find the smallest item in the tree.
	 * @return smallest item or null if empty.
	 */
	public AnyType findMin( )
	{
		if( isEmpty( ) )
		{
			System.out.println("Error, tree is empty.");
			return null;
		}
		return findMin( root ).element;
	}

	/**
	 * Find the largest item in the tree.
	 * @return the largest item of null if empty.
	 */
	public AnyType findMax( )
	{
		if( isEmpty( ) )
		{
			System.out.println("Error, tree is empty.");
			return null;
		}
		return findMax( root ).element;
	}

	/**
	 * Find an item in the tree.
	 * @param x the item to search for.
	 * @return true if not found.
	 */
	public boolean contains( AnyType x )
	{
		return contains( x, root );
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty( )
	{
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty( )
	{
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree( )
	{
		if( isEmpty( ) )
			System.out.println( "Empty tree" );
		else
			printTree( root );
	}

	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
	{
		if( t == null )
			return new BinaryNode<AnyType>( x, null, null );

		int compareResult = x.compareTo( t.element );

		if( compareResult < 0 )
			t.left = insert( x, t.left );
		else if( compareResult > 0 )
			t.right = insert( x, t.right );
		else
			;  // Duplicate; do nothing
			return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
	{
		if( t == null )
			return t;   // Item not found; do nothing

		int compareResult = x.compareTo( t.element );

		if( compareResult < 0 )
			t.left = remove( x, t.left );
		else if( compareResult > 0 )
			t.right = remove( x, t.right );
		else if( t.left != null && t.right != null ) // Two children
		{
			t.element = findMin( t.right ).element;
			t.right = remove( t.element, t.right );
		}
		else
			t = ( t.left != null ) ? t.left : t.right;
		return t;
	}
	
	/**
	 * Question 4.16
	 * Internal method to lazy delete node
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> lazyDelete( AnyType x, BinaryNode<AnyType> t )
	{
		if( t == null )
			return t;   // Item not found; do nothing
		
		t.lazyDeleted = true;
		return t;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * MODIFICATIONS: Fix for LazyDelete (Question 4.16)
	 * @param t the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
	{
		/*
		 * OLD FINDMIN ALGORITHM
		 * if( t != null )
			return null;
		else if( t.left == null )
			return t;
		return findMin( t.left );
		*/
		
		if (t == null) 
			return t;

		BinaryNode<AnyType> tmp = findMin(t.left);
		if (tmp != null) 
			return tmp;

		if (!t.lazyDeleted) 
			return t;

		return findMin(t.right);
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * MODIFICATIONS: Fix for LazyDelete (Question 4.16)
	 * @param t the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
	{
		/*
		 * OLD FINDMAX ALGORITHM
		 * if( t != null )
			while( t.right != null )
				t = t.right;

		return t;*/
		
		if (t == null) 
			return t;

		BinaryNode<AnyType> tmp = findMax(t.right);
		if (tmp != null) 
			return tmp;

		if (!t.lazyDeleted) 
			return t;

		return findMax(t.left);
	}

	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the subtree.
	 * @return node containing the matched item.
	 */
	private boolean contains( AnyType x, BinaryNode<AnyType> t )
	{
		if( t == null )
			return false;

		int compareResult = x.compareTo( t.element );

		if( compareResult < 0 )
			return contains( x, t.left );
		else if( compareResult > 0 )
			return contains( x, t.right );
		else
			return true;    // Match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the subtree.
	 */
	private void printTree( BinaryNode<AnyType> t )
	{
		if( t != null )
		{
			printTree( t.left );
			//Add check for lazy deletion
			if(!t.lazyDeleted)
				System.out.print( t.element + " " );
			printTree( t.right );
		}
	}

	/**
	 * Internal method to compute height of a subtree.
	 * @param t the node that roots the subtree.
	 */
	private int height( BinaryNode<AnyType> t )
	{
		if( t == null )
			return -1;
		else
			return 1 + Math.max( height( t.left ), height( t.right ) );    
	}

	// Basic node stored in unbalanced binary search trees
	private static class BinaryNode<AnyType>
	{
		// Constructors
		BinaryNode( AnyType theElement )
		{
			this( theElement, null, null );
		}

		BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
		{
			element  = theElement;
			left     = lt;
			right    = rt;
		}

		AnyType element;            // The data in the node
		BinaryNode<AnyType> left;   // Left child
		BinaryNode<AnyType> right;  // Right child
		Boolean lazyDeleted = false;
	}


	/** The tree root. */
	private BinaryNode<AnyType> root;


	// Test program
	public static void main( String [ ] args )
	{
		BinarySearchTree<Integer> t = new BinarySearchTree<Integer>( );
		final int NUMS = 4000;
		final int GAP  =   37;

		System.out.println( "Checking... (no more output means success)" );

		for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
			t.insert( i );

		for( int i = 1; i < NUMS; i+= 2 )
			t.remove( i );

		if( NUMS < 40 )
			t.printTree( );
		if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
			System.out.println( "FindMin or FindMax error!" );

		for( int i = 2; i < NUMS; i+=2 )
			if( !t.contains( i ) )
				System.out.println( "Find error1!" );

		for( int i = 1; i < NUMS; i+=2 )
		{
			if( t.contains( i ) )
				System.out.println( "Find error2!" );
		}
	}
}
