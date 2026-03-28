import ex07_composition.CPU;
import ex07_composition.RAM;
import ex07_composition.Storage;
import ex07_composition.GPU;
import ex07_composition.Computer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 7.1 — Computer Builder")
class ComputerTest {

    private CPU     cpu;
    private RAM     bigRam, smallRam;
    private Storage ssd, hdd;
    private GPU     gpu;

    @BeforeEach
    void setUp() {
        cpu      = new CPU("Intel", 8, 3.8, 5_000.0);
        bigRam   = new RAM(32, "DDR5", 5200, 3_000.0);
        smallRam = new RAM(8,  "DDR4", 3200, 1_000.0);
        ssd      = new Storage("SSD", 1000, 3500, 2_000.0);
        hdd      = new Storage("HDD", 2000,  150,   800.0);
        gpu      = new GPU("Nvidia", 8, 8_000.0);
    }

    // ── isGamingReady ─────────────────────────────────────────────────────────

    @Test @DisplayName("isGamingReady() true: GPU + 32GB RAM + SSD")
    void gamingReady() {
        assertTrue(new Computer(cpu, bigRam, ssd, gpu).isGamingReady());
    }

    @Test @DisplayName("isGamingReady() false: no GPU")
    void notReadyNoGpu() {
        assertFalse(new Computer(cpu, bigRam, ssd, null).isGamingReady());
    }

    @Test @DisplayName("isGamingReady() false: RAM < 16GB")
    void notReadySmallRam() {
        assertFalse(new Computer(cpu, smallRam, ssd, gpu).isGamingReady());
    }

    @Test @DisplayName("isGamingReady() false: HDD instead of SSD")
    void notReadyHdd() {
        assertFalse(new Computer(cpu, bigRam, hdd, gpu).isGamingReady());
    }

    // ── getTotalCost ──────────────────────────────────────────────────────────

    @Test @DisplayName("getTotalCost() sums all four components")
    void totalCostWithGpu() {
        assertEquals(18_000.0, new Computer(cpu, bigRam, ssd, gpu).getTotalCost(), 0.001);
    }

    @Test @DisplayName("getTotalCost() works when GPU is null")
    void totalCostNoGpu() {
        assertEquals(6_800.0, new Computer(cpu, smallRam, hdd, null).getTotalCost(), 0.001);
    }

    // ── Null safety ───────────────────────────────────────────────────────────

    @Test @DisplayName("displaySpecs() does not throw when GPU is null")
    void displaySpecsNullSafe() {
        assertDoesNotThrow(() -> new Computer(cpu, smallRam, hdd, null).displaySpecs());
    }
}
