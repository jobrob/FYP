package ForceDirected;

import java.io.*;
import java.util.ArrayList;
import java.nio.file.*;

import Graph.Colour;
import Graph.Edge;
import Graph.Graph;
import Graph.Graph3D;
import Graph.SVG;
import Graph.Vector;
import Graph.Vertex;
import Graph.Subgraph;

public class Eades3D 
{

	public double temperature = 100;
	private static int ORDER = 5;

	/**
	 * Sets up the initial position of vertex then calls apply forces on them the requierd amounts of time. Also outputs the SVG
	 * @param i The number of times apply forces will be called
	 */
	public static void simulate(int i,Graph3D g3d)
	{
		PrintWriter writer;
		for(Vertex v : g3d.getG().get(g3d.getG().size()-1).getV())
		{
			v.setPosition(Vector.ZERO);
			v.randomise(256);
		}
		Eades.simulate(100,g3d.getG().get(g3d.getG().size()-1));
		for(int k  = g3d.getG().size() - 2; k > -1; k--)
		{
			for(Vertex v : g3d.getG().get(k).getV())
			{
				if (g3d.getG().get(k+1).isIn(v))
				{
					double x = g3d.getG().get(k+1).getVertex(v.getName()).getX();
					double y = g3d.getG().get(k+1).getVertex(v.getName()).getY();
					v.setColour(g3d.getG().get(k+1).getVertex(v.getName()).getColour());
					v.setPosition(new Vector(x,y,k));
				}
				else
				{
					v.setPosition(new Vector(0,0,k));
					v.randomise(256);
				}
				Eades.simulate(100,g3d.getG().get(k+1));
			}
		}
		int j = 0;
		while(j<i)
		{
			applyForces(g3d);
			j++;
		}
		System.out.println(j);

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
				by.setDisplacement(by.getDisplacement().plus(t.normalise().scale((1.0/10.0)*Math.log(r/(1)))));
				on.setDisplacement(on.getDisplacement().plus(s.normalise().scale((1.0/10.0)*Math.log(r/(1)))));
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
//
			try 
			{
				for(int i = 0; i <100 ; i++)
				{
					Graph graph  = Graph.convertDot(Files.readAllLines(Paths.get("../dotGraphs/state-" + i + ".dot")));
					G.add(graph);
				}
				//Graph chapter2 = chapter1.copy();
				//chapter2 = Graph.of(Files.readAllLines(Paths.get("../newGraph2.txt")),chapter2);
				//Graph chapter3 = Graph.of(Files.readAllLines(Paths.get("../newGraph3.txt")),new Graph(V,E));
				//G.add(chapter2);
				//System.out.println(chapter2.neighbourhood(chapter2.getVertex("Liu Bei")));
				//G.add(chapter3);
				
			}
			catch (IOException e) 
			{
				e.printStackTrace();
				System.err.println("\nCouldn't get file at path `"+args[0]+"`.");
			}


			//Vertex v1 = new Vertex("1",Vector.ZERO,Vector.ZERO,Colour.RED);
			//Vertex v2 = new Vertex("2",Vector.ZERO,Vector.ZERO,Colour.GREEN);
			//Vertex v3 = new Vertex("3",Vector.ZERO,Vector.ZERO,Colour.BLUE);
			//Edge e1 = new Edge(v1,v2);
			//Edge testing = new Edge(v2,v1);
			//Edge e2 = new Edge(v2,v3);
			//Graph g = new Graph(V,E);
			//g.addVertex(v1);
			//g.addVertex(v2);
			//g.addVertex(v3);
			//g.addEdge(e1);
			//g.addEdge(e2);
			//G.add(g);
			//
			//Graph g2 = g.copy();
			//G.add(g2);
			//
			//G.get(1).getV().add(new Vertex("4",Vector.ZERO,Vector.ZERO,Colour.YELLOW));
			//G.get(1).getE().add(
			//	new Edge(
			//		G.get(1).getVertex("1"),
			//		G.get(1).getVertex("4")
			//	)
			//);
			//G.get(1).getE().add(
			//	new Edge(
			//		G.get(1).getVertex("2"),
			//		G.get(1).getVertex("4")
			//	)
			//);
			//
			//Graph g3 = g2.copy();
			//g3.addVertex(new Vertex("5",Vector.ZERO,Vector.ZERO,Colour.PURPLE));
			//g3.addEdge(new Edge(
			//	g3.getVertex("5"),
			//	g3.getVertex("1")));
			//g3.addEdge(new Edge(
			//	g3.getVertex("5"),
			//	g3.getVertex("2")));
			//
			//G.add(g3);
			//
			//Graph g4 = g3.copy();
			//g4.addVertex(new Vertex("6",Vector.ZERO,Vector.ZERO,Colour.TEAL));
			//g4.addEdge(new Edge(
			//	g4.getVertex("6"),
			//	g4.getVertex("2")));
			//g4.addEdge(new Edge(
			//	g4.getVertex("6"),
			//	g4.getVertex("3")));
			//G.add(g4);
			//Graph g5 = g4.copy();
			//g5.addVertex(new Vertex("7",Vector.ZERO,Vector.ZERO,Colour.BLACK));
			//g5.addEdge(new Edge(
			//	g5.getVertex("7"),
			//	g5.getVertex("6")));
			//g5.addEdge(new Edge(
			//	g5.getVertex("7"),
			//	g5.getVertex("5")));
			//G.add(g5);
			//Graph graph = Graph.K(2);
			//G.add(graph);
			//for(int i = 2; i< ORDER ;i++)
			//{
			//	Graph graph2 = G.get(G.size()-1).copy();
			//	graph2.addVertex(new Vertex("" + i));
			//	for (int j = 0;j<graph2.getV().size()-1;j++)
			//	{
			//		Edge e = new Edge(graph2.getVertex("" + i),graph2.getVertex("" + j));
			//		graph2.addEdge(e);
			//	}
			//	G.add(graph2);
			//}
			////
			//Graph graph = Graph.randomGraph(100);
			//G.add(graph);
			//for(int i = 0; i < 50; i++)
			//{
			//	Graph copy = Graph.mutate(G.get(G.size()-1).copy());
			//	G.add(copy);
			//}
			//for(int j = 0; j < 10; j++)
			//{
			//	for(int i = 0; i < 10; i++)
			//	{
			//		Graph copy = G.get(G.size()-1).copy();
			//		copy.addRandomEdge();
			//		G.add(copy);
			//	}
			//	for(int i = 0; i < 5; i++)
			//	{
			//		Graph copy = G.get(G.size()-1).copy();
			//		copy.removeRandomEdge();
			//		G.add(copy);
			//	}
			//}
			//G.add(graph);
			//Graph graph2 = graph.copy();
			//System.out.println(graph2.getE().size());
			////graph2.addRandomEdge();
			//G.add(graph2);
			//for(int i = 0; i <1 ; i++)
			//{
			//	Graph copy = G.get(G.size()-1).copy();
			//	copy.addRandomEdge();
			//	G.add(copy);
			//}
			//Graph graph = Graph.K(5);
			//Graph graph2 = graph.copy();
			//ArrayList<Vertex> tempV = new ArrayList<Vertex>();
			//tempV.add(graph2.getV().get(1));
			//tempV.add(graph2.getV().get(2));
			//Subgraph subgraph = new Subgraph(tempV);
			//ArrayList<Vertex> tempV2 = new ArrayList<Vertex>();
			//tempV2.add(graph2.getV().get(0));
			//tempV2.add(graph2.getV().get(1));
			//tempV2.add(graph2.getV().get(2));
			//ArrayList<Subgraph> Subgraphs = new ArrayList<Subgraph>();
			//Subgraphs.add(subgraph);
			//Subgraph subgraph2 = new Subgraph(tempV2,Subgraphs);
			//graph2.addSubgraph(subgraph2);
			//
			//Graph graph3 = graph2.copy();
			//graph3.addVertex(new Vertex("6"));
			//graph3.addEdge(new Edge(graph3.getVertex("6"),graph3.getVertex("0")));
			//graph3.getSg().get(0).addVertex(graph3.getVertex("6"));
			//
			//G.add(graph);
			//G.add(graph2);
			//G.add(graph3);


			//Graph chapter1 = new Graph(V,E);
			//chapter1.addVertex(new Vertex("Zhang Jue",Colour.YELLOW));
			//chapter1.addVertex(new Vertex("Zhang Lian",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Zhang Jue"),chapter1.getVertex("Zhang Lian")));
			//chapter1.addVertex(new Vertex("Zhang Bao",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Zhang Jue"),chapter1.getVertex("Zhang Bao")));
			//chapter1.addVertex(new Vertex("Cheng Yuanzhi",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Zhang Jue"),chapter1.getVertex("Cheng Yuanzhi")));
			//chapter1.addVertex(new Vertex("Deng Mao",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Cheng Yuanzhi"),chapter1.getVertex("Deng Mao")));
			//
			//chapter1.addVertex(new Vertex("Emperor Ling",Colour.YELLOW));
			//chapter1.addVertex(new Vertex("Dong Zhuo",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Emperor Ling"),chapter1.getVertex("Dong Zhuo")));
			//chapter1.addVertex(new Vertex("He Jin",Colour.YELLOW));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Emperor Ling"),chapter1.getVertex("He Jin")));
			//chapter1.addVertex(new Vertex("Cao Cao",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("He Jin"),chapter1.getVertex("Cao Cao")));
			//chapter1.addVertex(new Vertex("Cai Yong", Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Cai Yong"),chapter1.getVertex("Emperor Ling")));
			//chapter1.addVertex(new Vertex("Zhu Jun",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Emperor Ling"),chapter1.getVertex("Zhu Jun")));
			//chapter1.addVertex(new Vertex("Huangfu Song",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Emperor Ling"),chapter1.getVertex("Huangfu Song")));
			//chapter1.addVertex(new Vertex("Zhang Rang",Colour.YELLOW));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Emperor Ling"),chapter1.getVertex("Zhang Rang")));
			//chapter1.addVertex(new Vertex("Cao Jie",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Zhang Rang"),chapter1.getVertex("Cao Jie")));
			//chapter1.addVertex(new Vertex("Feng Xu",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Zhang Rang"),chapter1.getVertex("Feng Xu")));
			//chapter1.addVertex(new Vertex("Lu Zhi",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Lu Zhi"),chapter1.getVertex("Emperor Ling")));
			//chapter1.addVertex(new Vertex("Liu Yan",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Liu Yan"),chapter1.getVertex("Emperor Ling")));
			//chapter1.addVertex(new Vertex("Zhou Jing",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Liu Yan"),chapter1.getVertex("Zhou Jing")));
			//chapter1.addVertex(new Vertex("Liu Bei",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Liu Bei"),chapter1.getVertex("Liu Yan")));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Liu Bei"),chapter1.getVertex("Lu Zhi")));
			//chapter1.addVertex(new Vertex("Guan Yu",Colour.BLUE));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Liu Bei"),chapter1.getVertex("Guan Yu")));
			//chapter1.addVertex(new Vertex("Zhang Fei"));
			//chapter1.addEdge(new Edge(chapter1.getVertex("Liu Bei"),chapter1.getVertex("Zhang Fei")));
			//G.add(chapter1);
			//
			//Graph chapter2 = chapter1.copy();
			//chapter2.addEdge(new Edge(chapter2.getVertex("Huangfu Song"),chapter2.getVertex("Cao Cao")));
			//chapter2.addVector(new Vertex(chapter2.getVertex("")))

			//
			//Graph graph = Graph.randomGraph(50);
			//G.add(graph);
			//for(int i = 0; i < 1; i++)
			//{
			//	//for(int j = 0; j < 1;j++)
			//	//{
			//		Graph g = Graph.mutate(G.get(i));
			//		G.add(g);
			//	//}
			//}	


			//			Vertex v1 = new Vertex("1");
			//			Vertex v2 = new Vertex("2");
			//			Edge e1 = new Edge(v1,v2);
			//			Graph g2 = new Graph(V,E);
			//			g2.addVertex(v1);
			//			g2.addVertex(v2);
			//			g2.addEdge(e1);
			//			G.add(g2);
			//
			//			Vertex v3 = new Vertex("3");
			//			Graph g3 = g2.copy();
			//			g3.addVertex(v3);
			//			g3.addEdge(new Edge(g3.getVertex("3"),g3.getVertex("1")));
			//			G.add(g3);
			//
			//			Vertex v4 = new Vertex("4");
			//			Graph g4 = g3.copy();
			//			g4.addVertex(v4);
			//			g4.addEdge(new Edge(g4.getVertex("4"),g4.getVertex("1")));
			//			G.add(g4);
			//
			//			Vertex v5 = new Vertex("5");
			//			Graph g5 = g4.copy();
			//			g5.addVertex(v5);
			//			g5.addEdge(new Edge(g5.getVertex("2"),g5.getVertex("5")));
			//			G.add(g5);
			//
			//			Vertex v6 = new Vertex("6");
			//			Graph g6 = g5.copy();
			//			g6.addVertex(v6);
			//			g6.addEdge(new Edge(g6.getVertex("2"),g6.getVertex("6")));
			//			G.add(g6);
			//
			//			Vertex v7 = new Vertex("7");
			//			Graph g7 = g6.copy();
			//			g7.addVertex(v7);
			//			g7.addEdge(new Edge(g7.getVertex("2"),g7.getVertex("7")));
			//			G.add(g7);
			//
			//			Vertex v8 = new Vertex("8");
			//			Graph g8 = g7.copy();
			//			g8.addVertex(v8);
			//			g8.addEdge(new Edge(g8.getVertex("2"),g8.getVertex("8")));
			//			G.add(g8);
			//
			//			Vertex v9 = new Vertex("9");
			//			Graph g9 = g8.copy();
			//			g9.addVertex(v9);
			//			g9.addEdge(new Edge(g9.getVertex("3"),g9.getVertex("9")));
			//			G.add(g9);
			//
			//			ArrayList<Vertex> SgV = new ArrayList<Vertex>();
			//			SgV.add(v9);
			//			SgV.add(v8);
			//			Subgraph sub = new Subgraph(SgV);
			//			g9.addSubgraph(sub);
			//
			//
			//			Vertex v10 = new Vertex("10");
			//			Graph g10 = g9.copy();
			//			g10.addVertex(v10);
			//			g10.addEdge(new Edge(g10.getVertex("3"),g10.getVertex("10")));
			//			G.add(g10);
			//
			//			Vertex v11 = new Vertex("11");
			//			Graph g11 = g10.copy();
			//			g11.addVertex(v11);
			//			g11.addEdge(new Edge(g11.getVertex("3"),g11.getVertex("11")));
			//			G.add(g11);
			//
			//			Vertex v12 = new Vertex("12");
			//			Edge e11 = new Edge(v4,v12);
			//			Graph g12 = g11.copy();
			//			g12.addVertex(v12);
			//			g12.addEdge(new Edge(g12.getVertex("4"),g12.getVertex("12")));
			//			G.add(g12);
			//
			//			Vertex v13 = new Vertex("13");
			//			Graph g13 = g12.copy();
			//			g13.addVertex(v13);
			//			g13.addEdge(new Edge(g13.getVertex("4"),g13.getVertex("13")));
			//			G.add(g13);
			//
			//			Vertex v14 = new Vertex("14");
			//			Edge e13 = new Edge(v4,v14);
			//			Graph g14 = g13.copy();
			//			g14.addVertex(v14);
			//			g14.addEdge(new Edge(g14.getVertex("4"),g14.getVertex("14")));
			//			G.add(g14);
			//
			//			Vertex v15 = new Vertex("15");
			//			Graph g15 = g14.copy();
			//			g15.addVertex(v15);
			//			g15.addEdge(new Edge(g15.getVertex("4"),g15.getVertex("15")));
			//			G.add(g15);
			//
			//			Vertex v16 = new Vertex("16");
			//			Graph g16 = g15.copy();
			//			g16.addVertex(v16);
			//			g16.addEdge(new Edge(g16.getVertex("4"),g16.getVertex("16")));
			//			G.add(g16);



			Graph3D g3d = new Graph3D(G);
			Eades3D.simulate(10000,g3d);
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