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
	
	public Colour(String type, int paleness) {
		if (type.equals("saturated")) {
			Random random = new Random();
			int x = random.nextInt(256-paleness) + paleness;
			int y = random.nextInt(256-paleness) + paleness;
			int variant = random.nextInt(3);
			switch(variant) {
				case 0:
					r = x;
					g = y;
					b = 0;
					break;
				case 1:
					r = x;
					g = 0;
					b = y;
					break;
				case 2:
					r = 0;
					g = x;
					b = y;
					break;
				default:
					System.err.println("WHAT HAPPENED HERE?");
			}
		} else {
			System.err.println("You spelled 'saturated' incorrectly, doofus.");
		}
	}
	
	public Colour(String colour) {
		assert colour.charAt(0) == '#';
		assert colour.length() == 7;
		String red = colour.substring(1,3);
		String green = colour.substring(3,5);
		String blue = colour.substring(5,7);
		
		r =	Integer.parseInt(red, 16);
		g =	Integer.parseInt(green, 16);
		b =	Integer.parseInt(blue, 16);
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
