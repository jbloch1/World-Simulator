//File Name: Moveable.java
//Developers: Jonathan Bernard Bloch
/*Purpose: Implemented exactly as Immovable, however it can be moved by one cell position
if bumped into by an Autonomous object. It is displaced in the same direction as
the bump. For example, if the item was bumped on its right side and the motion of
the bump was towards the left, then the item will move to the left.*/
//Inputs: world, name and token
//Outputs: none
//Modifications none
//==========
//March 17 2018, April 16 2018 The Moveable class


public class Moveable extends Immovable 
{
	
	public Moveable(final World world, final String name, final char token)
	{
		super(world, name, token);
	}
	/*
	  Is movable from item push
	 */
	@Override
	public boolean isMoveable(final Moveable push)
	{
		int x = this.getX() + this.getX() - push.getX();
		int y = this.getY() + this.getY() - push.getY();
		if(!world.isValidCoord(x, y)) return false;
		Item i = world.look(x,y);
		return i == null ? true : i.isMoveable(this);
	}
	/*
	  Check isMoveable first to avoid exceptions.
	 */
	@Override
	public void move(final Moveable push) {
		int x = this.getX() + this.getX() - push.getX();
		int y = this.getY() + this.getY() - push.getY();
		// move the others recursively
		Item i = world.look(x,y);
		if(i != null) i.move(this);
		// move this
		world.remove(this);
		this.setXY(x, y);
		world.add(this);
	}
}
