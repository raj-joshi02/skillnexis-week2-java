import java.util.ArrayList;
import java.util.List;

/**
 * WEEK 2 - Object-Oriented Programming (OOP Concepts)
 * Run:  javac Week2_OOP.java && java Week2_OOP
 *
 * Contents
 *   Assignment 1 : Student class
 *   Assignment 2 : Vehicle -> Car, Bike (inheritance + overriding)
 *   Mini Project : Library Management System
 */

// ============================================================
// ASSIGNMENT 1: Student Class Implementation
// Concepts: class/object, constructors, constructor overloading,
//           encapsulation (private fields + getters/setters)
// ============================================================
class Student {
    private int rollNo;
    private String name;
    private String course;
    private double marks;

    // Default constructor
    public Student() {
        this(0, "Unknown", "Not Assigned", 0.0);
    }

    // Overloaded constructor (partial data)
    public Student(int rollNo, String name) {
        this(rollNo, name, "Not Assigned", 0.0);
    }

    // Overloaded constructor (full data)
    public Student(int rollNo, String name, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.course = course;
        setMarks(marks);
    }

    // Getters
    public int getRollNo()    { return rollNo; }
    public String getName()   { return name; }
    public String getCourse() { return course; }
    public double getMarks()  { return marks; }

    // Setters (with validation = encapsulation benefit)
    public void setName(String name)     { this.name = name; }
    public void setCourse(String course) { this.course = course; }
    public void setMarks(double marks) {
        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks! Must be between 0 and 100.");
            this.marks = 0;
        } else {
            this.marks = marks;
        }
    }

    public String calculateGrade() {
        if (marks >= 90) return "A+";
        if (marks >= 75) return "A";
        if (marks >= 60) return "B";
        if (marks >= 40) return "C";
        return "F";
    }

    public void displayInfo() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
        System.out.println("Course  : " + course);
        System.out.println("Marks   : " + marks);
        System.out.println("Grade   : " + calculateGrade());
        System.out.println("-----------------------------");
    }
}

// ============================================================
// ASSIGNMENT 2: Vehicle Inheritance Example
// Concepts: inheritance, method overriding, polymorphism, super
// ============================================================
class Vehicle {
    protected String brand;
    protected int speed;

    public Vehicle(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }

    public void start() {
        System.out.println(brand + " vehicle is starting...");
    }

    public void move() {
        System.out.println(brand + " is moving at " + speed + " km/h.");
    }

    public void fuelType() {
        System.out.println("Fuel type: Generic fuel");
    }

    public void displayInfo() {
        System.out.println("Brand: " + brand + " | Top Speed: " + speed + " km/h");
    }
}

class Car extends Vehicle {
    private int doors;

    public Car(String brand, int speed, int doors) {
        super(brand, speed);
        this.doors = doors;
    }

    @Override
    public void start() {
        System.out.println(brand + " car starts with a push-button ignition.");
    }

    @Override
    public void move() {
        System.out.println(brand + " car is driving on 4 wheels at " + speed + " km/h.");
    }

    @Override
    public void fuelType() {
        System.out.println("Fuel type: Petrol / Diesel / Electric");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Doors: " + doors);
    }
}

class Bike extends Vehicle {
    private boolean hasGear;

    public Bike(String brand, int speed, boolean hasGear) {
        super(brand, speed);
        this.hasGear = hasGear;
    }

    @Override
    public void start() {
        System.out.println(brand + " bike starts with a kick/self start.");
    }

    @Override
    public void move() {
        System.out.println(brand + " bike is riding on 2 wheels at " + speed + " km/h.");
    }

    @Override
    public void fuelType() {
        System.out.println("Fuel type: Petrol");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Geared: " + (hasGear ? "Yes" : "No"));
    }
}

// ============================================================
// MINI PROJECT: Library Management System
// Classes   : Book, User (abstract) -> StudentUser, FacultyUser, Library
// Interface : LibraryService
// Demonstrates: Inheritance, Abstraction, Encapsulation,
//               Polymorphism, Method Overloading
// ============================================================

/** Abstraction via interface: defines WHAT a library can do. */
interface LibraryService {
    void addBook(Book book);
    boolean borrowBook(int bookId, User user);
    boolean returnBook(int bookId, User user);
}

/** Encapsulation: all fields private, controlled access via methods. */
class Book {
    private final int id;
    private final String title;
    private final String author;
    private boolean available = true;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId()          { return id; }
    public String getTitle()    { return title; }
    public String getAuthor()   { return author; }
    public boolean isAvailable(){ return available; }

    // Only the library package logic should change status
    void markBorrowed() { available = false; }
    void markReturned() { available = true; }

    @Override
    public String toString() {
        return String.format("[%d] %-28s by %-18s %s",
                id, title, author, available ? "(Available)" : "(Issued)");
    }
}

/** Abstraction: abstract class; subclasses must define their own borrow limit. */
abstract class User {
    private final int userId;
    private final String name;
    private final List<Book> borrowedBooks = new ArrayList<>();

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public abstract int getBorrowLimit();   // abstract method
    public abstract String getRole();

    public int getUserId() { return userId; }
    public String getName() { return name; }

    // Return a copy so outside code can't modify the list directly (encapsulation)
    public List<Book> getBorrowedBooks() { return new ArrayList<>(borrowedBooks); }

    public boolean canBorrow() { return borrowedBooks.size() < getBorrowLimit(); }

    void addBorrowed(Book b)    { borrowedBooks.add(b); }
    void removeBorrowed(Book b) { borrowedBooks.remove(b); }

