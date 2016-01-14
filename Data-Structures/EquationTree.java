import java.util.Stack;

/*
 * Allow the user to supply infix/postfix/prefix versions of the equation 
 * (ask which type, then pass to the correct method to put into the tree). 
 * Store the equation into the tree and print all three versions of the equation from the tree.
 * 
 */

public class EquationTree {

	private Node root;

	public EquationTree()
	{
		root = null;
	}

	private static boolean isOperator(char thisChar){
		//Acceptable characters: +-*/^
		return (thisChar == '+' || thisChar == '-' || thisChar == '*' || thisChar =='/' || thisChar == '^');

	}

	public void populateTreeFromInfix(String infix)
	{
		//((a+(b*c))+(((d*e)+f)*g))
		if(infix.length() > 0)
			root = infixHelper(infix, 0);
	}

	public void populateTreeFromPostfix(String postfix)
	{
		if(postfix.length() > 0)
			root = postfixHelper(postfix, 0);
	}

	public void populateTreeFromPrefix(String prefix)
	{
		if(prefix.length() > 0)
			root = postfixHelper(preToPost(prefix), 0);
	}

	private String preToPost(String prefix)
	{
		if(prefix.length() <= 1){
			return prefix;
		}

		if(isOperator(prefix.charAt(0)))
		{
			String temp1 = preToPost(prefix.substring(1)) + prefix.charAt(0);
			String temp2 = preToPost(prefix.substring(temp1.length()));
			return temp1 + temp2;
		} else if(isOperator(prefix.charAt(1))) return prefix.substring(0,1);
		 else return prefix.substring(0,2);
		
	}

	private Node postfixHelper(String postfix, int depth)
	{
		//ab+cd-
		int k = 0;
		Stack<Node> nodes = new Stack<Node>();
		for (int i = 0; i < postfix.length(); i++) {
			char thisChar  = postfix.charAt(i);
			if (isOperator(thisChar)) {
				Node temp = new Node(thisChar, depth + k);
				k++;             
				temp.right = nodes.pop();
				temp.left = nodes.pop();
				nodes.push(temp);
			} else {
				Node temp2 = new Node(thisChar, depth + k);
				nodes.push(temp2);
			}
		}
		return nodes.pop();
	}


	private Node infixHelper(String infix, int depth)
	{
		String[] pieces = splitInfix(infix);//left, right, center
		Node temp = new Node(pieces[2].charAt(0), depth);
		if(!pieces[0].equals(""))
			temp.left = infixHelper(pieces[0], depth+1);
		if(!pieces[1].equals(""))
			temp.right = infixHelper(pieces[1], depth+1);
		return temp;
	}

	//returns left, right, center
	private String[] splitInfix(String infix)
	{
		if(infix.length() <= 1)
			return new String[]{"","",infix};
		//((a+(b*c))+(((d*e)+f)*g))
		infix = infix.substring(1, infix.length()-1);
		//(a+(b*c))+(((d*e)+f)*g)
		int openParen = 0;
		int midPoint = 0;
		for(int i = 0; i < infix.length(); i++)
		{
			if(infix.charAt(i) == '(')
				openParen++;
			if(infix.charAt(i) == ')')
				openParen--;
			if(openParen == 0)
			{
				midPoint = i;
				break;
			}
		}
		String[] temp = new String[3];
		temp[0] = infix.substring(0, midPoint+1);//(a+(b*c))
		temp[1] = infix.substring(midPoint+2);//(((d*e)+f)*g)
		temp[2] = ""+infix.charAt(midPoint+1);//+
		return temp;
	}
	public String infixPrinter()
	{
		String temp = "";
		temp += infixPrinterHelper(root);
		return temp;
	}
	private String infixPrinterHelper(Node t)
	{
		String temp = "";
		if(t.left != null)
		{
			temp += "(";
			temp += infixPrinterHelper(t.left);
		}
		temp += t.val;
		if(t.right != null)
		{
			temp += infixPrinterHelper(t.right);
			temp += ")";
		}
		return temp;
	}
	public String postfixPrinter()
	{
		String temp = "";
		temp += postfixPrinterHelper(root);
		return temp;
	}
	private String postfixPrinterHelper(Node t)
	{
		String temp = "";
		if(t.left != null)
			temp += postfixPrinterHelper(t.left);
		if(t.right != null)
			temp += postfixPrinterHelper(t.right);
		temp += t.val;
		return temp;
	}
	public String prefixPrinter()
	{
		String temp = "";
		temp += prefixPrinterHelper(root);
		return temp;
	}
	private String prefixPrinterHelper(Node t)
	{
		String temp = "";
		temp += t.val;
		if(t.left != null)
			temp += prefixPrinterHelper(t.left);
		if(t.right != null)
			temp += prefixPrinterHelper(t.right);
		return temp;
	}
	public String toString()
	{
		String temp = "";

		temp += toStringHelper(root);

		return temp;
	}
	private String toStringHelper(Node t)
	{
		String temp = "";
		temp += "Value: " + t.val + " :: Depth: " + t.depth + "\n";
		if(t.left != null)
		{
			temp += "LEFT SIDE\n";
			temp += toStringHelper(t.left);
		}
		if(t.right != null)
		{
			temp += "RIGHT SIDE\n";
			temp += toStringHelper(t.right);
		}
		return temp;
	}

	private class Node
	{
		public Node left, right;
		public char val;
		public int depth;
		public Node (char v, int d)
		{
			val = v;
			depth = d;
			left = null;
			right = null;
		}
	}
}
