import ex04_polymorphism.Shape;
import ex04_polymorphism.Circle;
import ex04_polymorphism.Rectangle;
import ex04_polymorphism.Triangle;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 4.1 — Shape Calculator")
class ShapeTest {

    private static final double D = 0.01;

    // ── Circle ────────────────────────────────────────────────────────────────

    @Test @DisplayName("Circle area = π * r²")
    void circleArea() {
        assertEquals(Math.PI * 25, new Circle("red", 5.0).calculateArea(), D);
    }

    @Test @DisplayName("Circle perimeter = 2 * π * r")
    void circlePerimeter() {
        assertEquals(2 * Math.PI * 5, new Circle("red", 5.0).calculatePerimeter(), D);
    }

    // ── Rectangle ─────────────────────────────────────────────────────────────

    @Test @DisplayName("Rectangle area = width * height")
    void rectangleArea() {
        assertEquals(24.0, new Rectangle("blue", 4.0, 6.0).calculateArea(), D);
    }

    @Test @DisplayName("Rectangle perimeter = 2 * (width + height)")
    void rectanglePerimeter() {
        assertEquals(20.0, new Rectangle("blue", 4.0, 6.0).calculatePerimeter(), D);
    }

    // ── Triangle ──────────────────────────────────────────────────────────────

    @Test @DisplayName("Triangle area = 0.5 * base * height")
    void triangleArea() {
        // Triangle(color, base, height, sideA, sideB)
        assertEquals(12.0, new Triangle("green", 6.0, 4.0, 5.0, 5.0).calculateArea(), D);
    }

    @Test @DisplayName("Triangle perimeter = base + sideA + sideB")
    void trianglePerimeter() {
        assertEquals(16.0, new Triangle("green", 6.0, 4.0, 5.0, 5.0).calculatePerimeter(), D);
    }

    // ── Inheritance ───────────────────────────────────────────────────────────

    @Test @DisplayName("All shapes are instances of Shape")
    void allAreShapes() {
        assertInstanceOf(Shape.class, new Circle("red", 3.0));
        assertInstanceOf(Shape.class, new Rectangle("blue", 2.0, 4.0));
        assertInstanceOf(Shape.class, new Triangle("green", 3.0, 4.0, 4.0, 5.0));
    }

    // ── Polymorphism ──────────────────────────────────────────────────────────

    @Test @DisplayName("calculateArea() via Shape[] returns correct subclass result")
    void polymorphicArea() {
        Shape[] shapes = {
            new Circle("red",     5.0),
            new Rectangle("blue", 4.0, 6.0),
            new Triangle("green", 6.0, 4.0, 5.0, 5.0)
        };
        assertEquals(Math.PI * 25, shapes[0].calculateArea(), D);
        assertEquals(24.0,         shapes[1].calculateArea(), D);
        assertEquals(12.0,         shapes[2].calculateArea(), D);
    }

    @Test @DisplayName("All shapes return a positive area")
    void allAreasPositive() {
        Shape[] shapes = {
            new Circle("r", 3),
            new Rectangle("b", 2, 5),
            new Triangle("g", 4, 3, 3.5, 4.5)
        };
        for (Shape s : shapes) {
            assertTrue(s.calculateArea() > 0,
                s.getClass().getSimpleName() + " area must be positive");
        }
    }
}
