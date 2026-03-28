import ex03_inheritance.Employee;
import ex03_inheritance.FullTimeEmployee;
import ex03_inheritance.PartTimeEmployee;
import ex03_inheritance.Contractor;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 3.2 — Employee Payroll System")
class EmployeeTest {

    // ── Pay calculation ───────────────────────────────────────────────────────

    @Test @DisplayName("FullTimeEmployee.calculatePay() returns monthly salary")
    void fullTimePay() {
        FullTimeEmployee e = new FullTimeEmployee("Alice", "E001", "Engineering", 50_000.0);
        assertEquals(50_000.0, e.calculatePay(), 0.001);
    }

    @Test @DisplayName("PartTimeEmployee.calculatePay() returns hoursWorked * hourlyRate")
    void partTimePay() {
        PartTimeEmployee e = new PartTimeEmployee("Bob", "E002", "Design", 40.0, 150.0);
        assertEquals(6_000.0, e.calculatePay(), 0.001);
    }

    @Test @DisplayName("Contractor.calculatePay() returns daysWorked * dailyRate")
    void contractorPay() {
        Contractor c = new Contractor("Carol", "E003", "IT", 2_000.0, 15, "TechCorp");
        assertEquals(30_000.0, c.calculatePay(), 0.001);
    }

    // ── Inheritance ───────────────────────────────────────────────────────────

    @Test @DisplayName("All three types are instances of Employee")
    void allAreEmployees() {
        assertInstanceOf(Employee.class, new FullTimeEmployee("A", "1", "HR", 40_000));
        assertInstanceOf(Employee.class, new PartTimeEmployee("B", "2", "HR", 20, 100));
        assertInstanceOf(Employee.class, new Contractor("C", "3", "HR", 1_500, 10, "Acme"));
    }

    // ── Polymorphism ──────────────────────────────────────────────────────────

    @Test @DisplayName("calculatePay() called through Employee[] calls correct subclass version")
    void polymorphicPayThroughArray() {
        Employee[] staff = {
            new FullTimeEmployee("Alice", "E001", "Eng", 50_000.0),
            new PartTimeEmployee("Bob",   "E002", "Des", 40.0, 150.0),
            new Contractor("Carol",       "E003", "IT",  2_000.0, 15, "Corp")
        };
        assertEquals(50_000.0, staff[0].calculatePay(), 0.001);
        assertEquals(6_000.0,  staff[1].calculatePay(), 0.001);
        assertEquals(30_000.0, staff[2].calculatePay(), 0.001);
    }

    @Test @DisplayName("calculatePay() via base Employee reference still calls subclass version")
    void polymorphicViaBaseRef() {
        Employee e = new FullTimeEmployee("Alice", "E001", "Eng", 50_000.0);
        assertEquals(50_000.0, e.calculatePay(), 0.001);
    }
}
