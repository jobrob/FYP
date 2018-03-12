package ForceDirected;

import java.io.*;
import java.util.ArrayList;
import java.nio.file.*;

import Graph.*;
import Util.InputGUI;
import Util.InputUI;

import javax.swing.*;

public class Eades3D 
{

	public double temperature = 100;
	private static int ORDER = 5;


	/**
	 * Sets up the initial position of vertex then calls apply forces on them the requierd amounts of time. Also outputs the SVG
	 * @param i The number of times apply forces will be called
	 */
	public static void simulate(int i,Graph3D g3d,double inertia, double springForces1, double springForces2, double electrostaticForces)
	{
		System.out.println("Starting to simulate");
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
		System.out.println("Simulation over");
		long endtime = System.nanoTime();
		double duration = (double)(endtime - starttime)/1000000000;
		System.out.println("The simulation took " + duration + " seconds");

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
			on.setPosition(on.getPosition().plus(on.getDisplacement()));
		}
	}

	public static void main(String[] args)
	{
		double graphEdges;
		double springForces1;
		double springForces2;
		double electrostaticForces;
		System.out.println(args.length + "long");
		if(args.length >= 4)
		{
			System.out.println("user input");
			graphEdges = Double.parseDouble(args[0]);
			springForces1 = Double.parseDouble(args[1]);
			springForces2 = Double.parseDouble(args[2]);
			electrostaticForces = Double.parseDouble(args[3]);
		}
		else
		{
			System.out.println("not user input");
			graphEdges = 0.2;
			springForces1 = 2.0;
			springForces2 = 1.0;
			electrostaticForces = 60000;
		}
		long starttime = System.nanoTime();
		ArrayList<Graph> G = new ArrayList<>();
		ArrayList<Vertex> V = new ArrayList<>();
		ArrayList<Edge> E = new ArrayList<>();
		try
		{
			for(int i = 0; i <100; i++)
			{
				Graph graph  = Graph.convertDot(Files.readAllLines(Paths.get("../dotGraphs/state-" + i + ".dot")));
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
//		Eades3D.simulate(10000,g3d,graphEdges,springForces1,springForces2,electrostaticForces);
		int noVertixces = g3d.totalVertices();
		int noEdges = g3d.totalEdges();
		int total = noVertixces + noEdges;
		int noGraphs = g3d.getG().size();
		System.out.println(" Total Vertices: " + noVertixces + ",\n Total Edges:" + noEdges + ",\n Total Elements:" + total + ",\n Total Graphs:" + noGraphs);
	}
}
