public class LinkedListTree<AnyType extends Comparable<AnyType>>
{
	private static class Node<AnyType>
	{
		AnyType value;
		Node<AnyType> left;
		Node<AnyType> right;

		Node(AnyType e)
		{
			this(e,null,null);
		}

		Node(AnyType val, Node<AnyType> leftVal, Node<AnyType> rightVal)
		{
			value = val;
			left = leftVal;
			right = rightVal;
		}
	}

	private Node<AnyType> root;

	public LinkedListTree()
	{
		root = null;
	}

	public boolean isEmpty()
	{
		return (root == null);
	}

	private boolean contains(AnyType val, Node<AnyType> thisNode)
	{
		if(thisNode == null)
		{
			return false;
		}

		int temp = val.compareTo(thisNode.value);
		if(temp < 0)
			return (contains(val,thisNode.left));

		else if(temp > 0)
			return (contains(val,thisNode.right));

		else
			return true;
	}
	
	private Node<AnyType> findMax(Node<AnyType> thisNode)
	{
		if(thisNode == null)
			return null;
		while(thisNode.right != null)
			thisNode = thisNode.right;

		return thisNode;
	}

	private Node<AnyType> findMin(Node<AnyType> thisNode)
	{
		if(thisNode == null)
			return null;

		else if(thisNode.left == null)
			return thisNode;

		else
			return(findMin(thisNode.left));
	}

	private Node<AnyType> insert(AnyType val, Node<AnyType> thisNode)
	{
		if(thisNode == null)
			return (new Node<AnyType>(val));

		int temp = val.compareTo(thisNode.value);

		if(temp < 0)
			thisNode.left = insert(val,thisNode.left);

		else if(temp > 0)
			thisNode.right = insert(val, thisNode.right);

		return thisNode;
	}

	private Node<AnyType> remove(AnyType val, Node<AnyType> thisNode)
	{
		if(thisNode == null)
			return thisNode;

		int temp = val.compareTo(thisNode.value);
		
		if(temp < 0)
			thisNode.left = remove(val,thisNode.left);

		else if(temp > 0)
			thisNode.right = remove(val,thisNode.right);

		else if(thisNode.left != null && thisNode.right != null)
		{
			thisNode.value = findMin(thisNode.right).value;
			thisNode.right = remove(thisNode.value,thisNode.right);
		} else {
			if(thisNode.left != null)
				thisNode = thisNode.left;
			else
				thisNode = thisNode.right;
		}

		return thisNode;
	}

	private void printTree(Node<AnyType> thisNode)
	{
		if(thisNode != null)
		{
			printTree(thisNode.left);
			System.out.println(thisNode.value);
			printTree(thisNode.right);
		}
	}


	public boolean contains(AnyType val)
	{
		return contains(val,root);
	}

	public AnyType findMin()
	{
		if(isEmpty())
			throw new java.util.NoSuchElementException();

		return (findMin(root).value);
	}

	public AnyType findMax()
	{
		if(isEmpty())
			throw new java.util.NoSuchElementException();

		return (findMax(root).value);
	}

	public void insert(AnyType val)
	{
		root = insert(val, root);
	}

	public void remove(AnyType val)
	{
		root = remove(val, root);
	}

	public void printTree()
	{
		if(isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);

	}

}
