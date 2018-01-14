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
	public static String of(Vertex v,double min)
	{
		return "<circle class=\"node\" cx=\"" + (v.getX() + min + 50) + "\" cy=\"" + (v.getY() + min + 50) + "\" r=\"" + NODE_RADIUS + "\"" + " fill=\"" + v.getColour() + "\" />";	
	}
	
	/**
	 * Converts an edge into a string for displaying with SVG 
	 * @param e the edge being converted
	 */
	public static String of(Edge e,double min)
	{
		return "<line class=\"edge\" x1=\"" + (e.getV1().getX() + min + 50) + "\" y1=\"" + (e.getV1().getY() + min + 50) + "\" x2=\""
											+ (e.getV2().getX() + min + 50) + "\" y2=\"" + (e.getV2().getY() + min + 50) + "\" />";
	}
	
	/**
	 * Converts a graph into a string for displaying with SVG 
	 * @param g the graph being converted
	 */
	public static String of(Graph g)
	{
		return SVG.of(g,0,256);
		/*String result = null;
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
		return result;*/
	}
	
	public static String of(Graph g, double min, double max)
	{
		System.out.println("min = " + min);
		System.out.println("max = " + max);
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
		double absMin = Math.abs(min);
		result += (0) + " " +(0) + " " + (max+100+absMin) + " " + (max+100+absMin);
		result += result2;
		result += "\t<rect x = \"" + (0) + "\" y=\"" +  (0) + "\" width = \"" + (max+100+absMin) + "\" height = \"" + (max + 100 +absMin) + "\" fill = \"rgb(255,255,255)\" />\n";
		
		for(Edge e : g.getE())
		{
			result += "    " + SVG.of(e,absMin) + "\n";
		}
		for(Vertex v : g.getV())
		{
			result += "    " + SVG.of(v,absMin) + "\n";
		}
		result += "\n</svg>\n";
		return result;
	}
	
	/*public static String of(Graph3D g3d)
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
			for(Edge e : g.getE())
			{
				result += "    " + SVG.of(e) + "\n";
			}
			for(Vertex v : g.getV())
			{
				result += "    " + SVG.of(v) + "\n";
			}
		}
		result += "\n</svg>\n";
		return result;
	}*/
}
