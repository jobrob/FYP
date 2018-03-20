package Util;
import java.util.*;

public class CharacterEscape
{
	/**
	 * Takes a string an returns a string that has its special characters replaced by the escaped values
	 * @return
	 */
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
				StringBuilder replacement = new StringBuilder(hashmap.get("" + input.charAt(i)));
				String[] parts = input.split("" + input.charAt(i));
				if(parts.length>0)
				{
					newInput.append(parts[0]);
					for(int j = 1; j<parts.length; j++)
					{
						newInput.append(replacement.append(parts[j]));
					}
				}
				else
				{
					newInput.append(replacement);
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