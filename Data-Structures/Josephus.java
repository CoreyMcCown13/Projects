// Josephus Problem: https://en.wikipedia.org/wiki/Josephus_problem

import java.util.ArrayList;
 
public class Josephus {
	
    public static void main(String[] args){
    	//Who doesn't love some ASCII art?
    	System.out.print(""
    			+ "       _                      _               \n"
    			+ "      | |                    | |              \n"
    			+ "      | | ___  ___  ___ _ __ | |__  _   _ ___ \n"
    			+ "  _   | |/ _ \\/ __|/ _ \\ '_ \\| '_ \\| | | / __|\n"
    			+ " | |__| | (_) \\__ \\  __/ |_) | | | | |_| \\__ \\\n"
    			+ "  \\____/ \\___/|___/\\___| .__/|_| |_|\\__,_|___/\n"
    			+ "                 Corey | | McCown             \n"
    			+ "                       |_|                    \n"
    			+ " # # # # # # # # # # # # # # # # # # # # # # #\n"
    			+ "# # # # # # # # Data Structures # # # # # # # #\n\n");
    	
    	//Testing to make sure the base cases work
    	josephus(0, 5);
    	josephus(1, 5);
    	
    	//Having some fun
    	josephus(3, 100);
    	josephus(25, 1000);
    }
    
    public static void josephus(int m, int n){
    	
        int loser = 0;
        long startTime = System.nanoTime();
        ArrayList<Integer> player = new ArrayList<Integer>(n);
        
        //Add all players (n)
        for(int i = 1; i <= n; i++) player.add(i);
        
        //Display information for this Josephus problem.
        System.out.println("Josephus Problem: " + n + " players, " + (1 + m) + " passes around...");
        
        //Find losing players
        System.out.print("Losing Players (in order): ");
        do {
            loser = (loser + m) % player.size();
            //Last pass if player size is 2, finish the line with a period, display execution time.
            if(player.size() == 2)
            {
            	long endTime = System.nanoTime();
            	System.out.println(player.get(loser) + "\n"
            			+ "Winning Player: " + player.get(0)
            			+ ".\nRun time: " + String.format( "%.6f",((endTime - startTime)/Math.pow(10,9))) + "s\n");
            }
            else System.out.print(player.get(loser) + ", ");
            
            player.remove(loser);
        } while (player.size() > 1); 
    }
}
