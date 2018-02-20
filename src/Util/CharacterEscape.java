package Util;
import java.util.*;

public class CharacterEscape
{
	public static String escapeHtml(String input)
	{
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("\"","&quot;");
		hashmap.put("&","&amp;");
		hashmap.put("<","&lt;");
		hashmap.put(">","&gt;");
		String newInput ="";
		for(int i = 0;i <input.length(); i++)
		{
			if(hashmap.containsKey("" + input.charAt(i)))
			{
				String replacement = hashmap.get("" + input.charAt(i));
				System.out.println("The raplcement is " + replacement);
				String[] parts = input.split("" + input.charAt(i));
				newInput = parts[0];
				for(int j = 1; j<parts.length; j++)
				{
					System.out.println(parts[j]);
					newInput += parts[j] + replacement;
				}
				System.out.println("replaceing ");
			}
		}
		if(newInput.isEmpty())
		{
			newInput = input;
		}
		System.out.println("the new input " + newInput);
		return newInput;
	}
}