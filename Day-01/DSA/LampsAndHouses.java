/*
 * Problem: Lamps and Houses
 *
 * Given the positions of houses and lamps on a straight street,
 * find the minimum radius required so that every house is covered
 * by at least one lamp.
 *
 * For each house, find the distance to its nearest lamp.
 * The required radius is the maximum of these minimum distances.
 *
 * Example:
 * Houses = [0, 5, 10]
 * Lamps  = [5]
 *
 * Distance from each house to the nearest lamp:
 * 0  -> 5
 * 5  -> 0
 * 10 -> 5
 *
 * Minimum radius required = 5
 *
 * Approach:
 * For every house, calculate its distance from every lamp and
 * find the minimum distance.
 * The maximum among all these minimum distances is the answer.
 *
 * Time Complexity: O(H * L)
 * Space Complexity: O(1)
 *
 * H = number of houses
 * L = number of lamps
 */

import java.util.*;

public class LampsAndHouses {
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter no.of houses: ");
        int n1 = sc.nextInt();

        System.out.println("Enter house positions: ");
        int[] houses = new int[n1];
        for(int i=0; i<n1; i++)
            houses[i] = sc.nextInt();

        System.out.println("Enter no.of lamps: ");
        int n2 = sc.nextInt();

        System.out.println("Enter lamps positions: ");
        int[] lamps = new int[n2];
        for(int i=0; i<n2; i++)
            lamps[i] = sc.nextInt();

        System.out.println("Minimum range of radius to light all houses: " + minRadius(houses, n1, lamps, n2));

        sc.close();
    }

    private static int minRadius(int[] houses, int n1, int[] lamps, int n2) {
        int res = -1;

        for(int i=0; i<n1; i++)
        {
            int min = Integer.MAX_VALUE;
            for(int j=0; j<n2; j++)
                min = Math.min(min, Math.abs(houses[i] - lamps[j]));

            res = Math.max(res, min);
        }

        return res;
    }
}
