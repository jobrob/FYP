package Graph;
import java.util.ArrayList;
public class Subgraph
{
	private ArrayList<Vertex> V;
	private ArrayList<Edge> E;
	private Colour colour;
	private ArrayList<Subgraph> Sg;
	private String name;

	public Subgraph(ArrayList<Vertex> V, ArrayList<Subgraph> Sg,String name)
	{
		this.V = new ArrayList<Vertex>();
		this.E = new ArrayList<Edge>();
		this.Sg = Sg;
		for(Vertex v : V)
		{
			addVertex(v);
		}
		this.colour = new Colour();
		this.name = name;
	}
	
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
	
	/**
	 * Adds a vertex to the subgraph and creates the inter subgraph edges between it and the other vertices
	 */
	public void addVertex(Vertex newVertex)
	{
		for(Vertex v : V)
		{
			Edge newEdge = new Edge(newVertex,v);
			E.add(newEdge);
		}
		V.add(newVertex);
	}
	
	/**
	 * Adds a subgraph to the list of subgraphs
	 */
	public void addSubgraph(Subgraph newSub)
	{
		Sg.add(newSub);
	}
	
	/**
	 * Returns the list of vertices
	 */
	public ArrayList<Vertex> getV()
	{
		return V;
	}
	
	/**
	 * Returns the list of subgraphs
	 */
	public ArrayList<Subgraph> getSg()
	{
		return Sg;
	}
	
	/**
	 * Returns the colour of of the subgraph
	 */
	public Colour getColour()
	{
		return colour;
	}

	public String getName()
	{
		return name;
	}
	
	public void setColour(Colour colour)
	{
		this.colour = colour;
	}
	
	/**
	 * Moves the nodes in the subgraphs based on the intersubgraph atraction.
	 */
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
	
	/**
	 * calculates the minimum and maximum x and y coordiantes of all the vertexies in hte subgraph
	 */
	public double[] minMax()
	{
//		for(Subgraph sub : Sg)
//		{
//			sub.minMax();
//		}

		double xMin = Double.POSITIVE_INFINITY;
		double xMax = Double.NEGATIVE_INFINITY;
		double yMin = Double.POSITIVE_INFINITY;
		double yMax = Double.NEGATIVE_INFINITY;

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
	
	/**
	 * Checks if a vertex is containd in the subgraphs area.
	 */
	public void containedIn(Vertex v)
	{
		double[] coorods = minMax();
		double xMin = coorods[0];
		double xMax = coorods[2];
		double yMin = coorods[1];
		double yMax = coorods[3];
		if((xMin<=v.getX()) && (v.getX()<=xMax) && (yMin<=v.getY()) && (v.getY()<=yMax))
		{
			if(!V.contains(v))
			{
				move(v,xMin,xMax,yMin,yMax);
			}
		}
	}
	
	/**
	 * Removes the vertex from the subgraph by moving it to the nearist side 
	 */
	public void move(Vertex v,double xMin,double xMax,double yMin,double yMax)
	{
		System.out.println("Remove kebab");
		double x = v.getX();
		double y = v.getY();
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
		if(i < 2)
		{
			v.setX(v.getX() + d[i] + sign + 10);
		}
		else
		{
			v.setY(v.getY() + d[i] + sign + 10);
		}
	}
	
	/**
	 *Returns true if the subgraph contains no 
	 */
	public boolean isLeaf()
	{
        return Sg.size() == 0;
    }
}
