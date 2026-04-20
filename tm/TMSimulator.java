package tm;


import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TMSimulator {

  /**
   * 
   * @param args
   * @throws Exception
   */
   public static void main(String[] args) throws Exception {

        BufferedReader tmFileReader = new BufferedReader(new FileReader(args[0]));

    
        int numStates = Integer.parseInt(tmFileReader.readLine());
        int numSymbols = Integer.parseInt(tmFileReader.readLine());

        List<TMState> states = createStates(numStates);

        readTransitions(tmFileReader, states, numStates, numSymbols);

        String input = readInput(tmFileReader);

        tmFileReader.close();

        runSimulation(states, numStates, input);
    }

    /**
     * 
     * @param numStates
     * @return
     */
    private static List<TMState> createStates(int numStates) {
        List<TMState> states = new ArrayList<>();

        for (int i = 0; i < numStates; i++) {
            states.add(new TMState(i));
        }

        return states;
    }

    /**
     * 
     * @param tmFileReader
     * @param states
     * @param numStates
     * @param numSymbols
     * @throws IOException
     */
    private static void readTransitions(BufferedReader tmFileReader, List<TMState> states, int numStates, int numSymbols) throws IOException {

        for (int state = 0; state < numStates - 1; state++) {
            for (int symbol = 0; symbol <= numSymbols; symbol++) {

                String line = tmFileReader.readLine();
                String[] parts = line.split(",");

                int nextState = Integer.parseInt(parts[0]);
                int writeSymbol = Integer.parseInt(parts[1]);
                char move = parts[2].charAt(0);

                states.get(state).addTransition(symbol, nextState, writeSymbol, move);
            }
        }
    }


    private static String readInput(BufferedReader tmFileReader) throws IOException {
        String input = tmFileReader.readLine();
        if (input == null) {
        
            return "";
        }
        return input;
    }

   
    private static void runSimulation(List<TMState> states, int numStates, String input) {

        Map<Integer, Integer> tape = initializeTape(input);

        int head = 0;
        int currentState = 0;
        int haltState = numStates - 1;

        int min = 0, max = 0;

        while (currentState != haltState) {

            int symbol = tape.getOrDefault(head, 0);

            int[] transition = states.get(currentState).getTransition(symbol);

            int nextState = transition[0];
            int writeSymbol = transition[1];
            int direction = transition[2];

            tape.put(head, writeSymbol);

            head += direction;

            currentState = nextState;

            min = Math.min(min, head);
            max = Math.max(max, head);
        }

        printTape(tape, min, max);
    }

    /**
     * intizalize tape.  more tbw
     * @param input
     * @return
     */
    private static Map<Integer, Integer> initializeTape(String input) {

        Map<Integer, Integer> tape = new HashMap<>();

        for (int i = 0; i < input.length(); i++) {
            tape.put(i, input.charAt(i) - '0');
        }

        return tape;
    }

    /**
     * prints out output tbw
     * @param tape
     * @param min
     * @param max
     */
    private static void printTape(Map<Integer, Integer> tape, int min, int max) {

        for (int i = min; i <= max; i++) {
            System.out.print(tape.getOrDefault(i, 0));
        }

        System.out.println();
    }
}