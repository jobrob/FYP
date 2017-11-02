public class Vertex
{
	String name;
	Vector vector;
	
	public Vertex(String name,Vector vector)
	{
		this.name = name;
		this.vector = vector;
	}
	
	public Vertex(String name)
	{
		this.name = name;
		this.vector = new Vector(0,0);
	}
	
	public Vector getVector()
	{
		return vector;
	}
	
	public double getX()
	{
		return vector.getX();
	}
	
	public double getY()
	{
		return vector.getY();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setVector(Vector vector)
	{
		this.vector = vector;
	}
	
	public boolean equals(Vertex v)
	{
		return (v.equals(vector));
	}
	
	public String toString()
	{	
		return(name + ":" + vector);
	}
	public String draw()
	{
		return("<!-- " + name " --> <g id = \"" + name + "class=\"edge\"<ellipse fill=\"none\" stroke=\"#000000\" cx=\"" + vector.getX() + "\" cy=\"" + vector.getY()"rx=\"1\" ry=\"1\"/>");
	}
}
