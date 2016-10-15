package AIs.GenEncoder.Phaenotype.SoundWorld;

import java.util.ArrayList;

/**
 * Created by Nibbla on 15.10.2016.
 */
public interface Sound {
    void addNote(Note n, double loudness, double d);

    default Sound createFrom(ArrayList<Note> notes) {
        return null;
    }

    Note createMainNote();
    double getLoudness();
}
