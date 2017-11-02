import java.lang.Math;
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
	
	public FDD(Graph g) {
		this.g = g;
	}
	
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
	
	public Vector netForce(Vertex v)
	{
		return netEForce(v).plus(netSForce(v));
	}
	
	public Vector netEForce(Vertex v) {	
		Vector result = Vector.ZERO;
		for (Vertex v2 : g.getV()) {
			if(!v2.equals(v)) {
				result = result.plus(eForce(v, v2));
			}
		}
		return result;
	}
	
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
	 */
	public Vector eForce(Vertex on, Vertex by) {
		Vector s = by.getVector().minus(on.getVector());
		double r = s.length();
		if(r < MIN_EFORCE_RADIUS) {
			return s.negate().normalise().scale(MAX_EFORCE_MAG);
		}
		return s.negate().normalise().scale(c3 / (r*r*r));
	}
	
	public Vector sForce (Vertex on, Vertex by)
	{
		Vector s = by.getVector().minus(on.getVector());
		double r = s.length();
		return s.negate().normalise().scale(c1*Math.log(r/c2));
	}
	
	public static void main(Strings args[])
	{
		
	}
	
	
	
		
}
