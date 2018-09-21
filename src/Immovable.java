//File Name: Immovable.java
//Developers: Jonathan Bernard Bloch
//Purpose: This is the immovable program.
//Inputs: none
//Outputs: none
//Modifications: none
//==========
//March 17 2018, April 16 2018 The immovable program.

import javax.print.attribute.UnmodifiableSetException;

/**
 * Immovable.
 * @author jbloch1
 *
 */
public class Immovable extends Item 
{
	public Immovable(final World world, final String name, final char token)
	{
		super(world, name, token);
	}
	@Override
	public boolean isMoveable(final Moveable push)
	{
		return false;
	}
	@Override
	public void move(final Moveable push) {
		throw new UnmodifiableSetException("Can't move.");
	}
}
