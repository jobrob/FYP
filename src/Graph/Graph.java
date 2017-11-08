package Graph;
import java.util.ArrayList;
public class Graph
{
	private ArrayList<Vertex> V;
	private ArrayList<Edge> E;
	
	/**
	 *Constructs a graph out of a collection of vertices and edges
	 *@param V the collection of vertices
	 *@param E the collection of edges
	 */
	public Graph(ArrayList<Vertex> V,ArrayList<Edge> E) 
	{
		this.V = new ArrayList<Vertex>();
		this.E = new ArrayList<Edge>();
		for(Vertex v : V)
		{
			addVertex(v);
		}
		for(Edge e : E)
		{
			addEdge(e);
		}
	}
	
	/**
	 *Returns the collection of vertices
	 */
	public ArrayList<Vertex> getV()
	{
		return V;
	}
	
	/**
	 *Returns the collection of edges
	 */
	public ArrayList<Edge> getE()
	{
		return E;
	}
	
	/**
	 *Adds a vertex to the collection of vertices and asserts that its not already in V
	 *@param vertex the vertex being added
	 */
	public void addVertex(Vertex vertex) 
	{
		for(Vertex v : V)
		{
			assert(!v.equals(vertex)) : "Vertex with same name error";
		}
		V.add(vertex);
	}
	
	/**
	 * Adds an edge to the collection of edges and asserts that its not already in E
	 * @param edge the edge to be added
	 */
	public void addEdge(Edge edge)
	{
		for(Edge e : E)
		{
			assert(!e.equals(edge)) : "Two same Edges";
			//if (edge.equals(e))
			//{
			//	throw new Exception("Edge " + edge + " already exists in this graph");
			//}
		}
		E.add(edge);
	}
	
	/**
	 * Returns the arraylist of neighbours to v
	 * @param v the vertex to have its neighbours computed
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

	/**
	 * Fills the graph with the vertices and edges of a
	 * fully connected graph with n vertices 
	 * @param n the number of vertices in the k graph
	 */
	public static Graph K(int n)
	{
		assert n > 0;
		
		ArrayList<Vertex>	V = new ArrayList<Vertex>();
		ArrayList<Edge>		E = new ArrayList<Edge>();
		
		for(int i = 0; i < n; i++)
		{
			V.add(new Vertex(""+i));
		}
		for(int i = 0; i < n; i++) 
		{
			for(int j = i+1; j < n; j++)
			{
				E.add(new Edge(V.get(i), V.get(j)));
			}
		}
		return new Graph(V,E);
	}
	
}