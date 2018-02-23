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
		this.G = new ArrayList<>();
		this.E = new ArrayList<>();
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
	
	/**
	 * Returns the total number of vertices in all of the graphs
	 */
	public int totalVertices()
	{
		int total = 0;
		for(Graph g : G)
		{
			total += g.getV().size();
		}
		return total;
	}
	
	/**
	 * Returns the total number of edges in all of the graphs
	 */
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
	private void addGraph(Graph graph)
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
	
	/**
	 * returns the smallest and largest x and y coordinates in an array
	 */
	public double[] minMax()
	{
		double xMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;
		double yMin = Double.MAX_VALUE;
		double yMax = Double.MIN_VALUE;
		for(Graph g : G)
		{
			for(Vertex v : g.getV())
			{
				if (v.getX() < xMin)
				{
					xMin = v.getX();
				}
				else if(v.getX() > xMax)
				{
					xMax = v.getX();
				}
				if (v.getY() < yMin)
				{
					yMin = v.getY();
				}
				else if(v.getY() > yMax)
				{
					yMax = v.getY();
				}
			}
		}
		double[] result = new double[4];
		result[0] = xMin;
		result[1] = yMin;
		result[2] = xMax;
		result[3] = yMax;
		return result;
	}
}