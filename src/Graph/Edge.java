package Graph;

import java.util.ArrayList;

public class Edge
{
	private final Vertex v1;
	private final Vertex v2;
	private String colour;
	private ArrayList<String> labels;
	private int repeats;


	public Edge(Vertex v1, Vertex v2, String colour, ArrayList<String> labels, int repeats)
    {
        if (v1 == null || v2 == null)
        {
            System.err.println("Some vertex in edge is `null`.");
        }
        this.v1 = v1;
        this.v2 = v2;
        if (v1.equals(v2))
        {
            System.out.println(" Edges must have 2 unique vertices" + v1 + " is equal to " + v2);
        }
        this.colour = colour;
        this.labels = labels;
        this.repeats = repeats;
    }

    public Edge(Vertex v1, Vertex v2, String colour, String label)
	{
		this(v1,v2,colour);
		addLabel(label);
	}

	public Edge(Vertex v1, Vertex v2, String colour)
	{
		this(v1,v2,colour,new ArrayList<String>(),0);
	}
	
	/**
	 * Creates an edge from vertex one to vertex 2
	 * @param v1 the first vertex
	 * @param v2 the second vertex
	 */
	public Edge (Vertex v1, Vertex v2)
	{
		this(v1,v2,"","");
	}
	
	/**
	 * Returns the first vertex
	 */
	public Vertex getV1()
	{
		return v1;
	}
	
	/**
	 * Returns the second vertex
	 */
	public Vertex getV2()
	{
		return v2;
	}
	
	public String getColour()
	{
		return colour;
	}
	
	public ArrayList<String> getLabels()
	{
		return labels;
	}

	public int getRepeats()
    {
        return repeats;
    }
	
	public void setColour(String colour)
	{
		this.colour = colour;
	}
	
	public void addLabel(String label)
	{
		if(labels.size() > repeats)
		{
			labels.set(repeats,label);
		}
		else
		{
			labels.add(label);
		}
	}

    public void setRepeats (int repeats)
    {
        this.repeats = repeats;
    }

    public double xMid()
	{
		return (v1.getX() + v2.getX())/2;
	}
	
	public double yMid()
	{
		return (v1.getY() + v2.getY())/2;
	}
	
	/**
	 * Checks to see if two edges are equal.
	 * The edges are unordered so (v1,v2)==(v2,v1)
	 * @param e the edge being compared to 
	 */
	public boolean equals(Edge e)
	{
        return(((e.getV1().equals(this.v1)) && (e.getV2().equals(this.v2))) || ((e.getV2().equals(this.v1) &&  (e.getV1().equals(this.v2)))));
	}
	
	/**
	 * Prints out the edge as a touple of its vectors
	 */
	public String toString()
	{
		return("( Name:" + v1.getName() + ", Id:" + v1.getId() + "\n" + "Name:" + v2.getName() + ",Id:" + v2.getId() + ")");
	}
	
	/**
	 * Checks to see if the edge contains a vertex in eather of its positions.
	 * @param v the vertex being comparerd to
	 */
	public boolean contains(Vertex v)
	{
		return(v1.equals(v) || v2.equals(v));
	}



} 
