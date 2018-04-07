package ForceDirected;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Random;

import Graph.*;
import Util.*;

import javax.swing.*;

public class Eades3D
{	
	/**
	 * Sets up the initial position of vertex then calls apply forces on them the requierd amounts of time. Also outputs the SVG
	 * @param i The number of times apply forces will be called
	 */
	public static void simulate(int i,Graph3D g3d,double inertia, double springForces1, double springForces2, double electrostaticForces)
	{
//		System.out.println("Starting to simulate");
		long starttime = System.nanoTime();
		PrintWriter writer;
		for(Vertex v : g3d.getG().get(g3d.getG().size()-1).getV())
		{
			v.setPosition(Vector.ZERO);
			v.randomise(256);
		}
		//Eades.simulate(100,g3d.getG().get(g3d.getG().size()-1));
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
				//Eades.simulate(100,g3d.getG().get(k+1));
			}
		}
		int j = 0;
		while(j<i)
		{
			applyForces(g3d,inertia,springForces1,springForces2,electrostaticForces);
			j++;
		}
		for(Graph g : g3d.getG())
		{
			g.removeSubgraphs();
		}

		for (int k=0; k < g3d.getG().size(); k++)
		{
			double[] minMax = g3d.minMax();
			try
			{
				writer = new PrintWriter("../output/" + k + ".svg", "UTF-8");
				//double[] minMax = g3d.getG().get(k).minMax();
				writer.print(SVG.of((g3d.getG().get(k)),minMax[0],minMax[1],minMax[2],minMax[3]));
				writer.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
//		System.out.println("Simulation over");
		long endtime = System.nanoTime();
		double duration = (double)(endtime - starttime)/1000000000;
//		System.out.println("There was a total of " + (g3d.totalEdges() + g3d.totalVertices()) + " elements and the simulation took " + duration + " seconds");

	}

	/**
	 * Applies the forces to each graph in the sequence in turn.
	 * Then applies the forces based on the edges between the graph.
	 */
	public static void applyForces(Graph3D g3d,double inertia,double springForces1,double springForces2, double electrostaticForces)
	{
		for(Graph g : g3d.getG())
		{
			Eades.applyForces(g,springForces1,springForces2,electrostaticForces);
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
				by.setDisplacement(by.getDisplacement().plus(t.normalise().scale((inertia)*Math.log(r/(1)))));
				on.setDisplacement(on.getDisplacement().plus(s.normalise().scale((inertia)*Math.log(r/(1)))));
			}
		}
		for(Edge e : g3d.getE())
		{
			Vertex by = e.getV1();
			Vertex on = e.getV2();
			by.setPosition(by.getPosition().plus(by.getDisplacement()));
			by.setDisplacement(Vector.ZERO);
			on.setPosition(on.getPosition().plus(on.getDisplacement()));
			on.setDisplacement(Vector.ZERO);
		}
	}

	public static void main(String[] args)
	{
		ArrayList<Graph> G = new ArrayList<>();
		ArrayList<Vertex> V = new ArrayList<>();
		ArrayList<Edge> E = new ArrayList<>();
//
// 		//RANDOM//
// 		Graph graph = Graph.K(3);
//		G.add(graph);
//		for(int i = 0;i<15 ; i++)
//		{
//			Graph newGraph = Graph.mutate(G.get(G.size()-1));
//			G.add(newGraph);
//		}

		//K CYCLE//
//		Graph graph = Graph.K(3);
//		G.add(graph);
//		for(int i = 3;i<15 ; i++)
//		{
//			Graph newGraph = Graph.K(i);
//			G.add(newGraph);
//		}

		//EARLY EXAMPLE//
		/*Graph g1 = new Graph(V,E);
		Vertex v1 = new Vertex("r",Vector.ZERO,Vector.ZERO,new Colour(255,0,0),"r");
		Vertex v2 = new Vertex("b",Vector.ZERO,Vector.ZERO,new Colour(0,0,255),"b");
		Vertex v3 = new Vertex("g",Vector.ZERO,Vector.ZERO,new Colour(0,255,0),"g");
		g1.addVertex(v1);
		g1.addVertex(v2);
		g1.addVertex(v3);
		Edge e1 = new Edge(g1.getVertex("r"),g1.getVertex("b"));
		Edge e2 = new Edge(g1.getVertex("r"),g1.getVertex("g"));
		Edge e3 = new Edge(g1.getVertex("g"),g1.getVertex("b"));
		g1.addEdge(e1);
		g1.addEdge(e2);
		g1.addEdge(e3);
		G.add(g1);

		Graph g2 = g1.copy();
		g2.addVertex(new Vertex("y",Vector.ZERO,Vector.ZERO,new Colour(255,255,0),"y"));
		g2.addEdge(new Edge(g2.getVertex("y"),g2.getVertex("g")));
		G.add(g2);

		Graph g3 = g2.copy();
		g3.addEdge(new Edge(g3.getVertex("y"),g3.getVertex("b")));
		G.add(g3);

		Graph g4 = g3.copy();
		g4.addEdge(new Edge(g4.getVertex("y"),g4.getVertex("r")));
		G.add(g4);
		
		Graph g5 = g4.copy();
		g5.addVertex(new Vertex("p" ,Vector.ZERO,Vector.ZERO,new Colour(128,0,128), "p"));
		g5.addEdge(new Edge(g5.getVertex("y"),g5.getVertex("p")));
		G.add(g5);

		Graph g6 = g5.copy();
		g6.addVertex(new Vertex("bl",Vector.ZERO,Vector.ZERO,new Colour(0,0,0),"bl"));
		g6.addEdge(new Edge(g6.getVertex("bl"),g6.getVertex("y")));
		G.add(g6);
		*/


		//SINGLE SUBGRAPH//
		/*
		Vertex v1 = new Vertex("1");
		Vertex v2 = new Vertex("2");
		Edge e1 = new Edge(v1,v2);
		Graph g2 = new Graph(V,E);
		g2.addVertex(v1);
		g2.addVertex(v2);
		g2.addEdge(e1);
		G.add(g2);

		Vertex v3 = new Vertex("3");
		Graph g3 = g2.copy();
		g3.addVertex(v3);
		g3.addEdge(new Edge(g3.getVertex("3"),g3.getVertex("1")));
		G.add(g3);

		Vertex v4 = new Vertex("4");
		Graph g4 = g3.copy();
		g4.addVertex(v4);
		g4.addEdge(new Edge(g4.getVertex("4"),g4.getVertex("1")));
		G.add(g4);

		Vertex v5 = new Vertex("5");
		Graph g5 = g4.copy();
		g5.addVertex(v5);
		g5.addEdge(new Edge(g5.getVertex("2"),g5.getVertex("5")));
		G.add(g5);

		Vertex v6 = new Vertex("6");
		Graph g6 = g5.copy();
		g6.addVertex(v6);
		g6.addEdge(new Edge(g6.getVertex("2"),g6.getVertex("6")));
		G.add(g6);

		Vertex v7 = new Vertex("7");
		Graph g7 = g6.copy();
		g7.addVertex(v7);
		g7.addEdge(new Edge(g7.getVertex("2"),g7.getVertex("7")));
		G.add(g7);

		Vertex v8 = new Vertex("8");
		Graph g8 = g7.copy();
		g8.addVertex(v8);
		g8.addEdge(new Edge(g8.getVertex("2"),g8.getVertex("8")));
		G.add(g8);

		Vertex v9 = new Vertex("9");
		Graph g9 = g8.copy();
		g9.addVertex(v9);
		g9.addEdge(new Edge(g9.getVertex("3"),g9.getVertex("9")));
		G.add(g9);



		Vertex v10 = new Vertex("10");
		Graph g10 = g9.copy();
		g10.addVertex(v10);
		g10.addEdge(new Edge(g10.getVertex("3"),g10.getVertex("10")));
		G.add(g10);

		Vertex v11 = new Vertex("11");
		Graph g11 = g10.copy();
		g11.addVertex(v11);
		g11.addEdge(new Edge(g11.getVertex("3"),g11.getVertex("11")));
		G.add(g11);

		Vertex v12 = new Vertex("12");
		Edge e11 = new Edge(v4,v12);
		Graph g12 = g11.copy();
		g12.addVertex(v12);
		g12.addEdge(new Edge(g12.getVertex("4"),g12.getVertex("12")));
		G.add(g12);
		g12.addSubgraph(new Subgraph(new ArrayList<Vertex>()));
		g12.getSg().get(0).addVertex(g12.getVertex("9"));
		g12.getSg().get(0).addVertex(g12.getVertex("8"));
		g12.getSg().get(0).addVertex(g12.getVertex("12"));

		Vertex v13 = new Vertex("13");
		Graph g13 = g12.copy();
		g13.addVertex(v13);
		g13.addEdge(new Edge(g13.getVertex("4"),g13.getVertex("13")));
		G.add(g13);

		Vertex v14 = new Vertex("14");
		Edge e13 = new Edge(v4,v14);
		Graph g14 = g13.copy();
		g14.addVertex(v14);
		g14.addEdge(new Edge(g14.getVertex("4"),g14.getVertex("14")));
		G.add(g14);

		Vertex v15 = new Vertex("15");
		Graph g15 = g14.copy();
		g15.addVertex(v15);
		g15.addEdge(new Edge(g15.getVertex("4"),g15.getVertex("15")));
		G.add(g15);

		Vertex v16 = new Vertex("16");
		Graph g16 = g15.copy();
		g16.addVertex(v16);
		g16.addEdge(new Edge(g16.getVertex("4"),g16.getVertex("16")));
		G.add(g16);
		*/
		//DOUBLE SUBGRAPH//
//		for(int i = 3;i<6;i++)
//		{
//			Graph test = Graph.K(i);
//			G.add(test);
//		}
//		Graph graph = Graph.K(5);
//		Graph graph2 = graph.copy();
//		ArrayList<Vertex> tempV = new ArrayList<Vertex>();
//		tempV.add(graph2.getV().get(1));
//		tempV.add(graph2.getV().get(2));
//		Subgraph subgraph = new Subgraph(tempV);
//		ArrayList<Vertex> tempV2 = new ArrayList<Vertex>();
//		tempV2.add(graph2.getV().get(0));
//		tempV2.add(graph2.getV().get(1));
//		tempV2.add(graph2.getV().get(2));
//		ArrayList<Subgraph> Subgraphs = new ArrayList<Subgraph>();
//		Subgraphs.add(subgraph);
//		Subgraph subgraph2 = new Subgraph(tempV2,Subgraphs);
//		graph2.addSubgraph(subgraph2);
//		Graph graph3 = graph2.copy();
//		graph3.addVertex(new Vertex("6"));
//		graph3.addEdge(new Edge(graph3.getVertex("6"),graph3.getVertex("0")));
//		graph3.getSg().get(0).addVertex(graph3.getVertex("6"));
//
//		G.add(graph);
//		G.add(graph2);
//		G.add(graph3);


		try
		{
			for (int i = 0; i < 100; i++)
			{
				byte[] bytes = Files.readAllBytes(Paths.get("../dotGraphs/example/state-" + i + ".dot"));
				String s = new String(bytes, StandardCharsets.UTF_8);

				// This ensures semi-colons are immediately followed by a newline character,
				// since the parser assumes such.
				s = s.replaceAll("\\;",";\n").replaceAll("\\n\\n","\n");
				String[] lines = s.split("\n");

				List<String> linesList = new ArrayList<String>(Arrays.asList(lines));

				Graph graph = Graph.convertDot(linesList);
				G.add(graph);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("\nCouldn't get file at path");
		}


		Graph3D g3d = new Graph3D(G);
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new InputUI(g3d);
			}
		});
	}
}
