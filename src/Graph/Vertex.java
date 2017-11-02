public class Vertex
{
	String name;
	Vector vector;
	/**
	 * Creats a Vertex out of a name and a vector to define it position
	 * @param name the name of the vertex
	 * @param vector the position of the vertex
	 */
	public Vertex(String name,Vector vector)
	{
		this.name = name;
		this.vector = vector;
	}
	
	/**
	 * Creates a vertex without a position vector
	 * Automaticly assigns the 0 vector is a assigned
	 * @param name the name of the vertex
	 */
	public Vertex(String name)
	{
		this.name = name;
		this.vector = new Vector(0,0);
	}
	
	/**
	 * Returns the position vector of the vertex
	 */
	public Vector getVector()
	{
		return vector;
	}
	
	/**
	 * Returns the x value of the Vertex
	 */
	public double getX()
	{
		return vector.getX();
	}
	
	/**
	 * Returns the y value of the Vertex
	 */
	public double getY()
	{
		return vector.getY();
	}
	
	/**
	 * Returns the name of the Vertex
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Updates the vector of the vertex
	 * @param vector the new vector
	 */
	public void setVector(Vector vector)
	{
		this.vector = vector;
	}
	
	/**
	 * Checks to see if two vertices are equal
	 * @param vertex. the vertex to be compared to
	 */
	public boolean equals(Vertex v)
	{
		return (v.equals(vector));
	}
	
	/**
	 * returns the vertex as a string in the form of a touple of its name and its vector
	 */
	public String toString()
	{	
		return(name + ":" + vector);
	}
	
	/**
	 * returns a string in the form that draws the vertex in the from required for svg.
	 */
	public String draw()
	{
		return("<!-- " + name + " --> <g id = \"" + name + "class=\"edge\"<ellipse fill=\"none\" stroke=\"#000000\" cx=\"" + vector.getX() + "\" cy=\"" + vector.getY() + "rx=\"1\" ry=\"1\"/>");
	}
}
