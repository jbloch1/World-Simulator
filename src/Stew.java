import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

//File Name: Stew.java
//Developers: Jonathan Bernard Bloch
/*Purpose: Modeled after HashSet by Josh Bloch and Neal Gafter.
	I changed some stuff from HashSet to implement different objects
	having the same hashCode. This is forced to by a perfect hash.
	Also, any object which has a hashCode above the value supplied
	by the initial constructor can not be stored.*/
//Inputs: iniialCapacity
//Outputs: None
//Modifications: None
//=========================================================================================
//March 17 2018, April 16 2018 The main program.


/*
	Modeled after HashSet by Josh Bloch and Neal Gafter.
 	I changed some stuff from HashSet to implement different objects
 	having the same hashCode. This is forced to by a perfect hash.
 	Also, any object which has a hashCode above the value supplied
 	by the initial constructor can not be stored.
 	<ul>
 	<li>No support for things like serializable.</li>
 	<li>The add function refuses add in a space (hashCode) that's occupied.</li>
 	<li>Get.</li>
 	</ul>
 */

public class Stew<E>
	extends AbstractSet<E>
	implements Set<E>
{
	final int bins;
	
	private transient LinkedHashMap<E,E> map;
	/*
	  Constructs a new, empty Stew.
	  @param initialCapacity    The maximum size of an
	  object's hashcode that you can store.
	 */
	public Stew(int initialCapacity) {
        map = new LinkedHashMap<E,E>(initialCapacity);
        bins = initialCapacity;
    }
	
	  //Iterator.
	public Iterator<E> iterator() {
        return map.keySet().iterator();
    }
	/**
	 * Size.
	 */
	public int size() {
        return map.size();
    }
	/**
	 * Is empty?
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}
	/**
	 * Contains?
	 */
	public boolean contains(Object o) {
        return map.containsKey(o);
    }
	/**
	 * Add.
	 * @return True if the value was set. False if another
	 * value is in the hashCode or if the hashCode is higher
	 * than the initialCapacity.
	 */
	public boolean add(E e) {
		if(map.containsKey(e)) return false;
		if(e.hashCode() >= bins) return false;
        if(map.put(e, e) != null) System.err.println("Error: " + e);;
        return true;
    }
	/**
	 * Remove.
	 */
	public boolean remove(Object o) {
        return map.remove(o) != null;
	}
	/**
	 * Clear.
	 */
    public void clear() {
        map.clear();
    }
    /**
     * Get.
     * @param proto   The prototype of this object, used for it's hashcode only.
     * @return Either null or proto.equals(return).
     */
    public E get(final E proto) {
    	return map.get(proto);
    }
    /**
     * Debug.
     */
    public String toString() {
    	String s = "Stew:\n";
    	for(Entry<E, E> e : map.entrySet()) {
    		s += "--" + e.getKey() + " {" + e.getKey().hashCode() + "}->" + e.getValue() + "\n";
    	}
    	return s;
    }
}
