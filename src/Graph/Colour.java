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
	
	public String toString()
	{ 
		return "rgb(" + r + "," + g + "," + b + ")";
	}
}
