# 02 -- Abstraction

## The core idea

Abstraction means hiding complexity behind a simple interface. You expose what something **does** while hiding **how** it does it.

Real world: you drive a car using a steering wheel, pedals, and gear stick. You do not need to understand the combustion engine, the fuel injection system, or the transmission to drive. Those details are abstracted away. You interact with the car through a simplified interface.

In code: you call `names.sort(null)` without needing to know whether it uses Timsort internally, how it handles nulls, or what the algorithmic complexity is. The complexity is hidden.

---

## Two levels of abstraction

**1. Using classes and methods (always present)**

Any time you call a method without caring about its implementation, you are using abstraction. A well-designed class exposes a clean public interface and hides its implementation details.

**2. Abstract classes and interfaces (structural abstraction)**

These go further -- they define the *shape* of a thing without providing the full implementation, forcing subclasses to fill in the details.

---

## Abstract classes

An abstract class uses the `abstract` keyword. It **cannot be instantiated** -- you can never write `new Shape()`. It exists purely to be extended.

```java
abstract class Shape {
    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    // Abstract method -- defines WHAT to do, not HOW
    // No body, no curly braces, ends with semicolon
    public abstract double calculateArea();
    public abstract double calculatePerimeter();

    // Non-abstract method -- shared implementation, inherited for free
    public void displayInfo() {
        System.out.println("Color: " + color);
        System.out.println("Area: " + calculateArea());       // calls overridden version
        System.out.println("Perimeter: " + calculatePerimeter()); // calls overridden version
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;  // Circle's specific formula
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}
```

Key observations:
- `displayInfo()` is in the base class and calls `calculateArea()` -- but at runtime, it calls `Circle`'s version, not `Shape`'s. This is abstraction and polymorphism working together.
- `Shape` has a constructor even though it cannot be instantiated. It is called via `super(color)`.
- If a subclass does not implement all abstract methods, it must also be declared `abstract`.

---

## Abstract method rules

```java
// Correct -- abstract method in abstract class
public abstract double calculateArea();

// Wrong -- abstract method in non-abstract class (compile error)
public abstract double calculateArea();  // class is not abstract

// Wrong -- abstract method with a body (compile error)
public abstract double calculateArea() {
    return 0;  // cannot have a body
}

// Wrong -- trying to instantiate an abstract class (compile error)
Shape s = new Shape("red");
```

---

## Abstraction vs Encapsulation -- the distinction

These are often confused. They are related but solve different problems.

| | Encapsulation | Abstraction |
|--|---------------|-------------|
| **Hides** | Internal data (fields) | Internal implementation (how things work) |
| **Mechanism** | `private` fields, getters/setters | Abstract classes, interfaces, well-named methods |
| **Goal** | Protect state | Reduce complexity |
| **Example** | `balance` is private | `calculateArea()` is abstract -- caller doesn't know the formula |

You use encapsulation to achieve abstraction -- they work together. But they are not the same thing.

---

## Why abstract classes over regular classes?

If you could just write a base `Shape` class with `return 0` for `calculateArea()`, why bother with `abstract`?

Because `abstract` gives you a **compiler guarantee**. If you create a subclass and forget to implement `calculateArea()`, the code will not compile. With a regular base class returning 0, you would get silent wrong behaviour. `abstract` turns a runtime bug into a compile-time error.

---

## Concept questions -- answer in full sentences

**Q1.** What is the difference between abstraction and encapsulation? Many students confuse these. Explain each one clearly and then explain how they relate to each other.

**Q2.** What does it mean for a class to be abstract? What two things does `abstract` prevent or require?

**Q3.** An abstract class can have both abstract and non-abstract methods. Why is this useful? Give an example scenario where you would want both in the same class.

**Q4.** Why can you not instantiate an abstract class directly? What would go wrong if Java allowed it?

**Q5.** Can an abstract class have a constructor? If so, what is its purpose given that the class cannot be instantiated?

**Q6.** What is an abstract method? Write its declaration syntax from memory and explain each part.

**Q7.** If a subclass extends an abstract class but does not implement all abstract methods, what happens? What must you do instead?

**Q8.** Explain abstraction using a real-world analogy that is not a car and not a vending machine. Connect it back to code.

**Q9.** A developer says: "Abstract classes are pointless. I can just put a method with an empty body or a default return value in a regular class." What is wrong with this argument?

**Q10.** How does calling a non-abstract method (like `displayInfo()`) that internally calls an abstract method (like `calculateArea()`) demonstrate both abstraction and polymorphism at the same time?
