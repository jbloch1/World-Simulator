//File Name: World.java
//Developers: Jonathan Bernard Bloch
//Purpose: This is the main program that generates a world, steps 100 steps, and asks the user if they want to continue
//Inputs: random world
//Outputs: 
//Modifications
//==========
//March 17 2018, April 16 2018 The main program.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class MainProgram 
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Loading world...");
		World world = buildWorld(5, 3, 2);
		System.out.println("[done]\n" + world);
		world.display();
		int count = 0;
		
		do
		{
			int max = /*(count==0 ? 26:1)*/100;
			for(int countIteration = 1; countIteration <= max; countIteration++)
			{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				world.step(); count++;
				world.display();
			}
			
			String response;
			while(true)
			{
				System.out.println(world);
				System.out.println("You have run " + count + " steps. Would you like to run the simulation again?");
				response = in.readLine();	
				if(response.equalsIgnoreCase("no") || response.equalsIgnoreCase("yes")) break;
				else System.out.println("Please reply either a yes or a no.");
			}			
			if(response.equalsIgnoreCase("no")) break;

		}while(true);	
		System.out.println("Fine then!! Have a nice day!");
	}
		
	/**
	 * construct a world of and then populate
	 * @throws IndexOutOfBoundsException If there is not enough room.
	 */
	private static World buildWorld(int immovables, int moveables, int autonomous)
	{
		final int rows = 5, columns = 5;
		// ensure the size is enough for every object
		if(columns * rows<immovables+moveables+autonomous) throw new IndexOutOfBoundsException("Not enough room for those objects");
		World world = new World(rows, columns);
		Random ran = new Random(/*0*/); // debugging, always creates random at the same place
		int x, y;

		for(int i = 0; i < immovables; i++) {
			char tok = (char) ('A' + (i % 26));
			String str = "" + tok;
			Immovable imm = new Immovable(world, "Imm-"+str, tok);
			do {
				x = ran.nextInt(world.rowSize);
				y = ran.nextInt(world.columnSize);
			} while(world.look(x, y) != null);
			world.add(imm, x, y);
		}
		for(int i = 0; i < moveables; i++) {
			char tok = (char) ('a' + (i % 26));
			String str = "" + tok;
			Moveable mov = new Moveable(world, "Mov-"+str, tok);
			do {
				x = ran.nextInt(world.rowSize);
				y = ran.nextInt(world.columnSize);
			} while(world.look(x, y) != null);
			world.add(mov, x, y);
		}
		for(int i = 0; i < autonomous; i++) {
			char tok = (char) ('!' + (i % 32));
			String str = "" + tok;
			Autonomous aut = new Autonomous(world, "Aut-"+str, tok);
			do {
				x = ran.nextInt(world.rowSize);
				y = ran.nextInt(world.columnSize);
			} while(world.look(x, y) != null);
			world.add(aut, x, y);
		}
		return world;
	}
}
