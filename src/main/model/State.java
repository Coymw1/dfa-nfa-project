package main.model;

import java.util.HashMap;
import java.util.Map;


public class State {

    private String id;
    private Map<String, Arrow> arrowsFrom;
    private boolean isStart;
    private boolean isAccept;

    public State(String id, boolean start, boolean accept) {
        if (id == null) {throw new IllegalArgumentException();}
        this.id = id;
        isStart = start;
        isAccept = accept;
        arrowsFrom = new HashMap<>();
    }
    public String getId() {return id;}

    public boolean isStartState() {return isStart;}

    public boolean isAcceptState() {return isAccept;}

    public Map<String, Arrow> getArrowsFrom() {
        return arrowsFrom;
    }

    public State next(String arrowID) {
        return getArrowsFrom().get(arrowID).arrowPointsTo();
    }
}