    public void displayBorrowed() {
        System.out.println(getRole() + " " + name + " has borrowed " + borrowedBooks.size() + " book(s):");
        if (borrowedBooks.isEmpty()) System.out.println("   (none)");
        for (Book b : borrowedBooks) System.out.println("   - " + b.getTitle());
    }
}

/** Inheritance: StudentUser IS-A User. */
class StudentUser extends User {
    public StudentUser(int id, String name) { super(id, name); }
    @Override public int getBorrowLimit() { return 2; }
    @Override public String getRole()     { return "Student"; }
}

/** Inheritance: FacultyUser IS-A User. */
class FacultyUser extends User {
    public FacultyUser(int id, String name) { super(id, name); }
    @Override public int getBorrowLimit() { return 5; }
    @Override public String getRole()     { return "Faculty"; }
}

/** Implements the interface; hides internal storage (encapsulation). */
class Library implements LibraryService {
    private final String name;
    private final List<Book> books = new ArrayList<>();

    public Library(String name) { this.name = name; }

    @Override
    public void addBook(Book book) {
        if (findBook(book.getId()) != null) {
            System.out.println("Book ID " + book.getId() + " already exists. Not added.");
            return;
        }
        books.add(book);
        System.out.println("Added: " + book.getTitle());
    }

    // Method overloading: add a book using raw details
    public void addBook(int id, String title, String author) {
        addBook(new Book(id, title, author));
    }

    @Override
    public boolean borrowBook(int bookId, User user) {
        Book book = findBook(bookId);
        if (book == null) {
            System.out.println("Borrow failed: Book ID " + bookId + " not found.");
            return false;
        }
        if (!book.isAvailable()) {
            System.out.println("Borrow failed: '" + book.getTitle() + "' is already issued.");
            return false;
        }
        if (!user.canBorrow()) {
            System.out.println("Borrow failed: " + user.getName() + " reached the limit of "
                    + user.getBorrowLimit() + " books.");
            return false;
        }
        book.markBorrowed();
        user.addBorrowed(book);
        System.out.println(user.getName() + " borrowed '" + book.getTitle() + "'.");
        return true;
    }

    @Override
    public boolean returnBook(int bookId, User user) {
        Book book = findBook(bookId);
        if (book == null || !user.getBorrowedBooks().contains(book)) {
            System.out.println("Return failed: " + user.getName() + " does not have book ID " + bookId + ".");
            return false;
        }
        book.markReturned();
        user.removeBorrowed(book);
        System.out.println(user.getName() + " returned '" + book.getTitle() + "'.");
        return true;
    }

    private Book findBook(int id) {
        for (Book b : books) if (b.getId() == id) return b;
        return null;
    }

    public void displayAllBooks() {
        System.out.println("\n=== " + name + " - Book Catalogue ===");
        for (Book b : books) System.out.println(b);
    }
}

// ============================================================
// MAIN: runs all demos
// ============================================================
public class Week2_OOP {

    public static void main(String[] args) {
        runAssignment1();
        runAssignment2();
        runMiniProject();
    }

    static void runAssignment1() {
        System.out.println("##### ASSIGNMENT 1: Student Class #####");
        Student s1 = new Student(101, "Aarav Sharma", "B.Tech CSE", 88.5);
        Student s2 = new Student(102, "Priya Verma");   // overloaded constructor
        s2.setCourse("BCA");
        s2.setMarks(67);
        Student s3 = new Student();                      // default constructor
        s3.setMarks(150);                                // triggers validation

        s1.displayInfo();
        s2.displayInfo();
        s3.displayInfo();
    }

    static void runAssignment2() {
        System.out.println("\n##### ASSIGNMENT 2: Vehicle Inheritance #####");
        Vehicle v = new Vehicle("Generic", 60);
        Vehicle car = new Car("Honda City", 180, 4);     // polymorphism
        Vehicle bike = new Bike("Royal Enfield", 120, true);

        Vehicle[] vehicles = { v, car, bike };
        for (Vehicle each : vehicles) {
            each.displayInfo();
            each.start();
            each.move();
            each.fuelType();
            System.out.println("-----------------------------");
        }
    }

    static void runMiniProject() {
        System.out.println("\n##### MINI PROJECT: Library Management System #####");
        Library lib = new Library("Central City Library");

        lib.addBook(new Book(1, "Clean Code", "Robert C. Martin"));
        lib.addBook(2, "Effective Java", "Joshua Bloch");      // overloaded
        lib.addBook(3, "Head First Java", "Kathy Sierra");
        lib.addBook(4, "Introduction to Algorithms", "Cormen");
        lib.addBook(1, "Duplicate Book", "Nobody");             // duplicate ID

        User student = new StudentUser(1, "Rahul");
        User faculty = new FacultyUser(2, "Dr. Mehta");

        lib.displayAllBooks();

        System.out.println("\n--- Borrowing ---");
        lib.borrowBook(1, student);
        lib.borrowBook(2, student);
        lib.borrowBook(3, student);   // exceeds student limit (2)
        lib.borrowBook(1, faculty);   // already issued
        lib.borrowBook(3, faculty);
        lib.borrowBook(99, faculty);  // not found

        lib.displayAllBooks();
        student.displayBorrowed();
        faculty.displayBorrowed();

        System.out.println("\n--- Returning ---");
        lib.returnBook(1, student);
        lib.returnBook(4, student);   // never borrowed
        lib.borrowBook(1, faculty);   // now possible

        lib.displayAllBooks();
        student.displayBorrowed();
        faculty.displayBorrowed();
    }
}
