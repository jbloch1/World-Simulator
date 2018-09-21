//File Name: Item.java
//Developers: Jonathan Bernard Bloch
/*Purpose: Items are Moveable, Immovable, or Autonomous. I could just enclose this in Immovable, but then there would be 
List<Immovable>, which is awkward to write and confuses. List<Item> is much better.*/
//Inputs: world, name and toke
//Outputs: none
//Modifications none
//==========
//March 17 2018, April 16 2018 The Item class

public abstract class Item
{
	protected final World world;
	private final String name;
	private final char token;
	private int x, y;
	
	/*
	  Initializes an item. Called from Immovable, Moveable and Autonomous using super method.
	  @param world The world it was intended for.
	  @param name
	  @param token
	 */
	public Item(final World world, final String name, final char token)
	{
		this.world = world;
		this.name = name;
		this.token = token;
		this.x = 0;
		this.y = 0;
	}
	
	/*
	  Default step() method does nothing.
	 */
	public void step()
	{
		
	}
	
	/*
	  Is the item movable from push?
	  return true or false if moveable or not respectively
	 */
	public abstract boolean isMoveable(final Moveable push);

	/*
	  Move the item.
	  @param moveable
	 */
	protected abstract void move(Moveable moveable);

	/*
	  Private string name, describing what it is.
	  @return name
	 */
	public String getName()
	{
		return name;
	}
	
	/*
	  Private char token, a character that stores the symbol that represents the
	  item when printed to the screen.
	  @return token
	 */
	public char getToken()
	{
		return token;
	}
	
	/*
	  Private int x, y, which specifies the location in the array this item exists.
	  @return
	*/
	protected int getX()
	{
		return x;
	}
	
	/*
	  Private int x, y, which specifies the location in the array this item exists.
	  @return
	*/
	protected int getY()
	{
		return y;
	}

	/*
	  Sets x,y. x, y are the key, so be careful that it's not in the world
	  @param x
	  @param y
	 */
	public void setXY(final int x, final int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/*
	  This is a perfect hash for the columnSize x rowSize.
	  It takes in no arguments and it uses the (x, y) coordinate of the 
	  item to compute a unique value to use as an index into the array
	  This is so that I have one authoritative state instead of two
	  or many which is contrary to object-oriented code.
	 */
	@Override
	public final int hashCode()
	{
		return x + y * world.rowSize;
	}
	
	/**
	 * Compares the x, y. This is so hashCode works.
	 * http://www.angelikalanger.com/Articles/JavaSolutions/SecretsOfEquals/Equals-2.html
	 */
	@Override
	public final boolean equals(Object o) {
		Item obj = (Item)o;
		return (obj.x == this.x) && (obj.y == this.y);
	}
	
	  //Debug.
	@Override
	public String toString() {
		return name + " ["+token+"] at ("+x+","+y+")";
	}

}
