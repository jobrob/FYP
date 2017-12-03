package Graph;

import java.util.ArrayList;

public class Graph3D 
{
	private ArrayList<Graph> G;
	private ArrayList<Edge> E;
	public Graph3D(ArrayList<Graph> G)
	{
		this.G = new ArrayList<Graph>();
		this.E = new ArrayList<Edge>();
		for(Graph g : G)
		{
			addGraph(g);
		}
	}
	
	public ArrayList<Graph> getG()
	{
		return G;
	}
	
	public ArrayList<Edge> getE()
	{
		return E;
	}
	public void addGraph(Graph graph)
	{
		if (G.size()>0)
		{
			for(Vertex v : G.get(G.size()-1).getV())
			{
				if(graph.isIn(v))
				{
					System.out.println("I think that " + v.getId() + " is the same as " + graph.getVertex(v.getName()).getId());
					E.add(new Edge(v,graph.getVertex(v.getName())));
				}
			}
		}
		G.add(graph);
	}
}