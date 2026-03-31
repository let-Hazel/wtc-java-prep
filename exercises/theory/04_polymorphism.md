# 04 -- Polymorphism

## The core idea

Polymorphism means "many forms." The same method call produces different behaviour depending on the actual type of the object at runtime -- not the type of the variable.

It is the feature that lets you write code that works with a general type (like `Animal`) and automatically does the right thing for each specific type (Dog, Cat, Parrot) without you having to check types manually.

---

## The classic example

```java
class Animal {
    public void makeSound() {
        System.out.println("...");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() { System.out.println("Woof!"); }
}

class Cat extends Animal {
    @Override
    public void makeSound() { System.out.println("Meow!"); }
}

class Parrot extends Animal {
    @Override
    public void makeSound() { System.out.println("Squawk!"); }
}

// Polymorphism in action:
Animal[] animals = { new Dog(), new Cat(), new Parrot() };

for (Animal a : animals) {
    a.makeSound();  // Java calls the correct version for each object
}

// Output:
// Woof!
// Meow!
// Squawk!
```

The loop does not care what kind of animal it is. It calls `makeSound()` and each object knows how to respond. If you add a new animal type later, you add the class -- the loop does not change.

---

## Variable type vs object type

This is the most important distinction in polymorphism:

```java
Animal a = new Dog();
```

- **Variable type (reference type):** `Animal` -- determines what methods you can call through this variable
- **Object type (actual type):** `Dog` -- determines which version of the method runs

```java
Animal a = new Dog();
a.makeSound();  // calls Dog's makeSound() -- not Animal's
a.bark();       // COMPILE ERROR -- bark() is not in Animal
                // the variable type (Animal) limits what you can call
```

The compiler checks the variable type to decide if the call is legal. The JVM checks the object type at runtime to decide which version to run.

---

## Dynamic dispatch

Dynamic dispatch is the mechanism behind runtime polymorphism. When you call an overridden method:

1. The compiler checks that the method exists on the variable type -- if not, compile error
2. At runtime, the JVM looks at the actual object type
3. The JVM dispatches (routes) the call to that type's implementation

This decision happens at runtime, not compile time. That is why it is called **dynamic** dispatch.

---

## Upcasting -- safe, automatic

Storing a subclass object in a parent-type variable. Always safe. Java does it automatically.

```java
Dog d = new Dog();
Animal a = d;          // upcasting -- automatic, no cast syntax needed
Animal b = new Cat();  // also upcasting -- automatic

// Storing in a collection:
List<Animal> zoo = new ArrayList<>();
zoo.add(new Dog());   // upcasting -- Dog IS-A Animal
zoo.add(new Cat());
zoo.add(new Parrot());
```

You lose access to subclass-specific methods through the parent reference, but the object itself is unchanged.

---

## Downcasting -- manual, potentially dangerous

Converting a parent-type reference back to a subclass type. Requires explicit cast syntax. Can throw `ClassCastException` at runtime if the object is not actually that type.

```java
Animal a = new Dog();

Dog d = (Dog) a;    // downcasting -- explicit, works because a IS a Dog
d.bark();           // now you can call Dog-specific methods

Cat c = (Cat) a;    // compiles -- but throws ClassCastException at runtime
                    // because a is actually a Dog, not a Cat
```

---

## instanceof -- safe type checking before downcasting

Always check before you downcast:

```java
Animal a = new Dog();

if (a instanceof Dog) {
    Dog d = (Dog) a;
    d.bark();           // safe
}

if (a instanceof Cat) {
    Cat c = (Cat) a;    // never executes -- Dog is not a Cat
    c.purr();
}
```

`instanceof` returns `true` if the object is an instance of that class **or any subclass of it**.

---

## Two types of polymorphism

**Runtime polymorphism (dynamic / most important):**
- Achieved through method overriding
- Decision of which method to call happens at runtime
- This is what people mean when they say "polymorphism" in OOP

**Compile-time polymorphism (static):**
- Achieved through method overloading
- Same method name, different parameter lists
- Decision of which version to call happens at compile time based on argument types

```java
// Overloading -- compile-time polymorphism
class Printer {
    public void print(String text) { ... }
    public void print(int number) { ... }
    public void print(String text, int copies) { ... }
}
```

---

## Why polymorphism matters for design

Without polymorphism, you would write this:

```java
// Bad -- violates open/closed principle, breaks when you add new types
for (Animal a : animals) {
    if (a instanceof Dog) {
        ((Dog) a).makeSound();
    } else if (a instanceof Cat) {
        ((Cat) a).makeSound();
    } else if (a instanceof Parrot) {
        ((Parrot) a).makeSound();
    }
    // add a new animal type? edit this switch. fragile.
}
```

With polymorphism, you write:

```java
// Good -- works for any Animal subtype, past or future
for (Animal a : animals) {
    a.makeSound();
}
```

---

## Concept questions -- answer in full sentences

**Q1.** What is polymorphism? Explain it without using the word "many forms."

**Q2.** What is the difference between the variable type and the object type in a statement like `Animal a = new Dog()`? Which one does the compiler care about? Which one does the JVM care about at runtime?

**Q3.** What is dynamic dispatch? Why is it called "dynamic"?

**Q4.** What is the difference between upcasting and downcasting? Which is safe and why? Which can fail and why?

**Q5.** What is `instanceof` and when should you use it? What does it return if the object is a subclass of the checked type?

**Q6.** Explain the difference between runtime polymorphism and compile-time polymorphism. Give a Java example of each.

**Q7.** A developer writes a loop that uses `instanceof` to check the type of each object before calling a type-specific method. Why is this considered bad design? What should they do instead?

**Q8.** You upcast a `Dog` to an `Animal` reference. Then you try to call `bark()` through the `Animal` reference. What happens and why?

**Q9.** Why does polymorphism make code more extensible? Explain using the scenario of adding a new animal type to an existing system.

**Q10.** Can polymorphism work with interfaces? Explain with a short example in your head (no need to write full code -- just describe what would happen).

**Q11.** What would happen in a language that does not support polymorphism if you needed different behaviour for different object types?

**Q12.** Why does Java call the overridden version of a method even when the variable type is the parent class?
