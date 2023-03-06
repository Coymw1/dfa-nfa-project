package main.model;

import java.util.List;

public class DFA extends NFA {
    public DFA(String[] Q, List<String> E, String[][] D, String q0, List<String> F) {
        super(Q, E, D, q0, F);
        //since this is a DFA, every state must have an arrow for each symbol in alphabet
        if (D.length != Q.length*E.size()) {throw new IllegalArgumentException();}
    }
}
