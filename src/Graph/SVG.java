package Graph;

import java.io.IOException;
import java.nio.file.*;

public class SVG
{
	private static final double CANVAS_MARGIN 	= 50;
	private static final double NODE_RADIUS		= 5;
	private static final double BOX_MARGIN		= 1;
	private static final double SCALE_FACTOR	= 20;

	/**
	 * Converts a vertex into a string for displaying with SVG
	 * @param v the vertex being converted to SVG
	 */
	public static String of(Vertex v,double xMin,double yMin)
	{
		if(v.getLabel().isEmpty())
		{
			return "<circle class=\"node\" cx=\"" + (v.getX() - xMin + CANVAS_MARGIN) + "\" cy=\"" + (v.getY() - yMin + CANVAS_MARGIN) + "\" r=\"" + NODE_RADIUS + "\"" + " fill=\"" + v.getColour() + "\" />  <!--" + v.getName() + "-->";
		}
		else
		{
			return "<circle class= \"node\" fill=\"white\" stroke=\"" + v.getColour() + "\" cx=\"" + (v.getX() - xMin + CANVAS_MARGIN) + "\" cy=\"" + (v.getY() - yMin + CANVAS_MARGIN) +  "\" r=\"" + 20 + "\"/>" + "\n"
						+ "\t<text text-anchor=\"middle\" x=\"" + (v.getX() - xMin + CANVAS_MARGIN) +  "\" y=\"" + (v.getY() - yMin + CANVAS_MARGIN) +  "\" font-family=\"Times,serif\" font-size=\"14.00\">" + v.getLabel() + "</text>";
		}
	}

	/**
	 * Converts an edge into a string for displaying with SVG 
	 * @param e the edge being converted
	 */
	public static String of(Edge e,double xMin,double yMin)
	{
		String result = "";
		if(e.getRepeats() != 0)
        {
			for(int i = 0; i <= e.getRepeats() ; i++)
            {
				double v1X = e.getV1().getX();
				double v1Y = e.getV1().getY();
				double v2X = e.getV2().getX();
				double v2Y = e.getV2().getY();
				double xMid = ((v1X + v2X)/2);
				double yMid = ((v1Y + v2Y)/2);
				double[][] rotationMatrix = new double[][]{{0,-1},{1,0}};
				Vector midpoint = e.getV2().getPosition().plus( e.getV1().getPosition() ).scale(0.5);
				Vector direction = e.getV2().getPosition().minus( e.getV1().getPosition() );
				if (i % 2 == 0) {
					direction = direction.negate();
				}
				Vector orthogonalDirection = direction.matrixMultiplication(rotationMatrix).normalise().scale(SCALE_FACTOR);

				int separation = (int)Math.ceil(i/2.0);
				Vector controlPoint = midpoint.plus(orthogonalDirection.scale(separation));
				result += "\n<path d =\"M" +  (e.getV1().getX() - xMin + CANVAS_MARGIN) + " " + (e.getV1().getY() - yMin +CANVAS_MARGIN) + " Q" + (controlPoint.getX() - xMin + CANVAS_MARGIN) + " " + (controlPoint.getY() - yMin + CANVAS_MARGIN) + " " + (e.getV2().getX() - xMin + CANVAS_MARGIN) + " " + (e.getV2().getY() - yMin + CANVAS_MARGIN) + "\" stroke = \"black\" stroke-width = \"1\" fill=\"none\" />";
				if(e.getLabels().size() > i && !e.getLabels().get(i).isEmpty())
				{
					result += "\t<text text-anchor=\"middle\" x=\"" + (controlPoint.getX() - xMin + CANVAS_MARGIN) +  "\" y=\"" + (controlPoint.getY() - yMin + CANVAS_MARGIN) +  "\" font-family=\"Times,serif\" font-size=\"14.00\">" + e.getLabels().get(i) + "</text>";
				}
			}
		}
        else
        {
        	result += "<line class=\"edge\"";
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
			if(!e.getLabels().get(0).isEmpty())
			{
				result += "\t<text text-anchor=\"middle\" x=\"" + (e.xMid() - xMin + 48) +  "\" y=\"" + (e.yMid() - yMin + 50) +  "\" font-family=\"Times,serif\" font-size=\"14.00\">" + e.getLabels().get(0) + "</text>";
			}
		}
		return result;
	}

