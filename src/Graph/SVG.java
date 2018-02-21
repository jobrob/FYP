package Graph;

import java.io.IOException;
import java.nio.file.*;

public class SVG
{
	private static final double CANVAS_MARGIN 	= 50;
	private static final double NODE_RADIUS		= 5;
	private static final double BOX_MARGIN		= 1;

	/**
	 * Converts a vertex into a string for displaying with SVG
	 * @param v the vertex being converted to SVG
	 */
	public static String of(Vertex v,double xMin,double yMin)
	{
		if(v.getLabel().isEmpty())
		{
			return "<circle class=\"node\" cx=\"" + (v.getX() - xMin + 50) + "\" cy=\"" + (v.getY() - yMin + 50) + "\" r=\"" + NODE_RADIUS + "\"" + " fill=\"" + v.getColour() + "\" />  <!--" + v.getName() + "-->";	
		}
		else
		{
			return "<circle class= \"node\" fill=\"white\" stroke=\"#000000\" cx=\"" + (v.getX() - xMin + 50) + "\" cy=\"" + (v.getY() - yMin + 50) +  "\" r=\"" + 20 + "\"/>" + "\n"
					+ "\t<text text-anchor=\" middle\" x=\"" + (v.getX() - xMin + 48) +  "\" y=\"" + (v.getY() - yMin + 50) +  "\" font-family=\"Times,serif\" font-size=\"14.00\">" + v.getLabel() + "</text>";
		}
	}

	/**
	 * Converts an edge into a string for displaying with SVG 
	 * @param e the edge being converted
	 */
	public static String of(Edge e,double xMin,double yMin)
	{
		String result = "<line class=\"edge\"";
		
		if(!e.getColour().isEmpty())
		{
			result += " stroke = \"" + e.getColour() + "\" ";
		}
		else
		{
			result += " stroke = \"black\" ";
		}
		result +="x1=\"" + (e.getV1().getX() - xMin + 50) + "\" y1=\"" + (e.getV1().getY() - yMin + 50) + "\" x2=\"" + (e.getV2().getX() - xMin + 50) + "\" y2=\"" + (e.getV2().getY() - yMin + 50) + "\""; 
		result += "/> <!--" + e.getV1().getName() + "," + e.getV2().getName() + "-->";
		if(!e.getLabel().isEmpty())
		{
			result += "\t<text text-anchor=\" middle\" x=\"" + (e.xMid() - xMin + 48) +  "\" y=\"" + (e.yMid() - yMin + 50) +  "\" font-family=\"Times,serif\" font-size=\"14.00\">" + e.getLabel() + "</text>";
		}
		return result;
	}

	public static String of(Subgraph sg, double xMin,double yMin)
	{
		System.out.println(sg.getColour());
		String result = "";
		if(sg.isLeaf())
		{
			System.out.println(" the subgraph is a leaf it has a subgraph size of " + sg.getSg().size());
			double[] coords = sg.minMax();
			double originX 	= coords[0] - xMin 			- (BOX_MARGIN + NODE_RADIUS) + CANVAS_MARGIN;
			double originY 	= coords[1] - yMin 			- (BOX_MARGIN + NODE_RADIUS) + CANVAS_MARGIN;
			double width;
			if(coords[0] == xMin)
			{
				System.out.println("True");
				width = coords[2] - coords[0] + 2 * (BOX_MARGIN + NODE_RADIUS);
			}
			else
			{
				System.out.println("False");
				width = coords[2] - coords[0] + 2 * (BOX_MARGIN + NODE_RADIUS);
			}
			double height;
			if(coords[1] == yMin)
			{
				height = coords[3] - coords[1] + 2 * (BOX_MARGIN + NODE_RADIUS);
			}
			else
			{
				height = coords[3] - coords[1] + 2 * (BOX_MARGIN + NODE_RADIUS);
			}

			System.err.println("The height in svg " + height);
			System.err.println("The width in svg" + width);
			//		System.err.println(" ");

			return "<rect class=\"box\" x=\"" + originX + "\" y=\"" + originY +"\" width=\"" + width + "\" height=\"" + height +"\" fill = \"" + sg.getColour() + "\" />" + "\n";
		}

		else
		{
			System.out.println(" The subgraph is not a leaf");
			for(Subgraph subSg : sg.getSg())
			{
				result = result + SVG.of(subSg,xMin,yMin);
			}
			double[] coords = sg.minMax();
			double originX 	= coords[0] - xMin 			- (BOX_MARGIN + NODE_RADIUS) + CANVAS_MARGIN;
			double originY 	= coords[1] - yMin 			- (BOX_MARGIN + NODE_RADIUS) + CANVAS_MARGIN;
			double width;
			if(coords[0] == xMin)
			{
				System.out.println("True");
				width = coords[2] - coords[0] + 2 * (BOX_MARGIN + NODE_RADIUS);
			}
			else
			{
				System.out.println("False");
				width = coords[2] - coords[0] + 2 * (BOX_MARGIN + NODE_RADIUS);
			}
			double height;
			if(coords[1] == yMin)
			{
				height = coords[3] - coords[1] + 2 * (BOX_MARGIN + NODE_RADIUS);
			}
			else
			{
				height = coords[3] - coords[1] + 2 * (BOX_MARGIN + NODE_RADIUS);
			}

			System.err.println("The height in svg " + height);
			System.err.println("The width in svg" + width);
			//		System.err.println(" ");

			result +=  "<rect class=\"box\" x=\"" + originX + "\" y=\"" + originY +"\" width=\"" + width + "\" height=\"" + height +"\" fill = \"" + sg.getColour() + "\" />" + "\n";
			for(Subgraph subSg : sg.getSg())
			{
				result += SVG.of(subSg,xMin,yMin);
			}
			return result;
		}
	}

	/**
	 * Converts a graph into a string for displaying with SVG 
	 * @param g the graph being converted
	 */
	public static String of(Graph g)
	{
		return SVG.of(g,0,0,256,256);
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

	public static String of(Graph g, double xMin, double yMin, double xMax,double yMax)
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
		result += (0) + " " +(0) + " " + (xMax + 100 - xMin) + " " + (yMax + 100 - yMin);
		result += result2;
		result += "\t<rect x = \"" + (0) + "\" y=\"" +  (0) + "\" width = \"" + (xMax + 100 - xMin) + "\" height = \"" + (yMax + 100 - yMin) + "\" fill = \"rgb(255,255,255)\" />\n";

		for(Subgraph sg : g.getSg())
		{
			System.out.println("Making a svg subgraph");
			result += "    " + SVG.of(sg,xMin,yMin) + "\n";
		}
		for(Edge e : g.getE())
		{
			result += "    " + SVG.of(e,xMin,yMin) + "\n";
		}
		for(Vertex v : g.getV())
		{
			result += "    " + SVG.of(v,xMin,yMin) + "\n";
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
