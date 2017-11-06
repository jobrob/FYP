package Graph;

import java.io.IOException;
import java.nio.file.*;

public class SVG
{
	final static String NODE_RADIUS = "4";
	
	public static String of(Vertex v)
	{
		return "<circle class=\"node\" cx=\"" + v.getX() + "\" cy=\"" + v.getY() + "\" r=\"" + NODE_RADIUS + "\" />";	
	}
	
	public static String of(Edge e)
	{
		return "<line class=\"edge\" x1=\"" + e.getV1().getX() + "\" y1=\"" + e.getV1().getY() + "\" x2=\""
											+ e.getV2().getX() + "\" y2=\"" + e.getV2().getY() + "\" />";
	}
	
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
}
