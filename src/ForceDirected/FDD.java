import java.lang.Math;
import Graph.*
public class FDD
{
	private double c1 = 2;
	private double c2 = 1;
	private double c3 = 1;
	private double c4 = 0.1;
	
	private final double MIN_EFORCE_RADIUS = 0.01;
	private final double MAX_EFORCE_MAG =
		c3 / (MIN_EFORCE_RADIUS * MIN_EFORCE_RADIUS * MIN_EFORCE_RADIUS);
	
	//Attraction caused by an edge = c1*log(d/c2)
	//Repulsion caused by two vertices = c3/d^2
	
	
	private Graph g;
	/**
	 * Creates a FDD of a given graph
	 * @param g The graph
	 */
	public FDD(Graph g) 
	{
		this.g = g;
	}
	
	/**
	 * Calculates the froces i times
	 * @param i the number of times the forces will be calculated.
	 */
	public String simulate(int i)
	{
		//randomise
		for(int i = 0;i<g.getV().size();i++)
		{
			
		}
	}
	
	/**
	 * Applies both the electrostatic and the spring force
	 */
	public void applyForces() 
	{
		Vector[] displacement = new Vector[g.getV().size()];
		for(int i = 0; i < g.getV().size(); i++)
		{
			displacement[i] = netForce(g.getV().get(i));
		}
		
		for(int i = 0; i < g.getV().size(); i++)
		{
			g.getV().get(i).setVector
			(
				g.getV().get(i).getVector().plus
				(
					displacement[i]
				)
			);
		}
	}
	
	/**
	 * calculates the net force on a vertex
	 * @param v the vertex which the net force will be calculated on
	 */
	public Vector netForce(Vertex v)
	{
		return netEForce(v).plus(netSForce(v));
	}
	
	/*
	 * calculates the total electrostatic foce on a given vertex from all other vertices in the graph
	 * @param v the vertex 
	 */
	public Vector netEForce(Vertex v) {	
		Vector result = Vector.ZERO;
		for (Vertex v2 : g.getV()) {
			if(!v2.equals(v)) {
				result = result.plus(eForce(v, v2));
			}
		}
		return result;
	}
	
	/**
	 * Calculates the net spring foce on a vertex v
	 * Caused by all vertices in the neighbourhood of v
	 * @param v the vertex being considered.
	 */
	public Vector netSForce(Vertex v) {
		Vector result = Vector.ZERO;
		for(Vertex v2 : g.neighbourhood(v)) {
			result = result.plus(sForce(v, v2));
		}
		return result;
	}
	
	/**
	 * Get the "electrostatic" force induced on the
	 * Vertex `on` as caused by the Vertex `by`. This
	 * force follows an inverse-square law.
	 * @param on the vertex which feals the force
	 * @param by the vertex which exerts the force
	 */
	public Vector eForce(Vertex on, Vertex by) {
		Vector s = by.getVector().minus(on.getVector());
		double r = s.length();
		if(r < MIN_EFORCE_RADIUS) {
			return s.negate().normalise().scale(MAX_EFORCE_MAG);
		}
		return s.negate().normalise().scale(c3 / (r*r*r));
	}
	
	/**
	 * Get the spring force induced on
	 * the Vertex 'on' caused the Vertex 'by' 
	 * which shares an edge with 'on'
	 * @param on the vertex which feals the force
	 * @param by the vertex which exerts the force
	 */
	public Vector sForce (Vertex on, Vertex by)
	{
		Vector s = by.getVector().minus(on.getVector());
		double r = s.length();
		return s.negate().normalise().scale(c1*Math.log(r/c2));
	}
	
	public static void main(Strings args[])
	{
		ArrayList<Vertex> V = new ArrayList<Vertex>();
		ArrayList<Edge> E = new ArrayList<Edge>();
		for(int i = 0;i<10;i++)
		{
			Vertex temp = new Vertex(String.valueOf(i));
			Vertex temp2 = new Vertex(String.valueOf(i+10));
			V.add(temp);
			V.add(temp2);
			Edge te = new Edge(temp,temp2);
			E.add(te);
		}
		Graph graph = new Graph(V,E);
		FDD = new FDD(Graph);
		FDD.run(100);
		
	}
}
