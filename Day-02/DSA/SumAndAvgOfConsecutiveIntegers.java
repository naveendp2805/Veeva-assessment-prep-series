/*
 * QUESTION:
 * Given a string containing letters and numbers, extract all the
 * integers from the string and calculate their sum and average.
 *
 * EXAMPLE INPUT:
 * abc1110kg451y490
 *
 * EXAMPLE OUTPUT:
 * Sum: 2051
 * Average: 683.67
 */

import java.util.*;

public class SumAndAvgOfConsecutiveIntegers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter string: ");
        String str = sc.nextLine();

        sumAndAvg(str, str.length());

        sc.close();
    }

    private static void sumAndAvg(String str, int n) {
        int sum = 0, c=0, s = 0;

        for(int i=0; i<n; i++)
        {
            char ch = str.charAt(i);

            if(Character.isLetter(ch)) {
                sum += s;
                if(s != 0) c++;
                s = 0;
            } else 
                s = s * 10 + (ch - '0');
        }

        if(s != 0)
        {
            sum += s;
            c++;
        }

        System.out.println("Sum: " + sum + "\nAvg: " + (double)(sum / c));
    }    
}
