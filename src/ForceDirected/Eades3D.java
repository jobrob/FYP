package ForceDirected;

import java.io.PrintWriter;
import java.util.ArrayList;

import Graph.Colour;
import Graph.Edge;
import Graph.Graph;
import Graph.Graph3D;
import Graph.SVG;
import Graph.Vector;
import Graph.Vertex;

public class Eades3D 
{
	
	public double temperature = 100;


	/**
	 * Sets up the initial position of vertex then calls apply forces on them the requierd amounts of time. Also outputs the SVG
	 * @param i The number of times apply forces will be called
	 */
	public static void simulate(int i,Graph3D g3d)
	{
		PrintWriter writer;
		for(Vertex v : g3d.getG().get(0).getV())
		{
			v.setPosition(Vector.ZERO);
			v.randomise(256);
		}
		for(int k = 1; k<g3d.getG().size(); k++)
		{
			for(int j=0; j < g3d.getG().get(k).getV().size(); j++)
			{
				if(g3d.getG().get(k-1).isIn(g3d.getG().get(k).getV().get(j)))
				{
					double x = g3d.getG().get(k-1).getV().get(j).getPosition().getX();
					double y = g3d.getG().get(k-1).getV().get(j).getPosition().getY();
					g3d.getG().get(k).getV().get(j).setPosition(new Vector(x,y,k));
				}
				else
				{
					g3d.getG().get(k).getV().get(j).setPosition(new Vector(0,0,k));
					g3d.getG().get(k).getV().get(j).randomise(256);
				}
			}
		}
		
		for(int j = 0; j < i; j++)
		{
			applyForces(g3d);
		}
		
		for (int k=0; k < g3d.getG().size(); k++)
		{
			double[] minMax = g3d.minMax();
			try 
			{
				writer = new PrintWriter("../" + k + ".svg", "UTF-8");
				//double[] minMax = g3d.getG().get(k).minMax();
				writer.print(SVG.of((g3d.getG().get(k)),minMax[0],minMax[1],minMax[2],minMax[3]));
				writer.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Applies the forces to each graph in the sequence in turn.
	 * Then applies the forces based on the edges between the graph.
	 */
	public static void applyForces(Graph3D g3d)
	{
		for(Graph g : g3d.getG())
		{
			Eades.applyForces(g);

		}
		for(Edge e : g3d.getE())
		{
			Vertex by = e.getV1();
			Vertex on = e.getV2();
			by.setDisplacement(Vector.ZERO);
			on.setDisplacement(Vector.ZERO);
			Vector s = by.getPosition().minus(on.getPosition());
			Vector t = on.getPosition().minus(by.getPosition());
			double r = s.length();
			if(r != 0 )
			{
				by.setDisplacement(by.getDisplacement().plus(t.normalise().scale(1*Math.log(r/(1)))));
				on.setDisplacement(on.getDisplacement().plus(s.normalise().scale(1*Math.log(r/(1)))));
			}
		}
		for(Edge e : g3d.getE())
		{
			Vertex by = e.getV1();
			Vertex on = e.getV2();
			by.setPosition(by.getPosition().plus(by.getDisplacement()));
			on.setPosition(on.getPosition().plus(on.getDisplacement()));
		}
	}
	
	public static void main(String[] args)
	{	
		for(int x = 0; x < 1 ;x++)
		{
			long starttime = System.nanoTime();
			ArrayList<Graph> G = new ArrayList<Graph>();
			ArrayList<Vertex> V = new ArrayList<Vertex>();
			ArrayList<Edge> E = new ArrayList<Edge>();
//		Vertex v1 = new Vertex("1",Vector.ZERO,Vector.ZERO,Colour.RED);
//		Vertex v2 = new Vertex("2",Vector.ZERO,Vector.ZERO,Colour.GREEN);
//		Vertex v3 = new Vertex("3",Vector.ZERO,Vector.ZERO,Colour.BLUE);
//		Edge e1 = new Edge(v1,v2);
//		Edge e2 = new Edge(v2,v3);
//		Graph g = new Graph(V,E);
//		g.addVertex(v1);
//		g.addVertex(v2);
//		g.addVertex(v3);
//		g.addEdge(e1);
//		g.addEdge(e2);
//		G.add(g);
//		
//		Graph g2 = g.copy();
//		G.add(g2);
//		
//		G.get(1).getV().add(new Vertex("4",Vector.ZERO,Vector.ZERO,Colour.YELLOW));
//		G.get(1).getE().add(
//			new Edge(
//				G.get(1).getVertex("1"),
//				G.get(1).getVertex("4")
//			)
//		);
//		G.get(1).getE().add(
//				new Edge(
//						G.get(1).getVertex("2"),
//						G.get(1).getVertex("4")
//						)
//				);
//		
//		Graph g3 = g2.copy();
//		g3.addVertex(new Vertex("5",Vector.ZERO,Vector.ZERO,Colour.PURPLE));
//		g3.addEdge(new Edge(
//							g3.getVertex("5"),
//							g3.getVertex("1")));
//		g3.addEdge(new Edge(
//							g3.getVertex("5"),
//							g3.getVertex("2")));
//		
//		G.add(g3);
//		
//		Graph g4 = g3.copy();
//		g4.addVertex(new Vertex("6",Vector.ZERO,Vector.ZERO,Colour.TEAL));
//		g4.addEdge(new Edge(
//							g4.getVertex("6"),
//							g4.getVertex("2")));
//		g4.addEdge(new Edge(
//							g4.getVertex("6"),
//							g4.getVertex("3")));
//		G.add(g4);
//		Graph g5 = g4.copy();
//		g5.addVertex(new Vertex("7",Vector.ZERO,Vector.ZERO,Colour.BLACK));
//		g5.addEdge(new Edge(
//							g5.getVertex("7"),
//							g5.getVertex("6")));
//		g5.addEdge(new Edge(
//							g5.getVertex("7"),
//							g5.getVertex("5")));
//		G.add(g5);
//		Graph graph = Graph.K(2);
//		G.add(graph);
//		for(int i = 2; i<15;i++)
//		{
//			Graph graph2 = G.get(G.size()-1).copy();
//			graph2.addVertex(new Vertex("" + i));
//			for (int j = 0;j<graph2.getV().size()-1;j++)
//			{
//				Edge e = new Edge(graph2.getVertex("" + i),graph2.getVertex("" + j));
//				graph2.addEdge(e);
//			}
//			G.add(graph2);
//		}
			Graph graph = Graph.K(3);
			G.add(graph);
			for(int i = 0; i < 20; i++)
			{
				Graph g = Graph.mutate(G.get(i));
				G.add(g);
			}	
			Graph3D g3d = new Graph3D(G);
			Eades3D.simulate(90000,g3d);
			long endtime = System.nanoTime();
			double duration = (double)(endtime - starttime)/1000000000;
			int noVertixces = g3d.totalVertices();
			int noEdges = g3d.totalEdges();
			int total = noVertixces + noEdges;
			int noGraphs = g3d.getG().size();
			System.out.println(noVertixces + "," + noEdges + "," + total + "," + noGraphs + "," + duration);
		}
	}
}