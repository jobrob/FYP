package ForceDirected;

import java.io.PrintWriter;
import java.lang.Math;
import Graph.*;

import java.util.ArrayList;
public class FDD
{
	private static String filename = null;
	
	private double c1 = 2;
	private double c2 = 1;
	private double c3 = 600;
	private double c4 = 0.1;
	
	//Attraction caused by an edge = c1*log(d/c2)
	//Repulsion caused by two vertices = c3/d^2
	
	
	private Graph g;
	
	/**
	 * Creates a FDD of a given graph
	 * @param g The graph
	 */
	public FDD(Graph g) 
	{
		this.g = g;
	}
	
	/**
	 * Calculates the forces i times
	 * @param i the number of times the forces will be calculated.
	 */
	public void simulate(int i)
	{
		PrintWriter writer;

		for(Vertex v : g.getV())
		{
			v.randomise(256);
		}
		for(int j = 0; j < i; j++)
		{
			applyForces();
		}
		
		try 
		{
			writer = new PrintWriter("../" + filename + ".svg", "UTF-8");
			writer.print(SVG.of(g));
			writer.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Applies both the electrostatic and the spring force
	 */
	public void applyForces() 
	{
		Vector[] force = new Vector[g.getV().size()];
		for(int i = 0; i < g.getV().size(); i++)
		{
			force[i] = netForce(g.getV().get(i));
		}
		
		for(int i = 0; i < g.getV().size(); i++)
		{
			g.getV().get(i).setVector
			(
				g.getV().get(i).getVector().plus
				(
					force[i]
				)
			);
		}
	}
	
	/**
	 * calculates the net force on a vertex
	 * @param v the vertex which the net force will be calculated on
	 */
	public Vector netForce(Vertex v)
	{
		return (netEForce(v).plus(netSForce(v))).scale(c4);
	}
	
	/**
	 * calculates the total electrostatic force on a given vertex from all other vertices in the graph
	 * @param v The vertex to compute the total electrostatic force on. 
	 */
	public Vector netEForce(Vertex v) 
	{	
		Vector result = Vector.ZERO;
		for (Vertex v2 : g.getV())
		{
			if(!v2.equals(v)) 
			{
				result = result.plus(eForce(v, v2));
			}
		}
		return result;
	}
	
	/**
	 * Calculates the net spring force on a vertex v
	 * Caused by all vertices in the neighbourhood of v
	 * @param v the vertex being considered.
	 */
	public Vector netSForce(Vertex v)
	{
		Vector result = Vector.ZERO;
		for(Vertex v2 : g.neighbourhood(v)) 
		{
			result = result.plus(sForce(v, v2));
		}
		return result;
	}
	
	/**
	 * Get the "electrostatic" force induced on the
	 * Vertex `on` as caused by the Vertex `by`. This
	 * force follows an inverse-square law.
	 * @param on the vertex which feels the force
	 * @param by the vertex which exerts the force
	 */
	public Vector eForce(Vertex on, Vertex by)
	{
		Vector s = by.getVector().minus(on.getVector());
		double r = s.length();
		return s.negate().normalise().scale(c3 / (r*r));
	}
	
	/**
	 * Get the spring force induced on
	 * the Vertex 'on' caused the Vertex 'by' 
	 * which shares an edge with 'on'
	 * @param on the vertex which feels the force
	 * @param by the vertex which exerts the force
	 */
	public Vector sForce (Vertex on, Vertex by)
	{
		Vector s = by.getVector().minus(on.getVector());
		double r = s.length();
		return s.normalise().scale(c1*Math.log(r/c2));
	}
	
	public static void main(String args[])
	{
		if (args.length > 0) 
		{
			filename = args[0];
		} 
		else 
		{
			filename = "FDD";
		}
		
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
		E.add(e1);
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
		Graph graph = new Graph(V,E);
		FDD fdd = new FDD(graph);
		fdd.simulate(1000);
	}
}
