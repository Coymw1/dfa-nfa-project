package main;

import main.model.NFA;
import main.model.DFA;

import java.util.ArrayList;
import java.util.List;



public class Main {

    public static void main(String[] args) {
        //Q = set of states
        //E = alphabet
        //D = transition function
        //q0 = start state
        //F = set of accept states
        String[] Q =  new String[] {"q0", "q1", "q2"};

        List<String> E = new ArrayList<>();
        E.add("a");
        E.add("b");

        String[][] D = new String[][]  {
                 {"q0", "a", "q1"},
                 {"q0", "b", "q2"},
                 {"q1", "a", "q1"},
                 {"q1", "b", "q1"},
                 {"q2", "a", "q1"},
                 {"q2", "b", "q2"},
        };

        List<String> F = new ArrayList<>();
        F.add("q1");

        //In this example the start state is q0
        DFA direct = new DFA(Q, E, D, "q0", F);
        String[] testInput = new String[]{"b", "a"};
    }
}