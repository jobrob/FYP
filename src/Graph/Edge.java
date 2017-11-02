public class Edge
{
	private Vertex v1;
	private Vertex v2;
	
	public Edge (Vertex v1, Vertex v2)
	{
		assert(v1.equals(v2));
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public Vertex getV1()
	{
		return v1;
	}
	
	public Vertex getV2()
	{
		return v2;
	}
	
	public boolean equals(Edge e)
	{
		return( e.v1.equals(v1) && e.v2.equals(v2) ) 
		|| ( e.v1.equals(v2) && e.v2.equals(v1) );
	}
	
	public String toString()
	{
		return("(" + v1 + "," + v2 + ")");
	}
	
	public boolean contains(Vertex v)
	{
		return(v1.equals(v) || v2.equals(v));
	}
	
	public String draw()
	{
		return("")
	}
} 
