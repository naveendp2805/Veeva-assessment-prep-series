/*
 * QUESTION:
 * Given an array containing positive and negative integers,
 * find the minimum distance between any two numbers.
 *
 * The distance between two numbers is their absolute difference.
 *
 * EXAMPLE INPUT:
 * [5, -2, 8, -3, 10]
 *
 * EXAMPLE OUTPUT:
 * 1
 *
 * Explanation:
 * The closest numbers are -3 and -2.
 * Distance = |-3 - (-2)| = 1
 */

import java.util.*;

public class ClosestNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter size of array: ");
        int n = sc.nextInt();

        System.out.println("Enter array elements: ");
        int[] arr = new int[n];
        for(int i=0; i<n; i++)
            arr[i] = sc.nextInt();

        System.out.println("Closest distance: " + findClosestDist(arr, n));

        sc.close();
    }

    private static int findClosestDist(int[] arr, int n) {
        int res = Integer.MAX_VALUE;

        Arrays.sort(arr);

        for(int i=1; i<n; i++)
            res = Math.min(res, arr[i] - arr[i-1]);

        return res;
    }
}
