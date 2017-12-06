package Graph;

import java.util.Random;

public class Colour
{
	int r;
	int g;
	int b;
	
	public Colour(int r,int g,int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
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
	
	public String toString()
	{ 
		return "rgb(" + r + "," + g + "," + b + ")";
	}
}
