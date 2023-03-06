package main.model;

import java.util.*;

public class NFA {

    //stores each state using the state id as key
    private Map<String, State> statesMap;
    //stores state names
    private String[] states;
    //list of strings in alphabet
    private String[][] transFunc;
    private List<String> alphabet;
    //encapsulated state instance to represent the start of the automaton
    private State startState;
    //list of accept states
    private List<State> acceptStates;
    //encapsulated state instance to represent the current state when moving through the automaton
    private State currentState;

    public NFA(String[] Q, List<String> E, String[][] D, String q0, List<String> F) {
        //parameter checking
        if (Q == null || E == null || D == null || q0 == null || F == null) {throw new IllegalArgumentException();}
        for (String[] function : D) {
            if (function == null) {throw new IllegalArgumentException();}
        }
        //checks if the set of states (Q) contains the start state (q0)
        int marker = 0;
        for (int i = 0; i < Q.length; i++) {
            if (Q[i].equals(q0)) {marker = 1;}
        }
        if (marker == 0) {throw new IllegalArgumentException();}

        //can't have more accept states that total states
        if (F.size() > Q.length) {throw new IllegalArgumentException();}


        statesMap = new HashMap<>();
        states = Q;
        acceptStates = new ArrayList<>();
        for (String s : Q) {
            //is start state and accept state
            boolean sta = false;
            boolean acc = false;

            if (s.equals("q0")) {sta = true;}
            if (F.contains(s)) {acc = true;
            }

            //regular state
            State state = new State(s, sta, acc);
            statesMap.put(s, state);

            if (state.isAcceptState()) {acceptStates.add(state);}
            if (state.isStartState()) {startState = state;}
        }

        // example
        // {
            // {"q0", "a", "q1"}
            // {"q0", "b", "q1"}
            // {"q1", "a", "q0"}
            // {"q1", "a", "q0"}
            //                      }

        //use transition function to determine initialize all states with the correct information (state type, arrow info)
        //add all arrows to hashmap object stored in this state
        for (String s : states) {
            //iterates through the transition function parameter
            //transition function parameter is an array where each element is an array representing a transition between two states
            for (String[] transitionFunctionArray : D) {
                //if the first element in the array matches the name of the current state,
                // we know that this array will be used for this state's arrows
                if (transitionFunctionArray[0].equals(s)) {
                    //next, we check the last element in the array for information about what state the arrow points to
                    //the current state's arrow hashmap is updated
                    Arrow addedArrow = new Arrow(transitionFunctionArray[1], statesMap.get(transitionFunctionArray[2]));
                    statesMap.get(s).getArrowsFrom().put(transitionFunctionArray[1], addedArrow);
                }
            }
//            System.out.println(statesMap.get(s).getId() + " has this many arrows pointing from it: " + statesMap.get(s).getArrowsFrom().size());
//            System.out.println("The arrows from " + s + " point to: " + statesMap.get(s).getArrowsFrom().get("a").arrowPointsTo().getId() + " and " + statesMap.get(s).getArrowsFrom().get("b").arrowPointsTo().getId());
        }
        alphabet = E;
        transFunc = D;
        startState = statesMap.get(states[0]);
        currentState = startState;

    }

    //goes through automaton with given input and returns the last state
    public State readInput(String[] input) {
        for (int i = 0; i < input.length; i++) {
            if (alphabet.contains(input[i])) {
                goToNextState(input[i]);
            }
        }
        return currentState;
    }


    public boolean isInputAccepted(String[] input) {
        if (readInput(input).isAcceptState()) {
            System.out.println("This string is accepted, and ends at accept state " + currentState.getId());
            return true;
        }
        else {System.out.println("This string is not accepted, because it ends at " + currentState.getId() + ", which is not an accept state.");
            return false;
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public void getCurrentStateInfo() {
        System.out.println("State name: " + currentState.getId());
        System.out.println("Start state?: " + currentState.isStartState());
        System.out.println("Accept state?: " + currentState.isAcceptState());
        System.out.println(" ");
        for (String s : alphabet) {
            if (currentState.getArrowsFrom().get(s) != null) {
                System.out.println("This state has an arrow '" + currentState.getArrowsFrom().get(s).getSymbol() + "', which points to this state: " + currentState.getArrowsFrom().get(s).arrowPointsTo().getId());
            }
        }
    }

    public void goToNextState(String arrowID) {
        State temp;
        temp = currentState.next(arrowID);
        currentState = temp;
    }

    public Map<String, State> getStatesMap() {
        return statesMap;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<State> getAcceptStates() {
        return acceptStates;
    }

    public State getStartState() {
        return startState;
    }

    public String[] getStateIDs() {
        return states;
    }
}
