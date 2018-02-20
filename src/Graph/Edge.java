package Graph;
public class Edge
{
	private Vertex v1;
	private Vertex v2;
	private String colour;
	private String label;
	
	public Edge (Vertex v1, Vertex v2, String colour, String label)
	{
		if (v1 == null || v2 == null) {
			System.err.println("Some vertex in edge is `null`.");
		}
		this.v1 = v1;
		this.v2 = v2;
        if (v1.equals(v2))
        {
            System.out.println(" Edges must have 2 unique vertices" + v1 + " is equal to " + v2);
        }
		this.colour = colour;
		this.label = label;
	}
	
	/**
	 * Creates an edge from vertex one to vertex 2
	 * @param v1 the first vertex
	 * @param v2 the second vertex
	 */
	public Edge (Vertex v1, Vertex v2)
	{
		this(v1,v2,"","");
	}
	
	/**
	 * Returns the first vertex
	 */
	public Vertex getV1()
	{
		return v1;
	}
	
	/**
	 * Returns the second vertex
	 */
	public Vertex getV2()
	{
		return v2;
	}
	
	public String getColour()
	{
		return colour;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setColour(String colour)
	{
		this.colour = colour;
	}
	
	/**
	 * Checks to see if two edges are equal.
	 * The edges are unordered so (v1,v2)==(v2,v1)
	 * @param e the edge being compared to 
	 */
	public boolean equals(Edge e)
	{
        return(((e.getV1().equals(this.v1)) && (e.getV2().equals(this.v2))) || ((e.getV2().equals(this.v1) &&  (e.getV1().equals(this.v2)))));
	}
	
	/**
	 * Prints out the edge as a touple of its vectors
	 */
	public String toString()
	{
		return("( Name:" + v1.getName() + ", Id:" + v1.getId() + "\n" + "Name:" + v2.getName() + ",Id:" + v2.getId() + ")");
	}
	
	/**
	 * Checks to see if the edge contains a vertex in eather of its positions.
	 * @param v the vertex being comparerd to
	 */
	public boolean contains(Vertex v)
	{
		return(v1.equals(v) || v2.equals(v));
	}
} 
