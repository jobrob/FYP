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
	
	public static final Colour RED = new Colour (255,0,0);
	
	public static final Colour GREEN = new Colour (0,255,0);
	
	public static final Colour BLUE = new Colour (0,0,255);
	
	public static final Colour YELLOW = new Colour(255,255,0);
	
	public static final Colour PURPLE = new Colour(255,0,255);
	
	public static final Colour TEAL = new Colour(0,255,255);
	
	public static final Colour BLACK = new Colour(0,0,0);
	/**
	 * Prints out the colour values in a convienet way for the SVG
	 */
	public String toString()
	{ 
		return "rgb(" + r + "," + g + "," + b + ")";
	}
}
