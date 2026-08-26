/*
QUESTION:
Design a Java OOP-based system to store a collection of books.

Each book contains:
- ISBN number
- Title
- Author
- Price

The ISBN number uniquely identifies a book, so duplicate books
with the same ISBN should not be stored.

Store the books using a Set and override equals() and hashCode()
based on the ISBN number.

Then, given an ISBN number, search for the corresponding book
and display its details.

EXAMPLE INPUT:
3
9780134685991, Effective Java, Joshua Bloch, 750
9781492078005, Java Concurrency, Brian Goetz, 900
9780134685991, Effective Java, Joshua Bloch, 750

Search ISBN:
9780134685991

EXPECTED OUTPUT:
Book Found:
ISBN: 9780134685991
Title: Effective Java
Author: Joshua Bloch
Price: 750.0

NOTE:
The two books with ISBN 9780134685991 are considered the same
because ISBN is used in equals() and hashCode(). Therefore,
only one of them is stored in the HashSet.
*/

import java.util.*;

class Book
{
    private String isbnNumber;
    private String title;
    private String author;
    private double price;

    public Book(String isbnNumber, String title, String author, double price)
    {
        this.isbnNumber = isbnNumber;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getIsbn() {
        return isbnNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int hashCode() {
        return isbnNumber.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Book book = (Book) obj;

        return isbnNumber.equals(book.isbnNumber);
    }

    @Override
    public String toString()
    {
        return "ISBN: " + isbnNumber + ", Title: " + title + ", Author: " + author + ", Price: " + price;
    }
}

class BookManager
{
    Set<Book> books;

    public BookManager(Set<Book> books) {
        this.books = books;
    }
    
    public void getBookDetailsOfGivenISBNNumber(String isbnNumber) {
        for(Book book : books)
        {
            if(book.getIsbn().equals(isbnNumber))
            {
                System.out.println(book);
                return;
            }
        }

        System.out.println("Book with ISBN Number: " + isbnNumber + " not found!!");
    }

}

public class BooksWithISBNNumber
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Set<Book> books = new HashSet<>();

        System.out.print("Enter number of books: ");
        int n = sc.nextInt();
        sc.nextLine();

        for(int i = 0; i < n; i++)
        {
            System.out.println("\nEnter details of Book " + (i + 1));

            System.out.print("ISBN: ");
            String isbn = sc.nextLine();

            System.out.print("Title: ");
            String title = sc.nextLine();

            System.out.print("Author: ");
            String author = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            books.add(new Book(isbn, title, author, price));
        }

        BookManager manager = new BookManager(books);

        System.out.print("\nEnter ISBN number to search: ");
        String searchISBN = sc.nextLine();

        manager.getBookDetailsOfGivenISBNNumber(searchISBN);

        sc.close();
    }
}
