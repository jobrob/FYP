import java.util.ArrayList;
public class Graph
{
	private ArrayList<Vertex> V;
	private ArrayList<Edge> E;
	
	/*
	Constructs a graph out of a collection of vertices and edges
	@param V the collection of vertices
	@param E the collection of edges
	*/
	public Graph(Vertex[] V,Edge[] E) throws Exception
	{
		for(Vertex v : V)
		{
			addVertex(v);
		}
		for(Edge e : E)
		{
			addEdge(e);
		}
	}
	
	/*
	Returns the collection of vertices
	*/
	public ArrayList<Vertex> getV()
	{
		return V;
	}
	
	/*
	Returns the collection of edges
	*/
	public ArrayList<Edge> getE()
	{
		return E;
	}
	
	/*
	Adds a vertex to the collection of vertices and asserts that its not already in V
	@param vertex the vertex being added
	*/
	public void addVertex(Vertex vertex) throws Exception
	{
		for(Vertex v : V)
		{
			if(v.equals(vertex))
			{
				throw new Exception("Vertex " + vertex + " has the same name as a vertex in the graph");
			}
		}
		V.add(vertex);
	}
	
	/*
	Adds an edge to the collection of edges and asserts that its not already in E
	@param edge the edge to be added
	*/
	public void addEdge(Edge edge) throws Exception
	{
		for(Edge e : E)
		{
			if (edge.equals(e))
			{
				throw new Exception("Edge " + edge + " already exists in this graph");
			}
		}
		E.add(edge);
	}
	
	/*
	Returns the arraylist of neighbours to v
	@param v the vertex to have its neighbours computed
	*/
	public ArrayList<Vertex> neighbourhood(Vertex v)
	{
		ArrayList<Vertex> neighbourhood = new ArrayList<Vertex>();
		for(Edge e : E)
		{
			if(e.contains(v))
			{
				if(e.getV1().equals(v))
				{
					neighbourhood.add(e.getV2());
				}
				else
				{
					neighbourhood.add(e.getV1());	
				}
			}
		}
		return neighbourhood;
	}
	//public String draw()
	//{
		
	//}
}
