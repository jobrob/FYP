package Graph;
import java.util.*;
import java.util.function.Predicate;

import Util.CharacterEscape;


public class Graph implements Cloneable
{
	private ArrayList<Vertex> V;
	private ArrayList<Edge> E;
	private ArrayList<Subgraph> Sg;

	/**
	 * Contructs a graph out of a collection of vertices, edges and subgraphs
 	 *@param V the list of vertices
	 *@param E the list of edges
	 *@param Sg the list of subgraphs
	 */
	public Graph(ArrayList<Vertex> V, ArrayList<Edge> E,ArrayList<Subgraph> Sg)
	{
		this.V = new ArrayList<>();
		this.E = new ArrayList<>();
		this.Sg = Sg;
		for(Vertex v : V)
		{
			addVertex(v);
		}
		for(Edge e : E)
		{
			addEdge(e);
		}
	}

	public Graph(ArrayList<Vertex> V,ArrayList<Edge> E)
	{
		this(V,E,new ArrayList<Subgraph>());
	}

	/**
	 *Returns the list of vertices
	 */
	public ArrayList<Vertex> getV()
	{
		return V;
	}

	/**
	 *Returns the list of edges
	 */
	public ArrayList<Edge> getE()
	{
		return E;
	}

	/**
	 *Returns the list of subgraphs
	 */
	public ArrayList<Subgraph> getSg()
	{
		return Sg;
	}


	/**
	 *Adds a vertex to the collection of vertices and checks that its not already in V
	 *@param vertex the vertex being added
	 */
	public void addVertex(Vertex vertex) 
	{
		if(vertex.getName().equals(" "))
		{
			vertex.setName("" + V.size()+1);
		}
		for(Vertex v : V)
		{
			if(v.getName().equals(vertex.getName()))
			{
				new Exception().printStackTrace();
                System.out.println("Error two vertices " + v.getName() + " and " + vertex.getName() + " are the same");
			}
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
			if(e.equals(edge))
            {
            	e.setRepeats(e.getRepeats() + 1);
            	e.addLabel(e.getLabels().get(0));
            	return;
            }
		}
		if(!V.contains(edge.getV1()))
		{
				new Exception().printStackTrace();
                System.out.println("Vertex " + edge.getV1() + " is not in the graph");
		}
		if(!V.contains(edge.getV2()))
		{
				new Exception().printStackTrace();
				System.out.println("Vertex " + edge.getV2() + " is not in the graph");
		}
		E.add(edge);
	}

	/**
	 *Adds a subgraph to the list of subgraphs
	 *@param sg the subgraph being added 
	 */
	public void addSubgraph(Subgraph sg)
	{
		Sg.add(sg);
	}

