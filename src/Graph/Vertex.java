package Graph;

import java.util.Random;

public class Vertex 
{
	Vector position;
	String name;
	/**
	 * Creates a Vertex out of a name and a vector to define it position
	 * @param name the name of the vertex
	 * @param vector the position of the vertex
	 */
	public Vertex(String name, Vector position)
	{
		this.position = position;
		this.name = name;
	}
	
	/**
	 * Creates a vertex without a position vector
	 * Automatically assigns the 0 vector is a assigned
	 * @param name the name of the vertex
	 */
	public Vertex(String name)
	{
		this(name, Vector.ZERO);
	}
	
	/**
	 * Returns the x value of the Vertex
	 */
	public double getX()
	{
		return position.getX();
	}
	
	/**
	 * Returns the y value of the Vertex
	 */
	public double getY()
	{
		return position.getY();
	}
	
	/**
	 * Returns the name of the Vertex
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the vector of the vertex
	 */
	public Vector getVector()
	{
		return position;
	}
	/**
	 * Sets the vector of the vertex to the one given
	 * @param postion the new position vector
	 */
	public void setVector(Vector position)
	{
		this.position = position;
	}
	
	/**
	 * Checks to see if two vertices have the same name.
	 * @param v The other vertex.
	 */
	public boolean equals(Vertex v)
	{
		return (v.getName().equals(name));
	}
	
	/**
	 * Return a string representation of this instance consisting of its
	 * name, position, and velocity.
	 */
	public String toString()
	{	
		return(name+": s = "+position);
	}
	
	/**
	 * Randomises the position vector of the vertex
	 * Coordinates are chosen from 0 to i
	 * @param i the the random number cap
	 */
	public void randomise(int i) 
	{
		Random r = new Random();
		double x = r.nextDouble() * i;
		double y = r.nextDouble() * i;
		position = (new Vector(x,y));
	}
}
