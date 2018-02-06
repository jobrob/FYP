package Graph;
import java.util.ArrayList;
public class Subgraph
{
	private ArrayList<Vertex> V;
	private ArrayList<Edge> E;
	private Colour colour;
	private ArrayList<Subgraph> Sg;
	
	public Subgraph(ArrayList<Vertex> V, ArrayList<Subgraph> Sg)
	{
		this.V = new ArrayList<Vertex>();
		this.E = new ArrayList<Edge>();
		this.Sg = Sg;
		for(Vertex v : V)
		{
			addVertex(v);
		}
		this.colour = new Colour();
	}
	
	public Subgraph(ArrayList<Vertex> V)
	{
		this.V = new ArrayList<Vertex>();
		this.E = new ArrayList<Edge>();
		this.Sg = new ArrayList<Subgraph>();
		for(Vertex v : V)
		{
			addVertex(v);
		}
		this.colour = new Colour();
	}
	
	public void addVertex(Vertex newVertex)
	{
		for(Vertex v : V)
		{
			Edge newEdge = new Edge(newVertex,v);
			E.add(newEdge);
		}
		V.add(newVertex);
	}
	
	public void addSubgraph(Subgraph newSub)
	{
		Sg.add(newSub);
	}
	
	public ArrayList<Vertex> getV()
	{
		return V;
	}
	
	public ArrayList<Subgraph> getSg()
	{
		return Sg;
	}
	
	public Colour getColour()
	{
		return colour;
	}
	
	public void applyForces()
	{
		if(Sg.size() > 0)
		{
			for(Subgraph sub : Sg)
			{
				sub.applyForces();
			}
		}
		for(Edge e : E)
		{
			Vertex u = e.getV1();
			Vertex v = e.getV2();
			Vector udistance = u.getPosition().minus(v.getPosition());
			Vector vdistance = v.getPosition().minus(u.getPosition());
			double magnitude = udistance.length();
			u.setDisplacement(u.getDisplacement().plus(vdistance.normalise().scale(0.1*Math.log(magnitude/1))));
			v.setDisplacement(v.getDisplacement().plus(udistance.normalise().scale(0.1*Math.log(magnitude/1))));
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
		for(Subgraph sub : Sg)
		{
			sub.minMax();
		}
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
		
		return new double[]{xMin, yMin, xMax, yMax};
	}
	
	public void containedIn(Vertex v)
	{
		double[] coorods = minMax();
		double xMin = coorods[0];
		double xMax = coorods[2];
		double yMin = coorods[1];
		double yMax = coorods[3];
		System.out.println("Checking contained");
		if((xMin<=v.getX()) && (v.getX()<=xMax) && (yMin<=v.getY()) && (v.getY()<=yMax))
		{
			if(!V.contains(v))
			{
				System.out.println("I think "  + v + " is in the box ");
				move(v,xMin,xMax,yMin,yMax);
			}
		}
	}
	
	public void move(Vertex v,double xMin,double xMax,double yMin,double yMax)
	{
		System.out.println("Im moving " + v);
		System.out.println("The value of minMax " + xMin + "," + xMax + "," + yMin + "," + yMax);
		double x = v.getX();
		double y = v.getY();
		System.out.println("diff " + (x-xMin) + ", " + (x-xMax) + ", " + (y-yMin) + ", " + (y-yMax));	
		double[] d = new double[] {x-xMin,x-xMax,y-yMin,y-yMax};
		int i = 0;
		for(int j = 1;j<d.length;j++)
		{
			if(d[j] <d[i])
			{
				i = j;
			}
		}
		int sign = (i+1)%2;
		System.out.println("i is " + i);
		if(i < 2)
		{
			System.out.println("Set x to " + (d[i] + sign*1));
			v.setX(v.getX() + d[i] + sign*1);
		}
		else
		{
			System.out.println("Set y to " + (d[i] + sign*1));
			v.setY(v.getY() + d[i] + sign*1);
		}
		System.out.println("I have moved " + v);
	}
	
	public boolean isLeaf()
	{
		if(Sg.size() == 0)
		{
			return true;
		}
		return false;
	}
}

/**
 Graphs contain a list of subgraphs
 These subgraphs have a list of vertices
 These Vertices have extra invisible "subgraph edges"
 Draw boxes
 */