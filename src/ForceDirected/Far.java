package ForceDirected;

import java.io.PrintWriter;
import java.util.ArrayList;

import Graph.*;

public class Far 
{
	private Graph graph;
	int width;
	int height;
	double k;
	public Far (Graph graph, int width,int height)
	{
		this.graph = graph;
		this.width = width;
		this.height = height;
		k = Math.sqrt(width*height/(graph.getV().size()));
	}
	
	public void simulate(int i)
	{
		for(Vertex v : graph.getV())
		{
			v.randomise(Math.min(width, height));
		}
		for(int j = 0 ; j<i; j++)
		{
			for(Vertex v: graph.getV())
			{
				netRforce(v);
			}
			for(Edge e : graph.getE())
			{
				netAforce(e);
			}
			for(Vertex v : graph.getV())
			{
				move(v);
			}
		}
		try
		{
			PrintWriter writer = new PrintWriter("../FAR.svg","UTF-8");
			writer.print(SVG.of(graph));
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void netRforce(Vertex v) 
	{
		v.setDisplacement(Vector.ZERO);
		for(Vertex u : graph.getV())
		{
			if(!u.equals(v))
			{
				Vector s = u.getPosition().minus(v.getPosition());
				//System.out.println("The distance between " + v + " and " + u + "=" + s);
				double r = s.length();
				//System.out.println("The repulisve force is equal to " + Math.pow(k,2)/r);
				v.setDisplacement(v.getDisplacement().plus(s.negate().normalise().scale(Math.pow(k,2)/r)));
				
			}
		}
	}

	private void netAforce(Edge e)
	{
		Vertex v = e.getV1();
		Vertex u = e.getV2();
		Vector s = v.getPosition().minus(u.getPosition());
		double r = s.length();
		v.setDisplacement(v.getDisplacement().plus(s.normalise().scale(Math.pow(r,2)/k)));
		u.setDisplacement(u.getDisplacement().minus(s.normalise().scale(Math.pow(r,2)/k)));
	}
	
	private void move(Vertex v)
	{
		v.setPosition(v.getPosition().plus(v.getDisplacement()));
		//System.out.println(v);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		System.out.println("im running");
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
		//Graph graph = (Graph.K(6));
		Far far = new Far(graph,256,256);
		far.simulate(50);

	}

}
