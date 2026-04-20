package tm;

import java.util.HashMap;
import java.util.Map;


public class TMState {

    private int id;

    private Map<Integer, int[]> transitions;

    public TMState(int id) {
        this.id = id;
        this.transitions = new HashMap<>();
    }

public void addTransition(int symbol, int nextState, int writeSymbol, char move) {

        int direction;
        if (move == 'R') {
            direction = 1;
        }
        else if (move == 'L') {
            direction = -1;
        } 
        else {
            throw new IllegalArgumentException("Move must be 'L' or 'R'");
        }

        transitions.put(symbol, new int[]{nextState, writeSymbol, direction});
    }


    public int[] getTransition(int symbol) {
        return transitions.get(symbol);
    }

    public Map<Integer, int[]> getTransitionsMap() {
        return transitions;
    }

    public int getId() {
        return id;
    }
    
}
