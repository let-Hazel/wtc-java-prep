# 11 -- Answers

Answers to `10_scenario_questions.md`. For `09_design_thinking.md` the answers are guidance notes -- design questions have no single right answer, only better or worse reasoning.

---

## Scenario Answers

**Scenario A:**
Output: `Area: 25.0`

`describe()` is in `Shape` and calls `getArea()`. Even though `Shape` does not implement `getArea()`, at runtime the actual object is a `Triangle`. Java uses dynamic dispatch -- when `describe()` calls `getArea()`, it routes to `Triangle`'s implementation. This is polymorphism working inside the class hierarchy: a non-abstract method in a parent class can call an abstract method that only subclasses implement.

---

**Scenario B:**
Does not compile. `Dog` extends `Animal` but does not implement `makeSound()`. Since `makeSound()` is abstract, any concrete (non-abstract) class extending `Animal` must implement it. Fix: either add `public void makeSound() { System.out.println("Woof"); }` to `Dog`, or declare `Dog` as `abstract` too (which just pushes the obligation further down the hierarchy).

---

**Scenario C:**
Output in order:
```
A
B
C
```
When you call `new C()`, Java automatically inserts `super()` calls up the chain. `C`'s constructor calls `B`'s (via implicit `super()`), which calls `A`'s. `A`'s constructor runs first, then `B`'s, then `C`'s. This is always top-down: the grandparent constructs first, then the parent, then the child. The purpose is to ensure each level of the hierarchy initialises its own fields before the next level builds on top.

---

**Scenario D:**
Does not fully compile. Line `f.swim()` is a compile error. `f` is declared as type `Flyable`. The compiler only knows about methods in `Flyable`. `swim()` is not in `Flyable` -- it is in `Swimmable`. Even though the actual object is a `Duck` which has `swim()`, the compiler uses the reference type to check what is accessible. Fix: cast to `Duck` first: `((Duck) f).swim()`, or store it as `Duck` or `Swimmable`.

`f.fly()` would compile and print "Duck flies."

---

**Scenario E:**
`a.speak()` -- compiles and prints "Woof". Even though `a` is declared as `Animal`, the actual object is a `Dog`. Dynamic dispatch calls `Dog`'s `speak()`.

`a.fetch()` -- compile error. `fetch()` is not defined in `Animal`. The compiler checks the reference type (`Animal`) to see what methods are available. `fetch()` is not there. To call it you would need to downcast: `((Dog) a).fetch()`.

---

**Scenario F:**
Does not compile. `super(make)` must be the **first statement** in the constructor. Here `this.doors = doors` appears before it. Java enforces this because the parent must be fully initialised before the child sets its own fields. Fix: swap the lines so `super(make)` comes first.

---

**Scenario G:**
Output:
```
Rex says Woof!
Luna says Meow!
Buddy says Woof!
```
This demonstrates runtime polymorphism. All three objects are stored in an `Animal[]`. The loop calls `makeSound()` on each without knowing the specific type. Java uses dynamic dispatch to call `Dog`'s or `Cat`'s version at runtime. Also demonstrates that a `protected` field `name` from `Animal` is accessible directly in subclass methods.

---

**Scenario H:**
Line 1: `Animal a = new Dog("Rex")` -- upcasting, automatic, always safe. The object is a `Dog`, the reference type is `Animal`.

Line 2: `Dog d = (Dog) a` -- downcasting, explicit cast. Safe here because `a` genuinely refers to a `Dog` object. `d` can now call Dog-specific methods.

Line 3: `Cat c = (Cat) a` -- compiles (the compiler allows casts between class types in the same hierarchy). But throws `ClassCastException` at runtime because the object is actually a `Dog`, not a `Cat`. You cannot cast a `Dog` to a `Cat`. Fix: check with `instanceof` before casting.

---

**Scenario I:**
`p1 == p2` prints `false`. `==` compares references -- whether both variables point to the exact same object in memory. `p1` and `p2` are two separate objects, so their references are different.

`p1.equals(p2)` also prints `false`. The `Person` class does not override `equals()`, so it inherits the default from `Object`, which also compares references (same behaviour as `==`). To make two `Person` objects with the same name equal, you would need to override `equals()` to compare the `name` field.

---

**Scenario J:**
`Counter.getCount()` prints `3`. `count` is static -- shared across all instances. Each constructor call increments it. Three objects were created, so `count` is 3.

`x.getId()` prints `1`. `x` was the first Counter created. At that point `count` became 1, so `id` was set to 1.

`z.getId()` prints `3`. `z` was the third Counter created. `count` was 3 at that point, so `id` was set to 3.

---

**Scenario K:**
`r.preview()` prints "Report preview here." -- `Report` overrides `preview()`.

`i.preview()` prints "Preview not available." -- `Invoice` does not override `preview()`, so it inherits the `default` implementation from the interface.

This demonstrates default methods: they provide a fallback implementation that is used when the implementing class does not override them.

---

**Scenario L:**
Compiles and runs. Output: "Woof". The override happens because the method signature in `Dog` matches `Animal`'s `makeSound()`. The `@Override` annotation is not required for the override to happen -- it is purely a safety tool. Without it, the code still works. But if you had a typo (e.g. `makeSond()`), without `@Override` it would silently create a new unrelated method and `makeSound()` would fall back to the parent's "Generic sound." With `@Override`, the compiler would catch the typo immediately.

---

## Design Thinking Guidance Notes

**Q1 (Zoo):** Good answer: `Animal` as an abstract class with shared fields. `Trainable`, `Exhibitable`, `Feedable` as interfaces since these are capabilities that could apply to unrelated animals. `Lion implements Exhibitable`, `Dolphin implements Trainable, Exhibitable`. Composition for `ZooKeeper` holding a list of `Animal` objects.

**Q3 (Square extends Rectangle):** The Liskov Substitution Principle says a subclass must behave correctly wherever the parent is used. If `Rectangle` has `setWidth(w)` that changes only width, then `Square.setWidth(w)` must also change height to maintain the square invariant. This breaks the parent's contract. The IS-A test passes geometrically but fails behaviourally.

**Q5 (Logger):** Composition is correct. A `Logger` is not a `FileWriter` -- it just uses one. If you extend, `Logger` inherits all `FileWriter` methods and users can call them directly, bypassing logging logic. With composition, `Logger` holds a private `FileWriter` and only exposes `log()`.

**Q6 (Open/Closed):** Violation: a method with a long `if/else` or `switch` that checks object types and does different things -- adding a new type means editing this method. Adherence: polymorphism through an abstract base or interface -- adding a new type means adding a new class, never editing existing ones.

**Q9 (Notification):** Abstract class `Notification` or interface `Notifiable` with a `send()` method. Each type (`EmailNotification`, `SMSNotification`) is its own class. A `NotificationService` works with `Notification[]` or `List<Notifiable>` without knowing specific types. Adding a new type = new class, nothing else changes.

