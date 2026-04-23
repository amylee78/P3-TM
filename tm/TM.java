package tm;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents a deterministic Turing machine with a bi-infinite tape alphabet
 * Γ = {0} ∪ Σ, blank 0, input symbols 1…m. State 0 is start; state {@code numStates - 1} halts.
 *
 * @author  Maria Gomez and Amy Lee
 */
public final class TM {

    /** Total number of states {@code Q}. */
    private final int numStates;

    /** Number of input alphabet symbols {@code m}; Σ = {1,…,m}. */
    private final int sigmaSize;

    /** |Γ| = m + 1 (blank plus input symbols). */
    private final int gammaSize;

    /** Halting / accepting state index. */
    private final int haltState;

    /** Flat transition table indexed by {@code state * gammaSize + tapeSymbol}. */
    private final int[] nextState;

    /** Write symbol for each transition (same indexing). */
    private final int[] writeSymbol;

    /** Move: false = L, true = R (same indexing). */
    private final boolean[] moveRight;

    /** Input line after the transition block (possibly empty for ε). */
    private final String initialInput;

    /**
     * Loads a machine description and input line from a file.
     *
     * @param path path to the encoding file
     * @throws IOException if reading fails or the format is invalid
     */
    public TM(Path path) throws IOException {
        try (BufferedReader in = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = in.readLine();
            if (line == null) {
                throw new IOException("Empty file");
            }
            this.numStates = Integer.parseInt(line.trim());
            if (numStates < 2) {
                throw new IOException("Need at least start and halt states");
            }
            line = in.readLine();
            if (line == null) {
                throw new IOException("Missing alphabet size");
            }
            this.sigmaSize = Integer.parseInt(line.trim());
            if (sigmaSize < 1 || sigmaSize > 9) {
                throw new IOException("Sigma size must be 1–9");
            }
            this.gammaSize = sigmaSize + 1;
            this.haltState = numStates - 1;

            int transitionCount = (numStates - 1) * gammaSize;
            this.nextState = new int[transitionCount];
            this.writeSymbol = new int[transitionCount];
            this.moveRight = new boolean[transitionCount];

            for (int i = 0; i < transitionCount; i++) {
                line = in.readLine();
                if (line == null) {
                    throw new IOException("Unexpected end of file in transition section");
                }
                parseTransitionLine(line.trim(), i);
            }

            line = in.readLine();
            this.initialInput = line == null ? "" : line;
        }
    }

    /**
     * Parses one transition line {@code next,write,move}.
     *
     * @param line trimmed line content
     * @param index flat index in the transition arrays
     */
    private void parseTransitionLine(String line, int index) {
        String[] parts = line.split(",", -1);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Bad transition line: " + line);
        }
        nextState[index] = Integer.parseInt(parts[0].trim());
        writeSymbol[index] = Integer.parseInt(parts[1].trim());
        String move = parts[2].trim();
        if (move.length() != 1) {
            throw new IllegalArgumentException("Bad move: " + line);
        }
        char m = move.charAt(0);
        switch (m) {
            case 'R':
                moveRight[index] = true;
                break;
            case 'L':
                moveRight[index] = false;
                break;
            default:
                throw new IllegalArgumentException("Move must be L or R: " + line);
        }
    }

    /**
     * Returns the input string line (possibly empty).
     *
     * @return raw input line from the file
     */
    public String getInitialInput() {
        return initialInput;
    }

    /**
     * Runs the machine on the given tape until the halt state is entered.
     *
     * @param tape tape (already initialized with input); head position passed separately
     * @param head starting head coordinate
     */
    public void run(Tape tape, int head) {
        int state = TMState.START_ID;
        tape.noteHeadAt(head);
        while (state != haltState) {
            int sym = tape.read(head);
            int ti = transitionIndex(state, sym);
            tape.write(head, writeSymbol[ti]);
            state = nextState[ti];
            if (state == haltState) {
                break;
            }
            if (moveRight[ti]) {
                head++;
            } else {
                head--;
            }
            tape.noteHeadAt(head);
        }
    }






    /**
     * Flat index for a transition from {@code state} reading {@code tapeSymbol}.
     *
     * @param state current state (&lt; halt state)
     * @param tapeSymbol symbol under the head in Γ
     * @return index into {@link #nextState}, {@link #writeSymbol}, {@link #moveRight}
     */
    private int transitionIndex(int state, int tapeSymbol) {
        return state * gammaSize + tapeSymbol;
    }
}
