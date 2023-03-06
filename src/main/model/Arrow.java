package main.model;

public class Arrow {


    private String id;
    private State next;

    public Arrow(String id, State n) {
        if (id == null || n == null) {throw new IllegalArgumentException();}
        next = n;
        this.id = id;
    }

    public State arrowPointsTo() {
        return next;
    }

    public String getSymbol() {
        return this.id;
    }

}
