package Graph;
import java.util.ArrayList;
public class Subgraph
{
	private ArrayList<Vertex> V;
	private ArrayList<Edge> E;
	private Colour colour;
	
	public Subgraph(ArrayList<Vertex> V)
	{
		this.V = new ArrayList<Vertex>();
		this.E = new ArrayList<Edge>();
		for(Vertex v : V)
		{
			addVertex(v);
		}
		this.colour = new Colour();
	}
	
	private void addVertex(Vertex newVertex)
	{
		for(Vertex v : V)
		{
			Edge newEdge = new Edge(newVertex,v);
			E.add(newEdge);
		}
		V.add(newVertex);
	}
	
	public void applyForces()
	{
		for(Edge e : E)
		{
			Vertex u = e.getV1();
			Vertex v = e.getV2();
			Vector udistance = u.getPosition().minus(v.getPosition());
			Vector vdistance = v.getPosition().minus(u.getPosition());
			double magnitude = udistance.length();
			u.setDisplacement(u.getDisplacement().plus(vdistance.normalise().scale(1*Math.log(magnitude/1))));
			v.setDisplacement(v.getDisplacement().plus(udistance.normalise().scale(1*Math.log(magnitude/1))));
		}
		for(Vertex v : V)
		{
			v.setPosition(v.getDisplacement().plus(v.getPosition()));
		}
		for(Vertex v : V)
		{
			v.setDisplacement(Vector.ZERO);
		}
	}
	
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
		System.out.println("Width = " + (xMax - xMin));
		System.out.println("Height = " + (yMax - yMin));
		System.out.println("The xMin, yMin, xMax, yMax values are " + xMin + " " +  yMin + " " + xMax + " " + yMax);
		
		return new double[]{xMin, yMin, xMax, yMax};
	}
}

/**
 Graphs contain a list of subgraphs
 These subgraphs have a list of vertices
 These Vertices have extra invisible "subgraph edges"
 Draw boxes
 */