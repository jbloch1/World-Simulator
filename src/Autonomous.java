import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//File Name: Autonomous.java
//Developers: Jonathan Bernard Bloch
/*Purpose: This is the Autonomous class, implemented exactly like Moveable 
(bumped by a Moveable object or another Autonomous object causes it to shift one cell in the direction it was bumped).*/
//Inputs: world, name and token
//Outputs: none
//Modifications none
//==========
//March 17 2018, April 16 2018 The Autonomous class

public final class Autonomous extends Moveable {

	static Random ran = new Random(0);
	
	public Autonomous(final World world, final String name, final char token)
	{
		super(world, name, token);
	}
			
	// vector for private movement list
	private class Vector {
		final int x, y;
		Vector(final int x, final int y) {
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return "("+x+","+y+")";
		}
	}

	private Vector direction(final int x, final int y) {
		if(!world.isValidCoord(x, y)) return null;
		// test item
		Item test = new Immovable(world, "Test", 'x');
		test.setXY(x, y);
		// real item
		Item item = world.get(test);
		return item == null || item.isMoveable(this) ? new Vector(x, y) : null;
	}
	
	private List<Vector> getExits() {
		List<Vector> exits = new ArrayList<>();
		Vector e;
		if((e = direction(getX() + 1, getY())) != null) exits.add(e);
		if((e = direction(getX() - 1, getY())) != null) exits.add(e);
		if((e = direction(getX(), getY() + 1)) != null) exits.add(e);
		if((e = direction(getX(), getY() - 1)) != null) exits.add(e);
		return exits;
	}
	
	/*
	  A public void step() method that randomly picks a direction and updates
	  the item to a new location by one cell.
	 */
	@Override
	public void step()
	{
		List<Vector> exits = getExits();
		//System.err.println("Automomous "+this+" has exits "+exits);
		// we can't go anywhere
		if(exits.isEmpty()) return;
		// pick a random direction
		Vector exit = exits.get(ran.nextInt(exits.size()));
		Item bump = world.look(exit.x, exit.y);
		if(bump != null) bump.move(this);
		world.remove(this);
		this.setXY(exit.x, exit.y);
		world.add(this);
	}
	
}
