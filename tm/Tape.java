package tm;

import java.util.HashMap;
import java.util.Map;

/**
 * Bi-infinite tape for a Turing machine using integer symbols.
 * Unvisited cells behave as blank (0). Tracks the span of cells the head has
 * visited so the final segment can be printed.
 *
 * @author Maria Gomez and Amy Lee
 */
public final class Tape {

    /** Maps tape coordinate to symbol; missing entries are blank (0). */
    private final Map<Integer, Integer> cells;

    /** Smallest coordinate visited by the head (inclusive). */
    private int minVisited;

    /** Largest coordinate visited by the head (inclusive). */
    private int maxVisited;

    /**
     * Creates an empty tape with the head at coordinate 0.
     */
    public Tape() {
        this.cells = new HashMap<>();
        this.minVisited = 0;
        this.maxVisited = 0;
    }

    /**
     * Writes the input string onto the tape starting at coordinate 0 and leaves the head at 0.
     * Each character must be a digit {1 -9 } matching alphabet symbols.
     *
     * @param input the input line (possibly empty for ε)
     */
    public void writeInitialInput(String input) {
        if (input == null || input.isEmpty()) {
            cells.put(0, 0);
            minVisited = 0;
            maxVisited = 0;
            return;
        }
        for (int i = 0; i < input.length(); i++) {
            int sym = input.charAt(i) - '0';
            cells.put(i, sym);
        }
        minVisited = 0;
        maxVisited = input.length() - 1;
    }

    /**
     * Reads the symbol at the given tape coordinate.
     *
     * @param position absolute tape index
     * @return tape symbol at {@code position}, or 0 if blank
     */
    public int read(int position) {
        Integer v = cells.get(position);
        return v == null ? 0 : v;
    }

    /**
     * Writes a symbol at the given position.
     *
     * @param position absolute tape index
     * @param symbol symbol to write
     */
    public void write(int position, int symbol) {
        cells.put(position, symbol);
    }

    /**
     * Updates visited bounds when the head moves to {@code headPosition}.
     *
     * @param headPosition current head coordinate
     */
    public void noteHeadAt(int headPosition) {
        if (headPosition < minVisited) {
            minVisited = headPosition;
        }
        if (headPosition > maxVisited) {
            maxVisited = headPosition;
        }
    }

    public int getMinVisited() {
    return minVisited;
        }

    public int getMaxVisited() {
        return maxVisited;
    }

    /**
     * Builds the output string for all visited cells from left to right.
     *
     * @return concatenation of digit symbols on the visited interval
     */
    public String visitedContents() {
        StringBuilder sb = new StringBuilder(maxVisited - minVisited + 1);
        for (int i = minVisited; i <= maxVisited; i++) {
            sb.append((char) ('0' + read(i)));
        }
       
        return sb.toString();
    }
}
