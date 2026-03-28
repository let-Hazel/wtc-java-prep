import ex06_interfaces.Printable;
import ex06_interfaces.Saveable;
import ex06_interfaces.Report;
import ex06_interfaces.Image;
import ex06_interfaces.Config;
import ex06_interfaces.Switchable;
import ex06_interfaces.Dimmable;
import ex06_interfaces.Schedulable;
import ex06_interfaces.Controllable;
import ex06_interfaces.SmartLight;
import ex06_interfaces.SmartThermostat;
import ex06_interfaces.SmartPlug;
import ex06_interfaces.SmartLamp;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 6 — Interfaces")
class InterfaceTest {

    // ── 6.1: Correct interface implementation ─────────────────────────────────

    @Test @DisplayName("Report implements Printable")
    void reportIsPrintable() {
        assertInstanceOf(Printable.class, new Report("Title", "Content", "Author"));
    }

    @Test @DisplayName("Report implements Saveable")
    void reportIsSaveable() {
        assertInstanceOf(Saveable.class, new Report("Title", "Content", "Author"));
    }

    @Test @DisplayName("Image implements Printable")
    void imageIsPrintable() {
        assertInstanceOf(Printable.class, new Image("photo.png", 1920, 1080));
    }

    @Test @DisplayName("Image does NOT implement Saveable")
    void imageNotSaveable() {
        assertFalse(new Image("photo.png", 1920, 1080) instanceof Saveable,
            "Image should only implement Printable");
    }

    @Test @DisplayName("Config implements Saveable")
    void configIsSaveable() {
        assertInstanceOf(Saveable.class, new Config());
    }

    @Test @DisplayName("Config does NOT implement Printable")
    void configNotPrintable() {
        assertFalse(new Config() instanceof Printable,
            "Config should only implement Saveable");
    }

    @Test @DisplayName("Printable[] holds Report and Image, print() doesn't throw")
    void printableArray() {
        Printable[] arr = { new Report("T", "C", "A"), new Image("f.png", 800, 600) };
        for (Printable p : arr) {
            assertDoesNotThrow(p::print);
        }
    }

    // ── 6.2: Smart devices ────────────────────────────────────────────────────

    @Test @DisplayName("SmartLight implements Switchable and Dimmable")
    void smartLightInterfaces() {
        SmartLight l = new SmartLight();
        assertInstanceOf(Switchable.class, l);
        assertInstanceOf(Dimmable.class,   l);
    }

    @Test @DisplayName("SmartThermostat implements Switchable and Controllable")
    void smartThermostatInterfaces() {
        SmartThermostat t = new SmartThermostat();
        assertInstanceOf(Switchable.class,   t);
        assertInstanceOf(Controllable.class, t);
    }

    @Test @DisplayName("SmartPlug implements Switchable and Schedulable")
    void smartPlugInterfaces() {
        SmartPlug p = new SmartPlug();
        assertInstanceOf(Switchable.class,  p);
        assertInstanceOf(Schedulable.class, p);
    }

    @Test @DisplayName("SmartLamp implements Switchable, Dimmable, and Schedulable")
    void smartLampInterfaces() {
        SmartLamp l = new SmartLamp();
        assertInstanceOf(Switchable.class,  l);
        assertInstanceOf(Dimmable.class,    l);
        assertInstanceOf(Schedulable.class, l);
    }

    @Test @DisplayName("turnOn() makes isOn() return true")
    void turnOn() {
        SmartLight l = new SmartLight();
        l.turnOn();
        assertTrue(l.isOn());
    }

    @Test @DisplayName("turnOff() makes isOn() return false")
    void turnOff() {
        SmartLight l = new SmartLight();
        l.turnOn();
        l.turnOff();
        assertFalse(l.isOn());
    }

    @Test @DisplayName("setDimLevel() reflected by getDimLevel()")
    void dimLevel() {
        SmartLight l = new SmartLight();
        l.setDimLevel(75);
        assertEquals(75, l.getDimLevel());
    }

    @Test @DisplayName("SmartLamp can be referenced as all three interface types")
    void smartLampMultipleReferences() {
        SmartLamp lamp = new SmartLamp();
        Switchable  s  = lamp;
        Dimmable    d  = lamp;
        Schedulable sc = lamp;

        s.turnOn();
        d.setDimLevel(50);
        sc.setSchedule("22:00");

        assertTrue(lamp.isOn());
        assertEquals(50, lamp.getDimLevel());
    }
}
