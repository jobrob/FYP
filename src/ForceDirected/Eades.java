package ForceDirected;
import java.util.ArrayList;
import java.io.PrintWriter;
import Graph.*;

//import Graph.Colour;
//import Graph.Edge;
//import Graph.Graph;
//import Graph.Graph3D;
//import Graph.SVG;
//import Graph.Vector;
//import Graph.Vertex;
public class Eades
{
	private static final double c1 = 2;
	private static final double c2 = 1;
	private static final double c3 = 60000;
	private static final double c4 = 0.1;
	
	/**
	*Applys forces to a 2D graph a given amount of times
	*@param simulations the number of times apply forces will be called
	*@param g the graph wich will be used
	*/
	public static void simulate (int simulations,Graph g)
	{
		for (Vertex v : g.getV())
		{
			v.randomise(256);
		}
		for(int i = 0; i<simulations; i++)
		{		
			applyForces(g);
		}
	}
	
	/**
	*Calculates the forces present in a graph and then moves the vertexces in the graph proportinal to this.
	*@param g the graph which will be used
	*/
	public static void applyForces(Graph g)
	{
		eForces(g.getV());

		sForces(g.getE());
		for(Vertex v : g.getV())
		{
			v.setPosition(v.getPosition().plus(v.getDisplacement().scale(c4)));
			v.setDisplacement(Vector.ZERO);
		}
	}
	
	/**
	*Caculates the attractive forces due to edges present in a graph
	*@param the array list of edges in the graph
	*/
	public static void sForces(ArrayList<Edge> E)
	{
		for(Edge e : E)
		{
			
			Vertex u = e.getV1();
			Vertex v = e.getV2();
			Vector udistance = u.getPosition().minus(v.getPosition());
			Vector vdistance = v.getPosition().minus(u.getPosition());
			double magnitude = udistance.length();
			u.setDisplacement(u.getDisplacement().plus(vdistance.normalise().scale(c1*Math.log(magnitude/c2))));
			v.setDisplacement(v.getDisplacement().plus(udistance.normalise().scale(c1*Math.log(magnitude/c2))));
		}
	}
	
	/**
	*Calculates the replusive forces due to verteces present in a graph
	*@param V the list of verexces
	*/
	public static void eForces(ArrayList<Vertex> V)
	{
		for(int i = 0; i < V.size(); i++)
		{
			for(int j = i+1 ; j< V.size(); j++)
			{
				Vertex u = V.get(i);
				Vertex v = V.get(j);
				Vector udistance = u.getPosition().minus(v.getPosition());
				Vector vdistance = v.getPosition().minus(u.getPosition());
				double magnitude = udistance.length();
				u.setDisplacement(u.getDisplacement().minus(vdistance.normalise().scale(c3/(magnitude*magnitude))));
				v.setDisplacement(v.getDisplacement().minus(udistance.normalise().scale(c3/(magnitude*magnitude))));
			}
		}
	}
	
	public static void main(String args[])
	{
		ArrayList<Vertex> V = new ArrayList<Vertex>();
		ArrayList<Edge> E = new ArrayList<Edge>();

		Vertex v1 = new Vertex("1");
		Vertex v2 = new Vertex("2");
		Vertex v3 = new Vertex("3");
		Vertex v4 = new Vertex("4");
		Vertex v5 = new Vertex("5");
		Vertex v6 = new Vertex("6");
		Vertex v7 = new Vertex("7");
		Vertex v8 = new Vertex("8");
		Vertex v9 = new Vertex("9");
		Vertex v10 = new Vertex("10");
		Vertex v11 = new Vertex("11");
		Vertex v12 = new Vertex("12");
		Vertex v13 = new Vertex("13");
		Vertex v14 = new Vertex("14");
		Vertex v15 = new Vertex("15");
		Vertex v16 = new Vertex("16");
		V.add(v1);
		V.add(v2);
		V.add(v3);
		V.add(v4);
		V.add(v5);
		V.add(v6);
		V.add(v7);
		V.add(v8);
		V.add(v9);
		V.add(v10);
		V.add(v11);
		V.add(v12);
		V.add(v13);
		V.add(v14);
		V.add(v15);
		V.add(v16);
		Edge e1 = new Edge(v1,v2);
		Edge e2 = new Edge(v1,v3);
		Edge e3 = new Edge(v1,v4);
		Edge e4 = new Edge(v2,v5);
		Edge e5 = new Edge(v2,v6);
		Edge e6 = new Edge(v2,v7);
		Edge e7 = new Edge(v2,v8);
		Edge e8 = new Edge(v3,v9);
		Edge e9 = new Edge(v3,v10);
		Edge e10 = new Edge(v3,v11);
		Edge e11 = new Edge(v4,v12);
		Edge e12 = new Edge(v4,v13);
		Edge e13 = new Edge(v4,v14);
		Edge e14 = new Edge(v4,v15);
		Edge e15 = new Edge(v4,v16);
		//E.add(e1);
		E.add(e2);
		E.add(e3);
		E.add(e4);
		E.add(e5);
		E.add(e6);
		E.add(e7);
		E.add(e8);
		E.add(e9);
		E.add(e10);
		E.add(e11);
		E.add(e12);
		E.add(e13);
		E.add(e14);
		E.add(e15);
		//E.add(new Edge(new Vertex("1"), new Vertex("2")));
		
		Graph graph = new Graph(V,E);
		graph.addEdge(new Edge((graph.getVertex("1")),(graph.getVertex("2"))));
		for(Vertex v : V)
		{
			v.randomise(256);
		}
		Eades.simulate(1000,graph);
		try 
		{
			System.out.println("making an svg");
			PrintWriter writer = new PrintWriter("../testing.svg", "UTF-8");
			writer.print(SVG.of(graph));
			writer.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}