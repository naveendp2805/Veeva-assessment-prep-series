/*
 * Problem: Subarrays With Sum K
 *
 * Given an integer array containing positive numbers, negative numbers,
 * and zeroes, and an integer target K, find the number of contiguous
 * subarrays whose sum is exactly equal to K.
 *
 * Example:
 * Input:
 * arr = [1, 2, 3]
 * K = 3
 *
 * Valid subarrays:
 * [1, 2] -> 3
 * [3]    -> 3
 *
 * Output:
 * 2
 *
 * Approach:
 * Use Prefix Sum and HashMap.
 *
 * For every position, calculate the current prefix sum.
 * If the current prefix sum is 'sum', then we need a previous
 * prefix sum equal to (sum - K) to form a subarray with sum K.
 *
 * The HashMap stores:
 *     prefix sum -> number of times that prefix sum has occurred
 *
 * Initialize the map with (0, 1) to handle subarrays that start
 * from index 0.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */

import java.util.*;

public class SubarraysWithSumK
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter array size: ");
        int n = sc.nextInt();

        System.out.println("Enter array elements: ");
        int[] arr = new int[n];
        for(int i=0; i<n; i++)
            arr[i] = sc.nextInt();

        System.out.println("Enter target value: ");
        int target = sc.nextInt();

        System.out.println("Subarray's count: " + countSubarrays(arr, n, target));

        sc.close();
    }

    private static int countSubarrays(int[] arr, int n, int target) {
        int count = 0, sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        for(int i=0; i<n; i++)
        {
            sum += arr[i];

            if(map.containsKey(sum - target)) 
                count += map.get(sum - target);

            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }
}