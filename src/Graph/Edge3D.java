package Graph;

public class Edge3D
{
	private Graph g1;
	private Graph g2;
	private Vertex v;
	
	public Edge3D(Graph g1, Graph g2, Vertex v)
	{
		this.g1 = g1;
		this.g2 = g2;
		this.v = v;
	}
	
	public Graph getG1()
	{
		return g1;
	}
	
	public Graph getG2()
	{
		return g2;
	}
	
	public Vertex getV()
	{
		return v;
	}
}
