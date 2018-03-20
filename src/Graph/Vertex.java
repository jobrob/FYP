package Graph;

import java.util.Random;
import java.util.UUID;

public class Vertex 
{
	Vector position;
	Vector displacement;
	String name;
	Colour colour;
	final UUID id;
	String label;


	/**
	 * Creates a Vertex out of a name and a vector to define it position. gives it a random id.
	 * @param name the name of the vertex
	 * @param position the position of the vertex
	 * @param displacement the displacement of the vertex
	 * @param colour the colour values of the vertex
	 * @param label the label of the vertex to be displayed in the svg
	 */
	public Vertex(String name,Vector position,Vector displacement,Colour colour,String label)
	{
		this.displacement = displacement;
		this.name = name;
		this.colour = colour;
		this.id = UUID.randomUUID();
		this.label = label;
	}
	
	public Vertex(String name,String label)
	{
		this(name,Vector.ZERO,Vector.ZERO,new Colour(),label);
	}

	public Vertex(String name, Vector position,Vector displacement,Colour colour)
	{
		this(name,position,displacement,colour,"");
	}
	
	public Vertex(String name,Vector position,Vector displacement)
	{
		this(name,position,displacement,new Colour());
	}

	public Vertex(String name)
	{
		this(name, Vector.ZERO, Vector.ZERO);
	}
	
	public Vertex(String name,Colour colour)
	{
		this(name,Vector.ZERO,Vector.ZERO,colour);
	}

	public Vertex(String name, Vector position)
	{
		this(name, position, Vector.ZERO);
	}
	
	public Vertex()
	{
		this("");
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
	 * Returns the z value of the Vertex
	 */
	public double getZ()
	{
		return position.getZ();
	}
	
	/**
	 * Returns the name of the Vertex
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the position vector of the vertex
	 */
	public Vector getPosition()
	{
		return position;
	}
	/**
	 * Returns the displacement vector of the vertex
	 */
	public Vector getDisplacement()
	{
		return displacement;
	}
	/**
	 * @return The colour of the Vertex
	 */
	public Colour getColour()
	{
		return colour;
	}
	/**
	 * @return The globaly unique Id for the Vertex
	 */
	public UUID getId()
	{
		return id;
	}

	/**
	 * returns the label of the Vertex
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Sets the vector of the vertex to the one given
	 * @param position the new position vector
	 */
	public void setPosition(Vector position)
	{
		this.position = position;
	}
	
	public void setDisplacement(Vector displacement)
	{
		this.displacement = displacement;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setX(double x)
	{
		Vector newPosition = new Vector(x,getY(),getZ());
		setPosition(newPosition);
	}
	
	public void setY(double y)
	{
		Vector newPosition = new Vector(getX(),y,getZ());
		setPosition(newPosition);
	}
	
	public void setColour(Colour colour)
	{
		this.colour = colour;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	
	/**
	 * Checks to see if two vertices have the same id.
	 * @param v The other vertex.
	 */
	public boolean equals(Vertex v)
	{
		return (v.getId().equals(id));
	}
	
	/**
	 * Return a string representation of this instance consisting of its
	 * name, position, and velocity.
	 */
	public String toString()
	{	
		return(name+": pos = "+position + "disp = " + displacement + "\n ID: " + id);
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
