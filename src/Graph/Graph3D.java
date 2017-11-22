package Graph;

import java.util.ArrayList;

public class Graph3D 
{
	private ArrayList<Graph> G;
	private ArrayList<Edge3D> E;
	public Graph3D(ArrayList<Graph> G)
	{
		this.G = new ArrayList<Graph>();
		this.E = new ArrayList<Edge3D>();
		for(Graph g : G)
		{
			addGraph(g);
		}
//		for(int i = 0 ; i<G.size()-1; i++)
//		{
//			System.out.println("Im checking the " + i + "th graph");
//			for(int j = 0; j<G.get(i).getV().size(); j++)
//			{
//				System.out.println("");
//				if(G.get(i).isIn(G.get(i).getV().get(j),G.get(i+1)))
//				{
//					System.out.println("Im making a 3D edge");
//					E.add(new Edge3D(G.get(i),G.get(i+1),G.get(i).getV().get(j)));
//				}
//			}
//		}
	}
	
	public ArrayList<Graph> getG()
	{
		return G;
	}
	
	public ArrayList<Edge3D> getE()
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
					E.add (new Edge3D((G.get(G.size()-1)),graph,v));
				}
			}
		}
		G.add(graph);
	}
}