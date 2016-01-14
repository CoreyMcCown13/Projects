import java.util.Scanner;

/* 
 * Ask the user for 2 inputs
 * First will be a keyword - infix/postfix
 * Second will be a math formula in the correct format
 * Convert from the given format to the other format
 * Solve the postfix version of the math formula
 * 
 * Allowed math symbols: ()/*%-+^
 * 
 */

public class Project4 {

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String choice = null, formula = null;
		//Make sure input is valid
		while(choice == null || (!choice.equals("infix") && !choice.equals("postfix")))
		{
			System.out.println("Select Postfix or Infix -");
			System.out.print(" > infix / postfix: ");
			choice = scanner.nextLine();
		}
		System.out.print("Enter your formula in " + choice + " notation: ");
		formula = scanner.nextLine();
		
		//Run the convert method to get it switched
		System.out.println(convertFormula(choice, formula));
	}

	private static String convertFormula (String type, String formula)
	{ 
		String convertedFormula = "";
		if(type.equals("infix"))
		{
			convertedFormula += "\"" + formula + "\" in postfix notation: ";
			String postfix = inToPost(formula);
			convertedFormula += postfix;
			Scanner scanner = new Scanner(System.in);
			String choice = null;
			//Make sure input is valid
			while(choice == null || (!choice.equals("y") && !choice.equals("n")))
			{
				System.out.println("Evaluate Numerical Values of Postfix Equation? -");
				System.out.print(" > y / n: ");
				choice = scanner.nextLine();
			}
			if(choice.equals("y")) convertedFormula += "\nSolved Equation: " + solvePostfix(postfix);
		} else {
			convertedFormula += "\"" + formula + "\" in postfix notation: ";
			convertedFormula += postToIn(formula);
		}
		return convertedFormula; 
	}

	public static double solvePostfix(String postfix)  
	{  
		double tempDouble = 0, temp1, temp2;    
		myStack<Double> thisStack = new myStack<Double>();  
		
		for(int i = 0; i < postfix.length(); i++) {  
			char thisChar = postfix.charAt(i);  
			if(thisChar >= '0' && thisChar <= '9'){
				thisStack.push((double)(thisChar-'0'));  
			} else {  
				temp1 = thisStack.pop();  
				temp2 = thisStack.pop();  
				switch(thisChar)  
				{  
					case '+':
						tempDouble = temp1 + temp2;  
						break;  
					case '-':
						tempDouble = temp2 - temp1;  
						break;  
					case '*':
						tempDouble = temp1 * temp2;  
						break;  
					case '/':
						tempDouble = temp2 / temp1;  
						break;  
					case '^':
						tempDouble = Math.pow(temp1, temp2);  
						break;  
					default:
						tempDouble=0;  
				}  
				thisStack.push(tempDouble);  
			}  
		}  
		return thisStack.pop();   
	}  
	
	private static String inToPost(String infix)
	{
		String postfix = "";
		myStack<Character> thisStack = new myStack<Character>();
		
		//This character marks the end of the stack
		//So that we can iterate everything at the end.
		thisStack.push('.');

		for(int i = 0; i < infix.length(); i++){
			char thisChar = infix.charAt(i);
			
			//Find if the character is an operator or not
			if(isOperator(thisChar)){
				//If it is an operator, check the priority of it and pop it if it has priority
				while(findPriority(thisChar, thisStack.peek())) 
					postfix += thisStack.pop();
				
				//Push it if it does not
				thisStack.push(thisChar);
			} else if(thisChar == '(') {
				//Character is an open parenthesis
				thisStack.push(thisChar);
			} else if(thisChar == ')') {
				//If the character is a closed parenthesis pop it, unless it is another open parenthesis
				while(thisStack.peek() != '(') 
				{
					postfix += thisStack.pop();
				}
				thisStack.pop();
			} else {
				//If it is not an operator or parenthesis, it's a value... just add it to the string.
				postfix += thisChar;
			} 
		}

		//Pops the rest of the elements until it is at the end of the stack
		//.peek() != null threw a runtime error, this is a work around
		while(thisStack.peek() != '.') {
			postfix += thisStack.pop(); 
		}
		return postfix;
	}
	
	public static String postToIn(String postfix){
		myStack<String> thisStack = new myStack<String>();

		//Iterate through the entire postfix string
		for(int i = 0; i < postfix.length(); i++){
			char temp1 = postfix.charAt(i);
			if(isOperator(temp1)){
				String temp2 = thisStack.pop();
				String temp3 = thisStack.pop();
				thisStack.push("(" + temp3 + temp1 + temp2 + ")");
			} else thisStack.push("" + temp1);
		}
		
		return thisStack.pop();
	}

	//Determines if a character is an operator or not
	private static boolean isOperator(char thisChar){
		//Acceptable characters: +-*/^
		return (thisChar == '+' || thisChar == '-' || thisChar == '*' || thisChar =='/' || thisChar == '^');

	}

	private static boolean findPriority(char c1, char c2){
		if((c2 == '+' || c2 == '-') && (c1 == '+' || c1 == '-'))
			return true;
		else if((c2 == '*' || c2 == '/') && (c1 == '+' || c1 == '-' || c1 == '*' || c1 == '/'))
			return true;
		else if((c2 == '^') && (c1 == '+' || c1 == '-' || c1 == '*' || c1 == '/'))
			return true;
		else
			return false;
	}
}
