# 03 -- Inheritance

## The core idea

Inheritance lets a class acquire the fields and methods of another class. The child class (subclass) builds on the parent class (superclass) -- it gets everything the parent has, and can add or change behaviour on top.

It models an **IS-A relationship**: a Dog IS an Animal. A SavingsAccount IS a BankAccount. A Manager IS an Employee.

---

## How it works

```java
class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }
}

class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String breed) {
        super(name, age);   // calls Animal's constructor -- MUST be first line
        this.breed = breed;
    }

    public void bark() {
        System.out.println(name + " says: Woof!");  // accesses protected field from parent
    }
}

Dog d = new Dog("Rex", 3, "Labrador");
d.eat();   // inherited from Animal -- works
d.sleep(); // inherited from Animal -- works
d.bark();  // Dog's own method
```

---

## super() -- the parent constructor call

When a subclass is created, the parent's constructor must run first. This is how the parent's fields get initialised.

```java
class Vehicle {
    private String make;
    private int year;

    public Vehicle(String make, int year) {
        this.make = make;
        this.year = year;
    }
}

class Car extends Vehicle {
    private int doors;

    public Car(String make, int year, int doors) {
        super(make, year);  // must be the FIRST line -- initialises Vehicle's fields
        this.doors = doors;
    }
}
```

**Rules for super():**
- Must be the first statement in the constructor body. No code before it.
- If you do not write it and the parent has a no-argument constructor, Java inserts `super()` automatically.
- If the parent has no no-argument constructor, you must call `super(...)` explicitly or the code will not compile.

---

## Method overriding

A subclass can replace a parent's method with its own version. This is called overriding.

```java
class Animal {
    public void makeSound() {
        System.out.println("Some generic sound.");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}
```

For a valid override, the method must have:
- The same name
- The same parameter list
- The same return type (or a subtype -- this is called a covariant return)
- Equal or broader visibility (cannot narrow from `public` to `private`)

---

## @Override annotation

`@Override` does not cause the override to happen -- the method signature matching is what causes it. What `@Override` does is tell the compiler: "I intend this to be an override." The compiler then checks that a matching method exists in the parent. If you made a typo in the method name, the compiler catches it instead of silently creating a new unrelated method.

```java
class Dog extends Animal {
    @Override
    public void makeSond() { }  // compile error -- no such method in Animal
                                // without @Override, this silently creates a new method
}
```

---

## super.methodName() -- calling the parent version

Sometimes you want to extend behaviour, not completely replace it:

```java
class Employee {
    public String getDetails() {
        return "Name: " + name + " | Dept: " + department;
    }
}

class Manager extends Employee {
    @Override
    public String getDetails() {
        return super.getDetails() + " | Team size: " + teamSize;
        // calls Employee's version, then adds manager-specific info
    }
}
```

---

## What is inherited, what is not

| Member | Inherited? |
|--------|-----------|
| `public` fields and methods | Yes |
| `protected` fields and methods | Yes |
| Package-private (default) members | Yes, if in same package |
| `private` fields and methods | No -- they exist in the object but are not directly accessible |
| Constructors | No -- constructors are not inherited, but accessible via `super()` |
| `static` members | Inherited but not overridden (they are hidden, not polymorphic) |

---

## Constructor chain in multi-level inheritance

```java
class A {
    public A() { System.out.println("A constructor"); }
}
class B extends A {
    public B() { System.out.println("B constructor"); }
    // Java inserts super() automatically because A has a no-arg constructor
}
class C extends B {
    public C() { System.out.println("C constructor"); }
}

new C();
// Output:
// A constructor
// B constructor
// C constructor
// Always top-down -- grandparent, then parent, then child
```

---

## Single inheritance limitation

Java allows a class to extend exactly **one** class. This prevents the diamond problem (ambiguity when two parents have the same method). You can work around this limitation using interfaces, which allow multiple implementation.

```java
class Dog extends Animal, Pet { }  // COMPILE ERROR -- not allowed

class Dog extends Animal implements Trainable, Lovable { }  // CORRECT
```

---

## Concept questions -- answer in full sentences

**Q1.** What does it mean for a class to inherit from another? What exactly does the subclass gain?

**Q2.** What is the IS-A relationship and why is it important when deciding whether to use inheritance?

**Q3.** What does `super()` do? What happens if you forget to call it and the parent class has no no-argument constructor?

**Q4.** Explain method overriding. What three things must be true for a method to be a valid override?

**Q5.** What does the `@Override` annotation actually do at compile time? Why is it strongly recommended even though it is not required?

**Q6.** A subclass cannot directly access a `private` field from its parent even though the field exists in the object. Why does Java enforce this? What should you do instead?

**Q7.** What is the difference between `super()` and `super.methodName()`? Give a scenario where you would use each.

**Q8.** Why does Java only allow single inheritance (extending one class)? What problem does this limitation prevent?

**Q9.** When you create an instance of a subclass, in what order do the constructors execute? Why does it happen in that order?

**Q10.** A subclass overrides a method but still wants to use the parent's behaviour as part of the new implementation. How do you do this and why is it useful?

**Q11.** What is the difference between inheriting a method and overriding it?

**Q12.** What is the difference between `extends` and `implements`? Can you use both at the same time?
