/*
 * QUESTION:
 *
 * Design a Car Management System using OOP.
 *
 * Each car has:
 *   - ID
 *   - Name
 *   - Price
 *
 * Store multiple cars and find the car having the highest price.
 *
 * EXAMPLE INPUT:
 *
 * 3
 * 101, BMW, 8000000
 * 102, Audi, 7500000
 * 103, Mercedes, 9000000
 *
 * EXAMPLE OUTPUT:
 *
 * Most Expensive car: Mercedes
 */

import java.util.*;

class Car
{
    private int id;
    private String name;
    private double price;

    public Car(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class CarManager
{
    List<Car> cars;

    public CarManager(List<Car> cars) {
        this.cars = cars;
    }

    public void getCarNamesWithHighestPrice() {
        String res = null;
        double maxPrice = 0.0;

        for(Car car : cars)
        {
            if(car.getPrice() > maxPrice)
            {
                maxPrice = car.getPrice();
                res = car.getName();
            }
        }

        System.out.println("Most Expensive car amongst all cars: " + res);
    }
}

public class CarsWIthHighestPrice {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        List<Car> cars = new ArrayList<>();

        System.out.print("Enter number of cars: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++)
        {
            System.out.println("\nCar " + (i + 1));

            System.out.print("Enter car ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter car name: ");
            String name = sc.nextLine();

            System.out.print("Enter car price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            cars.add(new Car(id, name, price));
        }

        CarManager manager = new CarManager(cars);

        System.out.println("\n===== RESULT =====");

        manager.getCarNamesWithHighestPrice();

        sc.close();
    }
}
