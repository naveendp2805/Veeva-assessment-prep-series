/*
* QUESTION:
* Given a comma-separated string containing color names,
* count the frequency of each color.
*
* Rules:
* 1. Ignore spaces before and after color names.
* 2. Treat uppercase and lowercase letters as the same.
* 3. Remove numbers and special characters from color names.
*
* EXAMPLE INPUT:
* Red, bl@ue, gr3en, yellow!, RED
*
* EXAMPLE OUTPUT:
* red - 2
* blue - 1
* green - 1
* yellow - 1
*/

import java.util.*;

public class FrequencyOfColors {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter comma seperated string: ");
        String str = sc.nextLine();

        HashMap<String, Integer> res = findFreq(str);
        
        System.out.println("Frequencies of colours: ");
        for(Map.Entry<String, Integer> entry : res.entrySet())
            System.out.println(entry.getKey() + " : " + entry.getValue());

        sc.close();
    }

    private static HashMap<String, Integer> findFreq(String str) {
        String[] colors = str.toLowerCase().split(",");

        HashMap<String, Integer> res = new HashMap<>();

        for(String color : colors)
        {
            color = color.trim();
            color = color.replaceAll("[^a-zA-Z]", "");

            res.put(color, res.getOrDefault(color, 0) + 1);
        }

        return res;
    }
}
