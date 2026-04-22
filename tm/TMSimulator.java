package tm;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Command-line driver for the Turing machine simulator. Expects one argument: the path to an
 * encoding file. Prints the digits on all tape cells visited during the run.
 *
 * @author Maria Gomez and Amy Lee
 */
public final class TMSimulator {

    private TMSimulator() {
    }

    /**
     * Loads a TM from the given file, simulates it on its input, and prints the visited tape.
     *
     * @param args argv[0] must be the input file path
     * @throws Exception if the file cannot be read or parsed
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java tm.TMSimulator <input-file>");
            System.exit(1);
        }
        Path path = Paths.get(args[0]);
        TM machine = new TM(path);
        Tape tape = new Tape();
        tape.writeInitialInput(machine.getInitialInput());
        machine.run(tape, 0);
        System.out.println(tape.visitedContents());
    }
}
