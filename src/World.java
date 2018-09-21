import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;

//File Name: World.java
//Developers: Jonathan Bernard Bloch
//Purpose: This is the world. This is implemented as a rowSize x columnSize array 
// backed by a LinkedHashMap (approximately) to enforce a singleton design pattern
//Inputs: rowSize and columnSize initially, items with add
//Outputs: Internal state
//Modifications
//==========
//March 17 2018, April 16 2018 The World object is composed of cells.




public class World extends Stew<Item>
{
	private JFrame frame = null; // A window
	private JLabel labels[][];

	public final int rowSize, columnSize;
			
	// Name: method name
	// Developers: same as in file
	// Purpose: Sets up a blank world.
	// Inputs:@param rowSize
	// @param columnSize
	// Outputs: None
	// Side-effects: Creates world
	// Special notes: None
	public World(final int rowSize, final int columnSize)
	{
		super(rowSize * columnSize);
		this.rowSize = rowSize;
		this.columnSize = columnSize;
		prepareGui();
	}
	
	// Name: method name
	// Developers: same as in file
	/* Purpose:
		A method called public void step() that iterates through the cells of the
		array changing the state of the world by updating the position of all the
	  	Autonomous and Moveable objects (see below). It does this once for each call to
	  	the method. An autonomous object can receive a boost from other autonomous objects.
	  	if it they are earlier in the list, causing it to move two/several frames.
	 	It looks like it teleported, however items are called once, not possibly multiple
	 	times like if I did it for each cell directly.
	 */
	// Inputs: none
	// Outputs: None
	// Side-effects: Steps thought one iteration
	// Special notes: None
	public void step()
	{
		// Concurrent modification prevents: forEach(i -> i.step())
		Stack<Item> stack = new Stack<>();
		forEach(item -> stack.add(item));
		while(!stack.empty()) stack.pop().step();
	}
	
	// Name: method name
	// Developers: same as in file
	// Purpose: Look in the world.
	// Inputs: x, y
	// Outputs: The item at the coordinates.
	// Side-effects: Steps thought one iteration
	// Special notes: None
	public Item look(final int x, final int y)
	{
		if(!isValidCoord(x, y)) return null;
		// i is a dummy only used for it's hashCode()
		Item i = new Immovable(this, "i", 'i');
		i.setXY(x, y);
		return get(i);
	}
	
	
	// Name: method name
	// Developers: same as in file
	/* Purpose: The method public void add(item,x,y) is used to populate the world
	  by adding items to the array at cell x,y. The cell needs to be available (empty) or
	  the add fails.*/
	// Inputs: item, x column, y row
	// Outputs: The item at the coordinates.
	// Side-effects: Steps thought one iteration
	// Special notes: throws NullPointerException, IndexOutOfBoundsException, ArrayStoreException when the array is occupied
	public void add(Item item, int x, int y) throws NullPointerException, IndexOutOfBoundsException, ArrayStoreException
	{
		if(item == null) throw new NullPointerException();
		if(item.world != this) throw new ArrayStoreException(item + " is from another world.");
		if(!isValidCoord(x, y)) throw new IndexOutOfBoundsException(item + " add ("+x+", "+y+") invalid coordinates.");
		if(look(x, y) != null) throw new ArrayStoreException(item + " add ("+x+", "+y+") array cell is already occupied.");
		// remove old position
		if(contains(item)) remove(item);
		item.setXY(x, y);
		if(!add(item)) throw new ArrayStoreException("Couldn't add " + item);
		//System.err.println("Adding "+item+"; Added " + look(x, y));
	}
	
	/*
	  Is this a valid coord?
	  @param x
	  @param y
	  @return
	 */
	public boolean isValidCoord(int x, int y)
	{
		if(x < 0 || y < 0) return false;
		else if(x >= rowSize || y >= columnSize) return false;
		else return true;
	}

	/*
	  The method public void display() to display the world on the screen
	  using Swing or JavaFX. This must be a GUI grid displaying simple text tokens
	  that represent the items in the world.
	 */
	public void display()
	{
		char tokens[][] = new char[rowSize][columnSize];
		for(int x = 0; x < rowSize; x++) {
			for(int y = 0; y < columnSize; y++) {
				tokens[x][y] = ' ';
			}
		}
		for(Item i : this) tokens[i.getX()][i.getY()] = i.getToken();
		for(int x = 0; x < rowSize; x++) {
			for(int y = 0; y < columnSize; y++) {
				labels[x][y].setText(""+tokens[x][y]);
			}
		}
	}
	
	  //Called from constructor
	private void prepareGui()
	{
		frame = new JFrame("World Display");
		frame.setSize(50 * rowSize, 50 * columnSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(columnSize,rowSize, 5, 5));
		frame.setVisible(true);
		labels = new JLabel[rowSize][columnSize];
		for(int x = 0; x < rowSize; x++) {
			for(int y = 0; y < columnSize; y++) {
				JLabel a = new JLabel("*");
				a.setHorizontalAlignment(JLabel.CENTER);
				frame.add(a);
				labels[x][y] = a;
			}
		}
	}
	
	// Debug.
	@Override
	public String toString() {
		String a = ""/*"<<World.toString\n"*/;
		for(int x = 0; x < rowSize; x++) {
			for(int y = 0; y < columnSize; y++) {
				Item i = look(x, y);
				a += i == null ? ' ' : i.getToken();
			}
			a += '\n';
		}
		//a += super.toString() + "World.toString>>\n";
		return a;
	}

}