	public static String of(Subgraph sg, double xMin, double yMin)
	{
		StringBuilder result = new StringBuilder();
		if(sg.isLeaf())
		{
			double[] coords = sg.minMax();
			double originX 	= coords[0] - xMin	- (BOX_MARGIN + NODE_RADIUS) + CANVAS_MARGIN;
			double originY 	= coords[1] - yMin	- (BOX_MARGIN + NODE_RADIUS) + CANVAS_MARGIN;
			double width, height;
			width 	= coords[2] - coords[0] + 2 * (BOX_MARGIN + NODE_RADIUS);
			height 	= coords[3] - coords[1] + 2 * (BOX_MARGIN + NODE_RADIUS);

			return "<rect class=\"box\" x=\"" + originX + "\" y=\"" + originY +"\" width=\"" + width + "\" height=\"" + height +"\" fill=\"" + sg.getColour() + "\" />" + "\n";
			//  <rect class="box" x="originX" y="originY" width="width" height="height" fill="sg.getColour()" />
		}

		else
		{
			for(Subgraph subSg : sg.getSg())
			{
				result.append(SVG.of(subSg, xMin, yMin));
			}
			double[] coords = sg.minMax();
			double originX 	= coords[0] - xMin 			- (BOX_MARGIN + NODE_RADIUS) + CANVAS_MARGIN;
			double originY 	= coords[1] - yMin 			- (BOX_MARGIN + NODE_RADIUS) + CANVAS_MARGIN;
			double width;
			if(coords[0] == xMin)
			{
				width = coords[2] - coords[0] + 2 * (BOX_MARGIN + NODE_RADIUS);
			}
			else
			{
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
			//		System.err.println(" ");

			result.append("<rect class=\"box\" x=\"").append(originX).append("\" y=\"").append(originY).append("\" width=\"").append(width).append("\" height=\"").append(height).append("\" fill = \"").append(sg.getColour()).append("\" />").append("\n");
			for(Subgraph subSg : sg.getSg())
			{
				result.append(SVG.of(subSg, xMin, yMin));
			}
			return result.toString();
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
		StringBuilder result = null;
		String result2 = null;
		try 
		{
			result = new StringBuilder(new String(Files.readAllBytes(Paths.get("../assets/template-header-part1.svg"))));
			result2 = new String(Files.readAllBytes(Paths.get("../assets/template-header-part2.svg")));
			//	System.out.println(result);
		} 
		catch (IOException e)
		{
			System.err.println("Couldn't get `template-header-part-part.svg`.");
			e.printStackTrace();
		}
		assert result != null;
		double width = xMax + 100 - xMin;
		double height = yMax + 100 - yMin;
		if(width<100)
		{
			width = 100;
		}
		if(height <100)
		{
			height = 100;
		}
		result.append((0) + " " + (0) + " ").append(width).append(" ").append(height);
		result.append(result2);
		result.append("\t<rect x = \"" + (0) + "\" y=\"" + (0) + "\" width = \"").append(width).append("\" height = \"").append(height).append("\" fill = \"rgb(255,255,255)\" />\n");
		for(Subgraph sg : g.getSg())
		{
			result.append("    ").append(SVG.of(sg, xMin, yMin)).append("\n");
		}
		for(Edge e : g.getE())
		{

			result.append("    ").append(SVG.of(e, xMin, yMin)).append("\n");
		}
		for(Vertex v : g.getV())
		{
			result.append("    ").append(SVG.of(v, xMin, yMin)).append("\n");
		}
		result.append("\n</svg>\n");
		return result.toString();
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
