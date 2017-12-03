package Graph;
public class Edge
{
	private Vertex v1;
	private Vertex v2;
	/**
	 * Creates an edge from vertex one to vertex 2
	 * @param v1 the first vertex
	 * @param v2 the second vertex
	 */
	public Edge (Vertex v1, Vertex v2)
	{
		this.v1 = v1;
		this.v2 = v2;
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
	/**
	 * Checks to see if two edges are equal.
	 * The edges are unordered to (v1,v2)==(v2,v1)
	 * @param e the edge being compared to 
	 */
	public boolean equals(Edge e)
	{
		return( e.v1.equals(v1) && e.v2.equals(v2) ) 
		|| ( e.v1.equals(v2) && e.v2.equals(v1) );
	}
	/**
	 * Prints out the edge as a touple of its vectors
	 */
	public String toString()
	{
		return("(" + v1 + "," + v2 + ")");
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
