package Util;
import java.util.*;

public class CharacterEscape
{
	public static String escapeHtml(String input)
	{
		HashMap<String, String> hashmap = new HashMap<>();
		hashmap.put("\"","&quot;");
		hashmap.put("&","&amp;");
		hashmap.put("<","&lt;");
		hashmap.put(">","&gt;");
		StringBuilder newInput = new StringBuilder();
		for(int i = 0;i <input.length(); i++)
		{
			if(hashmap.containsKey("" + input.charAt(i)))
			{
				String replacement = hashmap.get("" + input.charAt(i));
				String[] parts = input.split("" + input.charAt(i));
				newInput = new StringBuilder(parts[0]);
				for(int j = 1; j<parts.length; j++)
				{
					newInput.append(parts[j]).append(replacement);
				}
			}
		}
		if(newInput.length() == 0)
		{
			newInput = new StringBuilder(input);
		}
		return newInput.toString();
	}
}