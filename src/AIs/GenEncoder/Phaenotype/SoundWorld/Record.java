package AIs.GenEncoder.Phaenotype.SoundWorld;

import Game.History;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nibbla on 15.10.2016.
 */
public abstract class Record {
    LinkedList<Note> r;
    String name;

    public Record(String name){
        r = new LinkedList<>();
        this.name = name;
    }

    public static Record createRecord(History g, String name){
        return null;
    }

}
