package Graph;

import java.io.IOException;
import java.nio.file.*;

public class SVG
{
	final static String NODE_RADIUS = "4";
	/**
	 * Converts a vertex into a string for displaying with SVG
	 * @param v the vertex being converted to SVG
	 */
	public static String of(Vertex v)
	{
		return "<circle class=\"node\" cx=\"" + v.getX() + "\" cy=\"" + v.getY() + "\" r=\"" + NODE_RADIUS + "\" />";	
	}
	
	/**
	 * Converts an edge into a string for displaying with SVG 
	 * @param e the edge being converted
	 */
	public static String of(Edge e)
	{
		return "<line class=\"edge\" x1=\"" + e.getV1().getX() + "\" y1=\"" + e.getV1().getY() + "\" x2=\""
											+ e.getV2().getX() + "\" y2=\"" + e.getV2().getY() + "\" />";
	}
	
	/**
	 * Converts a graph into a string for displaying with SVG 
	 * @param g the graph being converted
	 */
	public static String of(Graph g)
	{
		String result = null;
		try 
		{
			result = new String(Files.readAllBytes(Paths.get("../assets/template-header.svg")));
			//	System.out.println(result);
		} 
		catch (IOException e)
		{
			System.err.println("Couldn't get `template-header.svg`.");
			e.printStackTrace();
		}
		
		//if(result == null)
		//	return null;
		
		for(Vertex v : g.getV())
		{
			result += "    " + SVG.of(v) + "\n";
		}
		
		for(Edge e : g.getE())
		{
			result += "    " + SVG.of(e) + "\n";
		}
		result += "\n</svg>\n";
		return result;
	}
	
	public static String of(Graph g, double xmin, double ymin, double xmax, double ymax)
	{
		String result = null;
		String result2 = null;
		try 
		{
			result = new String(Files.readAllBytes(Paths.get("../assets/template-header-part1.svg")));
			result2 = new String(Files.readAllBytes(Paths.get("../assets/template-header-part2.svg")));
			//	System.out.println(result);
		} 
		catch (IOException e)
		{
			System.err.println("Couldn't get `template-header-part-part.svg`.");
			e.printStackTrace();
		}
		result += (xmin-50) + " " + (ymin-50) + " " + (xmax+50) + " " + (ymax+50);
		result += result2;
		for(Vertex v : g.getV())
		{
			result += "    " + SVG.of(v) + "\n";
		}
		
		for(Edge e : g.getE())
		{
			result += "    " + SVG.of(e) + "\n";
		}
		result += "\n</svg>\n";
		return result;
	}
	
	public static String of(Graph3D g3d)
	{
		String result = null;
		try 
		{
			result = new String(Files.readAllBytes(Paths.get("../assets/template-header.svg")));
			//	System.out.println(result);
		} 
		catch (IOException e)
		{
			System.err.println("Couldn't get `template-header.svg`.");
			e.printStackTrace();
		}
		for(Graph g :g3d.getG())
		{
			for(Vertex v : g.getV())
			{
				result += "    " + SVG.of(v) + "\n";
			}
			
			for(Edge e : g.getE())
			{
				result += "    " + SVG.of(e) + "\n";
			}
		}
		result += "\n</svg>\n";
		return result;
	}
}
