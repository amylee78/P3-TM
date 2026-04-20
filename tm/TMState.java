package tm;

/**
 * Constants and helpers for Turing machine state labels used in this project.
 * The start state is always {@value #START_ID}; the halting state is always {@code |Q| - 1}.
 *
 * @author Student
 */
public final class TMState {

    /** Label of the initial state (per assignment specification). */
    public static final int START_ID = 0;

    private TMState() {
    }
}
