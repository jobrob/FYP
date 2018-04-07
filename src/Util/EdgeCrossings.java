package Util;
import Graph.Vector;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class EdgeCrossings
{

	public static Vector[][] findEdges(List<String> strings,int size)
	{
		Vector[][] edgeCords = new Vector[size][2];
		Iterator lines = strings.iterator();
		
		int i = 0;
		while(lines.hasNext())
		{
			String line = "" + lines.next();
			if(line.contains("<line"))
			{
				int x1Start = line.indexOf("x1")+4;
				int x1End = line.indexOf("\"",x1Start);
				double x1 = Double.parseDouble(line.substring(x1Start,x1End));
				int y1Start = line.indexOf("y1")+4;
				int y1End = line.indexOf("\"",y1Start);
				double y1 = Double.parseDouble(line.substring(y1Start,y1End));
				Vector v1 = new Vector(x1,y1,0);
				
				int x2Start = line.indexOf("x2")+4;
				int x2End = line.indexOf("\"",x2Start);
				double x2 = Double.parseDouble(line.substring(x2Start,x2End));
				int y2Start = line.indexOf("y2")+4;	
				int y2End = line.indexOf("\"",y2Start);
				double y2 = Double.parseDouble(line.substring(y2Start,y2End));
				Vector v2 = new Vector(x2,y2,0);
				edgeCords[i][0] = v1;
				edgeCords[i][1] = v2;
				i++;
			}
		}
		return edgeCords;
	}
	
	public static double edgeCrossings(Vector[][] edgeCords)
	{
		int size = edgeCords.length;
		int totalEdgesCrossed = 0;
		for(int i = 0; i<size;i++)
		{
			for(int j = i+1; j<size; j++)
			{
				if(edgeCords[i][0] != null && edgeCords[j][0] != null)
				{
					if(!(edgeCords[i][0].equals(edgeCords[j][0]) || edgeCords[i][0].equals(edgeCords[j][1])) && 
					   !(edgeCords[i][1].equals(edgeCords[j][0]) || edgeCords[i][1].equals(edgeCords[j][1])))
					{

						if (doIntersect(edgeCords[i][0], edgeCords[i][1], edgeCords[j][0], edgeCords[j][1]))
						{
							totalEdgesCrossed++;
						}
					}
				}
			}		
		}
		return totalEdgesCrossed;
	}
	
	public static boolean onSegment(Vector p, Vector q, Vector r)
	{
		if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) && q.getY() <= Math.max(p.getY(), 				r.getY()) && q.getY() >= Math.min(p.getY(), r.getY()))
		{
 			return true;
		}
	    return false;
	}
	
	public static int orientation(Vector p, Vector q, Vector r)
	{
		double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
		          (q.getX() - p.getX()) * (r.getY() - q.getY());
	 
		if (val == 0) return 0;  // colinear
	 
		return (val > 0)? 1: 2; // clock or counterclock wise
	}
	
	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	public static boolean doIntersect(Vector p1, Vector q1, Vector p2, Vector q2)
	{
		// Find the four orientations needed for general and
		// special cases
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);
	 
		// General case
		if (o1 != o2 && o3 != o4)
		    return true;
	 
		// Special Cases
		// p1, q1 and p2 are colinear and p2 lies on segment p1q1
		if (o1 == 0 && onSegment(p1, p2, q1)) return true;
	 
		// p1, q1 and q2 are colinear and q2 lies on segment p1q1
		if (o2 == 0 && onSegment(p1, q2, q1)) return true;
	 
		// p2, q2 and p1 are colinear and p1 lies on segment p2q2
		if (o3 == 0 && onSegment(p2, p1, q2)) return true;
	 
		 // p2, q2 and q1 are colinear and q1 lies on segment p2q2
		if (o4 == 0 && onSegment(p2, q1, q2)) return true;
	 
		return false; // Doesn't fall in any of the above cases
	}


	public static void main(String[] args)
	{
		try
		{
			List<String> lines = Files.readAllLines(Paths.get("../output/72.svg"),StandardCharsets.UTF_8);
			Vector[][] edgeCords = findEdges(lines,lines.size());
			edgeCrossings(edgeCords);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
