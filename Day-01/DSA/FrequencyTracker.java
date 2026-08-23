/*
 * Problem: Word Frequency Tracker
 *
 * Given a string containing words, numbers, and special characters,
 * find the frequency of each word and the total number of words.
 *
 * Requirements:
 * - Consider only alphabetic characters (A-Z, a-z) as words.
 * - Ignore numbers and special characters.
 * - Numbers and special characters may be attached to words.
 * - Treat uppercase and lowercase versions of the same word as equal.
 *
 * Example:
 * Input:
 * "Hello123 hello @world world! Java8 java 123"
 *
 * Extracted words:
 * hello, hello, world, world, java, java
 *
 * Output:
 * hello : 2
 * world : 2
 * java  : 2
 *
 * Total words: 6
 *
 * Approach:
 * Use regular expressions to remove all characters except alphabets,
 * then split the resulting string into words.
 *
 * Use a HashMap to store:
 *     word -> frequency
 *
 * Regex:
 *     [^a-zA-Z]+
 *
 * This matches one or more characters that are NOT alphabets.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */

import java.util.*;

public class FrequencyTracker 
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter input String: ");
        String s = sc.nextLine();

        freqTracker(s, s.length());

        sc.close();
    }

    private static void freqTracker(String s, int n) {
        
        String[] words = s.replaceAll("[^a-zA-z]+", " ").trim().split("\\s+");

        HashMap<String, Integer> map = new HashMap<>();
        int totalWords = words.length;

        for(String word : words)
        {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        System.out.println("Words with their frequencies: ");
        for(Map.Entry<String, Integer> entry : map.entrySet())
            System.out.println(entry.getKey() + " : " + entry.getValue());

        System.out.println("Total words : " + totalWords);
    }

}
