package Graph;

import java.util.Random;

public class Colour
{
	int r;
	int g;
	int b;
	
	/**
	 * Represents a colour as a 3 intergers for the red green and blue proportions
	 * @param r
	 * @param g
	 * @param b
	 */
	public Colour(int r,int g,int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Creates a random colour by randomising all the interger values.
	 */
	public Colour()
	{
		Random rand = new Random();
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Creates a colour out of hex
	 * @parm String colour the colour in hex
	 */
	public Colour(String colour) 
	{
		if (colour.charAt(0) == '#')
		{
			String red = colour.substring(1,3);
			String green = colour.substring(3,5);
			String blue = colour.substring(5,7);

			r =	Integer.parseInt(red, 16);
			g =	Integer.parseInt(green, 16);
			b =	Integer.parseInt(blue, 16);
		}
		else
		{
			switch (colour.toUpperCase())
			{
				case "RED":
					r = 255;
					g = 0;
					b = 0;
					break;
				case "BLUE":
					r = 0;
					g = 0;
					b = 255;
					break;

				case "GREEN":
					r = 0;
					g = 255;
					b = 0;
					break;

				case "YELLOW":
					r = 255;
					g = 255;
					b = 0;
					break;

				case "PURPLE":
					r = 255;
					g = 0;
					b = 255;
					break;

				case "TEAL":
					r = 0;
					g = 255;
					b = 255;
					break;

				case "ORANGE":
					r = 255;
					g = 140;
					b = 0;
					break;

				default:
					r = 0;
					g = 0;
					b = 0;
					break;
			}
		}
	}
	
	public static final Colour RED = new Colour (255,0,0);
	
	public static final Colour GREEN = new Colour (0,255,0);
	
	public static final Colour BLUE = new Colour (0,0,255);
	
	public static final Colour YELLOW = new Colour(255,255,0);
	
	public static final Colour PURPLE = new Colour(255,0,255);
	
	public static final Colour TEAL = new Colour(0,255,255);
	
	public static final Colour BLACK = new Colour(0,0,0);
	
	/**
	 * Prints out the colour values
	 */
	public String toString()
	{ 
		return "rgb(" + r + "," + g + "," + b + ")";
	}
}
