import ex08_combined.Command;
import ex08_combined.Robot;
import ex08_combined.ForwardCommand;
import ex08_combined.BackCommand;
import ex08_combined.ShutdownCommand;
import ex08_combined.HelpCommand;
import ex08_combined.TurnCommand;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Modifier;

@DisplayName("Exercise 8.2 — Robot Command System")
class RobotTest {

    private Robot robot;

    @BeforeEach
    void setUp() {
        robot = new Robot("R2D2");
    }

    // ── Construction ──────────────────────────────────────────────────────────

    @Test @DisplayName("Robot stores its name")
    void robotName() {
        assertEquals("R2D2", robot.getName());
    }

    @Test @DisplayName("Robot starts at position containing 0")
    void robotStartsAtOrigin() {
        assertTrue(robot.getPosition().contains("0"),
            "Initial position should contain 0 — got: " + robot.getPosition());
    }

    @Test @DisplayName("Robot starts in running state")
    void robotStartsRunning() {
        assertTrue(robot.isRunning());
    }

    // ── Command.create() factory ──────────────────────────────────────────────

    @Test @DisplayName("Command.create(\"forward 10\") returns ForwardCommand")
    void createForward() {
        assertInstanceOf(ForwardCommand.class, Command.create("forward 10"));
    }

    @Test @DisplayName("Command.create(\"back 5\") returns BackCommand")
    void createBack() {
        assertInstanceOf(BackCommand.class, Command.create("back 5"));
    }

    @Test @DisplayName("Command.create(\"shutdown\") returns ShutdownCommand")
    void createShutdown() {
        assertInstanceOf(ShutdownCommand.class, Command.create("shutdown"));
    }

    @Test @DisplayName("Command.create(\"help\") returns HelpCommand")
    void createHelp() {
        assertInstanceOf(HelpCommand.class, Command.create("help"));
    }

    @Test @DisplayName("Command.create(\"turn left\") returns TurnCommand")
    void createTurnLeft() {
        assertInstanceOf(TurnCommand.class, Command.create("turn left"));
    }

    @Test @DisplayName("Command.create(\"turn right\") returns TurnCommand")
    void createTurnRight() {
        assertInstanceOf(TurnCommand.class, Command.create("turn right"));
    }

    // ── All commands are Command subclasses ───────────────────────────────────

    @Test @DisplayName("All command types are instances of Command")
    void allCommandsAreCommands() {
        assertInstanceOf(Command.class, Command.create("forward 5"));
        assertInstanceOf(Command.class, Command.create("back 5"));
        assertInstanceOf(Command.class, Command.create("shutdown"));
        assertInstanceOf(Command.class, Command.create("help"));
        assertInstanceOf(Command.class, Command.create("turn right"));
    }

    // ── Movement ──────────────────────────────────────────────────────────────

    @Test @DisplayName("ForwardCommand moves robot (position reflects movement)")
    void forwardMovesRobot() {
        robot.handleCommand(Command.create("forward 10"));
        assertTrue(robot.getPosition().contains("10"),
            "After forward 10, position should include 10 — got: " + robot.getPosition());
    }

    @Test @DisplayName("BackCommand moves robot backward")
    void backMovesRobot() {
        robot.handleCommand(Command.create("forward 30"));
        robot.handleCommand(Command.create("back 10"));
        assertTrue(robot.getPosition().contains("20"),
            "After forward 30, back 10, position should include 20 — got: " + robot.getPosition());
    }

    // ── Bounds ────────────────────────────────────────────────────────────────

    @Test @DisplayName("Robot cannot exceed +200 boundary")
    void forwardBound() {
        robot.handleCommand(Command.create("forward 300"));
        assertFalse(robot.getPosition().contains("300"),
            "Robot should not reach 300 — boundary is +200");
    }

    @Test @DisplayName("Robot cannot exceed -200 boundary")
    void backwardBound() {
        robot.handleCommand(Command.create("back 300"));
        assertFalse(robot.getPosition().contains("-300"),
            "Robot should not reach -300 — boundary is -200");
    }

    // ── Shutdown ──────────────────────────────────────────────────────────────

    @Test @DisplayName("ShutdownCommand stops the robot")
    void shutdownStopsRobot() {
        robot.handleCommand(Command.create("shutdown"));
        assertFalse(robot.isRunning());
    }

    // ── Polymorphism contract ─────────────────────────────────────────────────

    @Test @DisplayName("handleCommand() parameter type is Command (base type)")
    void handleCommandTakesBaseType() throws NoSuchMethodException {
        assertNotNull(Robot.class.getMethod("handleCommand", Command.class),
            "handleCommand must accept Command, not a specific subclass");
    }

    @Test @DisplayName("Command is abstract")
    void commandIsAbstract() {
        assertTrue(Modifier.isAbstract(Command.class.getModifiers()),
            "Command must be declared abstract");
    }
}