	/**
	 *Returns the arraylist of neighbours to v
	 *@param v the vertex to have its neighbours computed
	 */
	public ArrayList<Vertex> neighbourhood(Vertex v)
	{
		ArrayList<Vertex> neighbourhood = new ArrayList<>();
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

		ArrayList<Vertex>	V = new ArrayList<>();
		ArrayList<Edge>		E = new ArrayList<>();

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
	 * @returns double[xmin,ymin,xmax,ymax]
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
	 * Checks weather a vertex with the name "String Name" is in the graph
	 */
	public boolean isIn(String name)
	{
		for (Vertex v : V)
		{
			if(v.getName().equals(name))
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
	 * Moves all vertexces out of subgraphs that they are not a part of.
	 */
	public void removeSubgraphs()
	{
		for(Subgraph sg : Sg)
		{
			for(Vertex v : V)
			{
				sg.containedIn(v);
			}
		}
	}

	/**
	 * Returns an exact copy of the graph
	 * @return the graph copy
	 */
	public Graph copy()
	{
		ArrayList<Vertex> copyV = new ArrayList<>();
		ArrayList<Edge> copyE = new ArrayList<>();
		ArrayList<Subgraph> copySg = new ArrayList<>();
		for(Vertex v : V)
		{
			copyV.add(new Vertex(v.getName(),v.getPosition(),Vector.ZERO,v.getColour(),v.getLabel()));
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
			if(newV!=null && newU != null)
			{
				if(!isIn(new Edge(newV,newU)))
				{
					copyE.add(new Edge(newV,newU));
				}
			}
		}
		for(Subgraph sg : Sg)
		{
			Subgraph copySub = copySubgraph(sg,copyV);
			copySg.add(copySub);
		}
		return new Graph(copyV,copyE,copySg);
	}

	/**
	 * Returns a new copy of the subgraph with all of it children
	 * @param sg
	 */
	public Subgraph copySubgraph(Subgraph sg,ArrayList<Vertex> copyV)
	{
		if(!sg.isLeaf())
		{
			ArrayList<Vertex> tempV = new ArrayList<>();
			Subgraph copySub = new Subgraph(tempV);
			for(Subgraph subSg : sg.getSg())
			{
				Subgraph copy = copySubgraph(subSg,copyV);
				copySub.addSubgraph(copy);
			}
			for(Vertex v : sg.getV())
			{
				Vertex newVertex = null;
				for(Vertex vertex : copyV)
				{
					if(vertex.getName().equals(v.getName()))
					{
						newVertex = vertex;
					}
				}
				if(newVertex != null)
				{
					copySub.addVertex(newVertex);
				}
			}
			copySub.setColour(sg.getColour());
			return copySub;
		}
		else
		{
			ArrayList<Vertex> tempV = new ArrayList<>();
			Subgraph copySub = new Subgraph(tempV);
			for(Vertex v : sg.getV())
			{
				Vertex newVertex = null;
				for(Vertex vertex : copyV)
				{
					if(vertex.getName().equals(v.getName()))
					{
						newVertex = vertex;
					}
				}
				if(newVertex != null)
				{
					copySub.addVertex(newVertex);
				}
			}
			copySub.setColour(sg.getColour());
			return copySub;
		}
	}

	/**
	 * Removes a vertrex from the graph. Removes any edges that were based of the vertex as well
	 */
	public void removeVertex(Vertex vertex)
	{
//		Iterator<Edge> Edges = E.iterator();
//		while(Edges.hasNext())
//		{
//			if(Edges.next().contains(vertex))
//			{
//				//System.out.println("Removeing edge");
//				Edges.remove();
//			}
//		}
		Predicate<Edge> edgePredicate = e -> e.contains(vertex);
		E.removeIf(edgePredicate);
		V.remove(vertex);
	}

	/**
	 * Removes an edge from th graph
	 */
	public void removeEdge(Edge edge)
	{
//		V.remove(edge.getV1());
//		V.remove(edge.getV2());
		E.remove(edge);
	}


	/**
	 * Creates a random edge between random vertices in the graph
	 */
	public void addRandomEdge()
	{
		Random rand = new Random();
		int i = rand.nextInt(V.size());
		int j = rand.nextInt(V.size());
		while(j == i)
		{
			j = rand.nextInt(V.size());
		}
		if(!isIn(new Edge(V.get(i),V.get(j))))
		{
			addEdge(new Edge(V.get(i),V.get(j)));
		}
	}

	/**
	 * Genarates a random graph with a random amounts of nodes and vertices. 
	 */
	public static Graph randomGraph(int size)
	{
		ArrayList<Vertex> Ver = new ArrayList<>();
		ArrayList<Edge> Edg = new ArrayList<>();
		Graph graph = new Graph(Ver,Edg);
		ArrayList<Vertex> V = graph.getV();
		ArrayList<Edge> E = graph.getE();
		Random rand = new Random();
		int i = rand.nextInt(size);
		while(i!= size-1)
		{
			if (i<2*(size/4))
			{
				Vertex newRandomVertex = new Vertex("" + graph.getV().size()+ 1);
				int j = rand.nextInt(V.size());
				graph.addVertex(newRandomVertex);
				Edge newRandomEdge = new Edge(newRandomVertex,V.get(j));
				graph.addEdge(newRandomEdge);
			}
			else if(i< 3*(size/4))
			{
				Vertex newRandomVertex = new Vertex("" + graph.getV().size() + 1);
				int j = rand.nextInt(V.size());
				int k = rand.nextInt(V.size());
				graph.addVertex(newRandomVertex);

				Edge newRandomEdge1 = new Edge(newRandomVertex,V.get(j));
				Edge newRandomEdge2 = new Edge(newRandomVertex,V.get(k));
				if(!graph.isIn(newRandomEdge1))
				{
					graph.addEdge(newRandomEdge1);
				}
				if(!graph.isIn(newRandomEdge1))
				{
					graph.addEdge(newRandomEdge2);
				}
			}
			else if (3*(size/4)<= i )
			{
				int j = rand.nextInt(V.size());
				int k = rand.nextInt(V.size());
				while(k == j)
				{
					k = rand.nextInt(V.size());
				}
				Edge newRandomEdge = new Edge(V.get(j),V.get(k));
				if(!graph.isIn(newRandomEdge))
				{
					graph.addEdge(newRandomEdge);
				}
			}
			i = rand.nextInt(size);
		}
		System.out.println("After generating a random graph the number of vectors is " + graph.getV().size());
		System.out.println("After generating a random graph the number of edges is " + graph.getE().size());
		return graph;
	}

	/**
	 * Randomly mutates a graph with a 3/4 chance of adding a node and a 1/4 chance of adding a edge. Returns a new graph which is * a copy of the graph mutated
	 * @param g the graph provided
	 */
	public static Graph mutate(Graph g)
	{
		Graph graph = g.copy();
		Random rand = new Random();
		int i = rand.nextInt(100);
		if(i <75)
		{
			Vertex newRandomVertex = new Vertex("" + (graph.getV().size() + 1));
			int j = rand.nextInt(((graph.getV().size())/2));
			j += (graph.getV().size())/2;
			graph.addVertex(newRandomVertex);
			graph.addEdge(new Edge(newRandomVertex,graph.getV().get(j)));
		}
		else
		{
			int j = rand.nextInt(graph.getV().size());
			int k = rand.nextInt(graph.getV().size());
			while(k == j)
			{
				k = rand.nextInt(((graph.getV().size())/2)) ;
				k += (graph.getV().size())/2;
			}
			Edge newRandomEdge = new Edge(graph.getV().get(j),graph.getV().get(k));
			if(!graph.isIn(newRandomEdge))
			{
				graph.addEdge(newRandomEdge);
			}
		}
		return graph;
	}

	/**
	 * Converts a dot file into a graph.
	 * @param strings the list of strings in the dot file.
	 * @return
	 */
	public static Graph convertDot(List<String> strings)
	{
		Iterator lines = strings.iterator();
		ArrayList<Vertex> V = new ArrayList<>();
		ArrayList<Edge> E = new ArrayList<>();
		Graph newGraph = new Graph(V,E);
		while(lines.hasNext())
		{
			String line = "" + lines.next();
			lines.remove();
			if(line.contains("subgraph "))
			{
				int nameStart = line.indexOf("subgraph");
				int nameEnd = line.indexOf("{");
				if(nameEnd == -1)
				{
					nameEnd = line.length();
				}
				String name = line.substring(nameStart,nameEnd).trim();
//				convertSubgraph(new Subgraph(new ArrayList<Vertex>(),new ArrayList<Subgraph>(),line.substring(nameStart,nameEnd)),strings,newGraph);
				ArrayList<Vertex> subV = new ArrayList<>();
				Subgraph subgraph = new Subgraph(subV);
				while(!line.contains("graph["))
				{
					line = "" + lines.next();
				}
				if(line.contains("color"))
				{

					int colourStart = line.indexOf("color") + 7;
					int colourEnd = line.indexOf("\"",colourStart);
					String subgraphColour = line.substring(colourStart,colourEnd);
					subgraph.setColour(new Colour(subgraphColour));
				}
				line = "" + lines.next();
				while(!line.contains("}"))
				{
					if(line.contains("label"))
					{
						int vertexnameEnd = line.indexOf("[");
						int labelStart = line.indexOf("label=\"");
						int labelEnd = line.indexOf("\"",labelStart+7);
						String label = line.substring(labelStart+7,labelEnd);
						if(!label.trim().isEmpty())
						{
							Vertex u1 = new Vertex(line.substring(0, vertexnameEnd).trim(), CharacterEscape.escapeHtml(label));
							newGraph.addVertex(u1);
							subgraph.addVertex(u1);
						}
					}
					else
					{
						int vertexnameEnd = line.indexOf("[");
						if(vertexnameEnd == -1)
						{
							vertexnameEnd = line.length();
						}

						Vertex u1 = new Vertex(line.substring(0,vertexnameEnd).trim());
						newGraph.addVertex(u1);
						subgraph.addVertex(u1);
					}
					line = "" + lines.next();
				}
				newGraph.addSubgraph(subgraph);
			}
			if(line.contains("label=") && !line.matches(".*(--|->).*"))
			{
				int nameEnd = line.indexOf("[");
				int labelStart = line.indexOf("label=\"");
				int labelEnd = line.indexOf("\"",labelStart+7);
				String label = line.substring(labelStart+7,labelEnd);
				if(!label.trim().isEmpty())
				{
					Vertex u1 = new Vertex(line.substring(0,nameEnd).trim(),CharacterEscape.escapeHtml(label));
					newGraph.addVertex(u1);
				}
			}
			if (line.matches(".*(--|->).*"))
			{
				String[] split = line.split("(--)|(->)");
				int stringEnd = split[1].indexOf("[");
				String u1Name = split[0].trim();
				String u2Name = split[1].substring(0,stringEnd).trim();
				if (newGraph.isIn(u1Name))
				{
					if(newGraph.isIn(u2Name))
					{
						Edge newEdge = new Edge(newGraph.getVertex(u1Name),newGraph.getVertex(u2Name));
						newGraph.addEdge(newEdge);
					}
					else
					{
						Vertex u2 = new Vertex(u2Name);
						newGraph.addVertex(u2);
						newGraph.addEdge(new Edge(newGraph.getVertex(u1Name),newGraph.getVertex(u2Name)));
					}
				}
				else
				{

					if(newGraph.isIn(u2Name))
					{
						newGraph.addVertex(new Vertex(u1Name));
						newGraph.addEdge(new Edge(newGraph.getVertex(u1Name),newGraph.getVertex(u2Name)));
					}
					else
					{
						Vertex u2 = new Vertex(u2Name);
						newGraph.addVertex(new Vertex(u1Name));
						newGraph.addVertex(new Vertex(u2Name));
						newGraph.addEdge(new Edge(newGraph.getVertex(u1Name),newGraph.getVertex(u2Name)));
					}
				}
				if(split[1].contains("color"))
				{
					int colourStart = split[1].indexOf("color") + 7;
					int colourEnd = split[1].indexOf("\"",colourStart);
					if(colourEnd == -1)
					{
						newGraph.getE().get(newGraph.getE().size()-1).setColour(split[1].substring(colourStart-1,split[1].indexOf(",",colourStart-1)));
					}
					else
					{
						newGraph.getE().get(newGraph.getE().size()-1).setColour(split[1].substring(colourStart,colourEnd));
					}
				}
				if(split[1].contains("label"))
				{
					int labelStart = split[1].indexOf("label") +7;
					int labelEnd = split[1].indexOf("\"",labelStart);
					newGraph.getE().get(newGraph.getE().size()-1).addLabel(split[1].substring(labelStart,labelEnd));
				}
			}
		}
		return newGraph;
	}
}