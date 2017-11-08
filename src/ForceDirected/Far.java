package ForceDirected;

import java.io.PrintWriter;

import Graph.*;

public class Far 
{
	private Graph graph;
	int width;
	int height;
	int k;
	public Far (Graph graph, int width,int height)
	{
		this.graph = graph;
		this.width = width;
		this.height = height;
		k = width*height/(graph.getV().size());
	}
	
	public void simulate(int i)
	{
		for(Vertex v : graph.getV())
		{
			v.randomise(Math.min(width, height));
		}
		for(int j = 0 ; j<i; j++)
		{
			for(Vertex v: graph.getV())
			{
				netRforce(v);
			}
			for(Edge e : graph.getE())
			{
				netAforce(e);
			}
			for(Vertex v : graph.getV())
			{
				move(v);
			}
		}
		try
		{
			PrintWriter writer = new PrintWriter("../FAR.svg","UTF-8");
			writer.print(SVG.of(graph));
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void netRforce(Vertex v) 
	{
		v.setDisplacement(Vector.ZERO);
		for(Vertex u : graph.getV())
		{
			if(!u.equals(v))
			{
				Vector s = u.getPosition().minus(v.getPosition());
				double r = s.length();
				v.setDisplacement(s.negate().normalise().scale(Math.pow(k,2)/r));
				
			}
		}
	}

	private void netAforce(Edge e)
	{
		
	}
	
	private void move(Vertex v)
	{
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}

}
