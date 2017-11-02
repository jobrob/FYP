import java.util.ArrayList;
public class Graph
{
	private ArrayList<Vertex> V;
	private ArrayList<Edge> E;
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
	
	public ArrayList<Vertex> getV()
	{
		return V;
	}
	
	public ArrayList<Edge> getE()
	{
		return E;
	}
	
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
