package ForceDirected;

import java.io.PrintWriter;
import java.util.ArrayList;

import Graph.*;

public class Eades3D 
{
	private Graph3D g3d;

	public Eades3D(Graph3D g3d)
	{
		this.g3d = g3d;
	}
	
	public void applyForces()
	{
		for(Graph g : g3d.getG())
		{
			Eades eades = new Eades(g);
			eades.applyForces();
		}
		for(Edge3D e3d : g3d.getE())
		{
			Vertex by = e3d.getG1().getVertex(e3d.getV().getName());
			Vertex on = e3d.getG2().getVertex(e3d.getV().getName());
			by.setDisplacement(Vector.ZERO);
			on.setDisplacement(Vector.ZERO);
			Vector s = by.getPosition().minus(on.getPosition());
			Vector t = on.getPosition().minus(by.getPosition());
			double r = s.length();
			by.setDisplacement(by.getDisplacement().plus(t.normalise().scale(10*Math.log(r/10))));
			on.setDisplacement(on.getDisplacement().plus(s.normalise().scale(10*Math.log(r/10))));	
		}
		for(Edge3D e3d : g3d.getE())
		{
			Vertex by = e3d.getG1().getVertex(e3d.getV().getName());
			Vertex on = e3d.getG2().getVertex(e3d.getV().getName());
			by.setPosition(by.getPosition().plus(by.getDisplacement()));
			on.setPosition(on.getPosition().plus(on.getDisplacement()));
		}
	}
	
	public void simulate(int i)
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
				g3d.getG().get(k).getV().get(j).setPosition(new Vector(0,0,k));
				g3d.getG().get(k).getV().get(j).randomise(256);
			}
		}
		
		for(int j = 0; j < i; j++)
		{
			applyForces();
		}
		
		for (int k=0; k < g3d.getG().size(); k++)
		{
			try 
			{
				writer = new PrintWriter("../" + k + ".svg", "UTF-8");
				writer.print(SVG.of(g3d.getG().get(k)));
				writer.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args)
	{
		ArrayList<Graph> G = new ArrayList<Graph>();
		for(int i = 0; i<2; i++)
		{
			ArrayList<Vertex> V = new ArrayList<Vertex>();
			ArrayList<Edge> E = new ArrayList<Edge>();
			Vertex v1 = new Vertex("1");
			Vertex v2 = new Vertex("2");
			Vertex v3 = new Vertex("3");
			Edge e1 = new Edge(v1,v2);
			Edge e2 = new Edge(v1,v3);
			Graph g = new Graph(V,E);
			g.addVertex(v1);
			g.addVertex(v2);
			g.addVertex(v3);
			g.addEdge(e1);
			g.addEdge(e2);
			G.add(g);
		}
		
		
		
		G.get(1).getV().add(new Vertex("4"));
		G.get(1).getE().add(
			new Edge(
				G.get(1).getVertex("1"),
				G.get(1).getVertex("4")
			)
		);
		//G.get(1).getV().add(new Vertex("5"));
		G.get(1).getE().add(
				new Edge(
						G.get(1).getVertex("2"),
						G.get(1).getVertex("4")
						)
				);
		/*G.get(2).getV().add(new Vertex("4"));
		G.get(2).getV().add(new Vertex("5"));
		G.get(2).getE().add(
				new Edge(
						G.get(2).getVertex("4"),
						G.get(2).getVertex("5")
						)
				);*/
		
		Graph3D g3d = new Graph3D(G);
		Eades3D e3d = new Eades3D(g3d);
		e3d.simulate(10000);
	}
}
