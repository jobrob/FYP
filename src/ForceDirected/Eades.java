package ForceDirected;
import java.util.ArrayList;

import Graph.*;


public class Eades
{
	private static final double scaleFactor = 0.1;
	
	/**
	*Applys forces to a 2D graph a given amount of times
	*@param simulations the number of times apply forces will be called
	*@param g the graph wich will be used
	*/
	public static void simulate (int simulations,Graph g,double springForces1,double springForces2, double electrostaticForces)
	{
//		for (Vertex v : g.getV())
//		{
//			v.randomise(256);
//		}
		for(int i = 0; i<simulations; i++)
		{		
			applyForces(g,springForces1,springForces2,electrostaticForces);
		}
		g.removeSubgraphs();
	}
	
	/**
	*Calculates the forces present in a graph and then moves the vertexces in the graph proportinal to this.
	*@param g the graph which will be used
	*/
	public static void applyForces(Graph g,double springForces1,double springForces2,double electrostaticForces)
	{
		for(Subgraph sg : g.getSg())
		{
			sg.applyForces();
		}
		eForces(g.getV(),electrostaticForces);
		sForces(g.getE(),springForces1,springForces2);
		for(Vertex v : g.getV())
		{
			v.setPosition(v.getPosition().plus(v.getDisplacement().scale(scaleFactor)));
			v.setDisplacement(Vector.ZERO);
		}
		g.removeSubgraphs();

	}
	
	/**
	*Caculates the attractive forces due to edges present in a graph
	*@param E the array list of edges in the graph
	*/
	private static void sForces(ArrayList<Edge> E,double springForces1,double springForces2)
	{
		for(Edge e : E)
		{
			Vertex u = e.getV1();
			Vertex v = e.getV2();
			Vector udistance = u.getPosition().minus(v.getPosition());
			Vector vdistance = v.getPosition().minus(u.getPosition());
			double magnitude = udistance.length();
			u.setDisplacement(u.getDisplacement().minus(udistance.normalise().scale((e.getRepeats()+1)*springForces1*Math.log(magnitude/springForces2))));
			v.setDisplacement(v.getDisplacement().minus(vdistance.normalise().scale((e.getRepeats()+1)*springForces1*Math.log(magnitude/springForces2))));
		}
	}
	
	/**
	*Calculates the replusive forces due to verteces present in a graph
	*@param V the list of verexces
	*/
	private static void eForces(ArrayList<Vertex> V,double electrostaticForces)
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
				//u.setDisplacement(u.getDisplacement().plus(new Vector(1,1,0).scale(Math.log(optDistance/magnitude))));
				//v.setDisplacement(v.getDisplacement().plus(new Vector(1,1,0).scale(Math.log(optDistance/magnitude))));
				u.setDisplacement(u.getDisplacement().minus(vdistance.normalise().scale(electrostaticForces/(magnitude*magnitude))));
				v.setDisplacement(v.getDisplacement().minus(udistance.normalise().scale(electrostaticForces/(magnitude*magnitude))));
			}
		}
	}

}