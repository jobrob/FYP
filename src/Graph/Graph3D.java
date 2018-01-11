package Graph;

import java.util.ArrayList;

public class Graph3D 
{
	private ArrayList<Graph> G;
	private ArrayList<Edge> E;
	
	/**
	 * Creates a graph sequence and creats the edges between the graphs.
	 * @param G
	 */
	public Graph3D(ArrayList<Graph> G)
	{
		this.G = new ArrayList<Graph>();
		this.E = new ArrayList<Edge>();
		for(Graph g : G)
		{
			addGraph(g);
		}
	}
	/**
	 * Returns the list of the graphs in the sequence
	 * @return
	 */
	public ArrayList<Graph> getG()
	{
		return G;
	}
	
	/**
	 * Returns the list of the edges between the graphs in the sequence.
	 * @return
	 */
	public ArrayList<Edge> getE()
	{
		return E;
	}
	
	public int totalVertices()
	{
		int total = 0;
		for(Graph g : G)
		{
			total += g.getV().size();
		}
		return total;
	}
	
	public int totalEdges()
	{
		int total = 0;
		for(Graph g : G)
		{
			total += g.getE().size();
		}
		return total;
	}
	
	/**
	 * Adds a graph to the sequence and checks the previous graph to place an edge between them 
	 * whenever there is the same vertex in both graphs
	 * @param graph
	 */
	public void addGraph(Graph graph)
	{
		if (G.size()>0)
		{
			for(Vertex v : G.get(G.size()-1).getV())
			{
				if(graph.isIn(v))
				{
					E.add(new Edge(v,graph.getVertex(v.getName())));
				}
			}
		}
		G.add(graph);
	}
}