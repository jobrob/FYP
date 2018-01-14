package Graph;
import java.util.ArrayList;
import java.util.Random;
public class Graph implements Cloneable
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
		vertex.setName("" + V.size()+1);
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
			/*if(e.equals(edge))
			{
				break;
			}*/
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
	
	/**
	 * Finds the values of the minimum and maximium coodinates in the graph
	 * @return
	 */
	public double[] minMax()
	{
		double xMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;
		double yMin = Double.MAX_VALUE;
		double yMax = Double.MIN_VALUE;
		for(Vertex v : V)
		{
			if(v.getX() < xMin)
			{
				xMin = v.getX();
			}
			if(v.getY() < yMin)
			{
				yMin = v.getY();
			}
			if(v.getX() > xMax)
			{
				xMax = v.getX();
			}
			if(v.getY() > yMax)
			{
				yMax = v.getY();
			}
		}
		double[] result = new double[4];
		result[0] = xMin;
		result[1] = yMin;
		result[2] = xMax;
		result[3] = yMax;
		return result;
	}

	/**
	 * Checks the graph to see if a vertex is in the graph.
	 */
	public boolean isIn(Vertex vertex) 
	{
		for(Vertex v : V)
		{
			if (v.getName().equals(vertex.getName()))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks a graph to see if it contains an edge.
	 */
	public boolean isIn (Edge edge)
	{
		for (Edge e : E)
		{
			if(e.equals(edge))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds a vertex in the graph with the name passed to it. Returns null if the name isnt found 
	 * @param name The name that will be looked for
	 * @return The vertex with the name in the graph
	 */
	public Vertex getVertex(String name) 
	{
		for(Vertex v : V)
		{
			if(v.name.equals(name))
			{
				return v;
			}
		}
		return null;
	}
	
	/**
	 * Returns an exact copy of the graph
	 * @return
	 */
	public Graph copy()
	{
		ArrayList<Vertex> copyV = new ArrayList<Vertex>();
		ArrayList<Edge> copyE = new ArrayList<Edge>();
		for(Vertex v : V)
		{
			copyV.add(new Vertex(v.getName(),v.getPosition(),Vector.ZERO,v.getColour()));
		}
		for(Edge e : E)
		{
			Vertex newV = null;
			Vertex newU = null;
			for(Vertex v : copyV)
			{
				if(v.getName().equals(e.getV1().getName()))
				{
					newV = v;
				}
				else if(v.getName().equals(e.getV2().getName()))
				{
					newU = v;
				}
			}
			copyE.add(new Edge(newV,newU));
		}
		return new Graph(copyV,copyE);
	}
	
	/**
	 * Genarates a random graph with a random amounts of nodes and vertices. 
	 */
	public static Graph randomGraph()
	{
		ArrayList<Vertex> Ver = new ArrayList<Vertex>();
		ArrayList<Edge> Edg = new ArrayList<Edge>();
		Graph graph = new Graph(Ver,Edg);
		ArrayList<Vertex> V = graph.getV();
		ArrayList<Edge> E = graph.getE();
		Vertex v1 = new Vertex();
		Vertex v2 = new Vertex();
		Vertex v3 = new Vertex();
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);
		Edge e1 = new Edge(v1,v2);
		Edge e2 = new Edge(v2,v3);
		graph.addEdge(e1);
		graph.addEdge(e2);
		Random rand = new Random();
		int i = rand.nextInt(100);
		while(i!= 99)
		{
			System.out.println("The value of the random int is " + i);
			System.out.println("The number of vertexcies:" + graph.getV().size());
			if (i<50)
			{
				Vertex newRandomVertex = new Vertex();
				int j = rand.nextInt(V.size());
				graph.addVertex(newRandomVertex);
				Edge newRandomEdge = new Edge(newRandomVertex,V.get(j));
				graph.addEdge(newRandomEdge);
			}
			else if(i< 75)
			{
				Vertex newRandomVertex = new Vertex();
				int j = rand.nextInt(V.size());
				int k = rand.nextInt(V.size());
				graph.addVertex(newRandomVertex);
				Edge newRandomEdge1 = new Edge(newRandomVertex,V.get(j));
				Edge newRandomEdge2 = new Edge(newRandomVertex,V.get(k));
				graph.addEdge(newRandomEdge1);
				graph.addEdge(newRandomEdge2);
			}
			else if (75<= i )
			{
				int j = rand.nextInt(V.size());
				int k = rand.nextInt(V.size());
				while(k == j)
				{
					k = rand.nextInt(V.size());
				}
				Edge newRandomEdge = new Edge(V.get(j),V.get(k));
				graph.addEdge(newRandomEdge);
			}
			i = rand.nextInt(100);
		}
		System.out.println("After generating a random graph the number of vectors is " + graph.getV().size());
		System.out.println("After generating a random graph the number of edges is " + graph.getE().size());
		return graph;
	}
	
	/**
	 * Randomly mutates a graph with a 3/4 chance of adding a node and a 1/4 chance of adding a edge. Returns a new graph which is * a copy of the graph mutated
	 * @param Graph g the graph provided
	 */
	public static Graph mutate(Graph g)
	{
		Graph graph = g.copy();
		Random rand = new Random();
		int i = rand.nextInt(100);
		if(i <75)
		{
			Vertex newRandomVertex = new Vertex();
			int j = rand.nextInt(graph.getV().size());
			graph.addVertex(newRandomVertex);
			graph.addEdge(new Edge(newRandomVertex,graph.getV().get(j)));
		}
		else
		{
			int j = rand.nextInt(graph.getV().size());
			int k = rand.nextInt(graph.getV().size());
			while(k == j)
			{
				k = rand.nextInt(graph.getV().size());
			}
			Edge newRandomEdge = new Edge(g.getV().get(j),graph.getV().get(k));
			if(!graph.isIn(newRandomEdge))
			{
				graph.addEdge(newRandomEdge);
			}
		}
		return graph;
	}
	
	public void shift (double min)
	{
		for(Vertex v : V)
		{
			v.setX(v.getX() + min);
		}
	}
}