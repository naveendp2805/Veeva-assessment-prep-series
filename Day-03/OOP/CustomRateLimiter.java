/*
 * Description:
 *
 * Design a custom rate limiter that controls how frequently the same
 * message can be printed.
 *
 * A message can be printed only once within a 10-second time window.
 * If the same message is received again before 10 seconds have passed,
 * it should be rejected and the remaining time should be displayed.
 *
 * If 10 seconds have passed since the message was last printed, the
 * message should be allowed again.
 *
 * Use a HashMap to store:
 *     message -> last printed timestamp
 *
 * The timestamp should be obtained automatically using Java's
 * built-in time API in seconds.
 *
 * Example:
 *
 * Input:
 *     login
 *     login
 *
 * Output:
 *     login
 *     Please try again after 8 seconds.
 *
 * The program should provide a menu to:
 *     1. Enter a message
 *     2. Exit
 */

import java.util.*;
import java.time.*;

public class CustomRateLimiter {

    HashMap<String, Long> map;

    
    public CustomRateLimiter() {
        this.map = new HashMap<>();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        CustomRateLimiter limiter = new CustomRateLimiter();

        while(true)
        {
            System.out.println("\n1: Enter message\n2: Exit");
            System.out.println("Enter vhoice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            if(ch == 1) {
                String msg = sc.nextLine();

                long timestamp = Instant.now().getEpochSecond();


                if (limiter.shouldPrintMessage(msg, timestamp))
                    System.out.println("Request allowed");
                else
                    System.out.println("Request blocked");
            } else if (ch == 2) {
                sc.close();
                return;
            } else
                System.out.println("Invalid choice.");
        }
    }

    private boolean shouldPrintMessage(String msg, long timestamp) {
        
        if(!map.containsKey(msg) || timestamp - map.get(msg) >= 10)
        {
            System.out.println(msg);
            map.put(msg, timestamp);
            return true;
        }
 
        long elapsed = timestamp - map.get(msg);
        long remaining = 10 - elapsed;

        System.err.println("Please try again after " + remaining + " seconds.");

        return false;
    }
}
