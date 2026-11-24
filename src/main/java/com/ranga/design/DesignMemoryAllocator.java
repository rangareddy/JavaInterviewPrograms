package com.ranga.design;

/**
 * Design an allocator for a memory array of {@code n} units, all initially free. {@code allocate(size, mID)} finds the leftmost block of {@code size} consecutive free units, marks them with {@code mID} and returns the index of its first unit, or {@code -1} if no such block exists. {@code freeMemory(mID)} frees every unit with that {@code mID} and returns how many were freed.
 *
 * <p><b>Algorithm Strategy (Array Scan for Runs of Free Cells):</b>
 * <ol>
 *   <li>Store the memory as an {@code int[]} where 0 means free and any other value is an allocation id.</li>
 *   <li>{@code allocate} scans left to right counting the current run of free cells; when the run reaches {@code size}, fill that range and return its start.</li>
 *   <li>If the scan ends without a long enough run, return -1.</li>
 *   <li>{@code freeMemory} sweeps the array and zeroes every cell equal to {@code mID}, counting them.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>On 10 units: {@code allocate(1, 1)} returns 0, {@code allocate(1, 2)} returns 1, {@code freeMemory(1)} returns 1, {@code allocate(1, 3)} returns 0 again.</li>
 *   <li>{@code allocate(10, 4)} fails because no run of 10 free units exists.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n) per operation} — One scan of the memory.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The memory array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignMemoryAllocator {

    public static void main(String[] args) {
        DesignMemoryAllocator allocator = new DesignMemoryAllocator(10);
        System.out.println("Input  : allocate(1, 1)");
        System.out.println("Output : " + allocator.allocate(1, 1));
        System.out.println();
        System.out.println("Input  : allocate(1, 2), freeMemory(1), allocate(3, 3), allocate(1, 4)");
        System.out.println("Output : " + allocator.allocate(1, 2) + ", " + allocator.freeMemory(1) + ", " + allocator.allocate(3, 3) + ", " + allocator.allocate(1, 4));
        System.out.println();
        System.out.println("Input  : allocate(10, 5), freeMemory(7)");
        System.out.println("Output : " + allocator.allocate(10, 5) + ", " + allocator.freeMemory(7));
    }

    private final int[] memory;

    public DesignMemoryAllocator(int n) {
        memory = new int[n];
    }

    public int allocate(int size, int mID) {
        int run = 0;
        for (int i = 0; i < memory.length; i++) {
            run = memory[i] == 0 ? run + 1 : 0;
            if (run == size) {
                int start = i - size + 1;
                for (int j = start; j <= i; j++) {
                    memory[j] = mID;
                }
                return start;
            }
        }
        return -1;
    }

    public int freeMemory(int mID) {
        int freed = 0;
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] == mID) {
                memory[i] = 0;
                freed++;
            }
        }
        return freed;
    }
}
