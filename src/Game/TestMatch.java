package Game;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by giogio on 9/17/16.
 */
public class TestMatch {
    public static void main(String[] args) throws InterruptedException, IOException {
        Match match = new Match(false,false,false,false,11);
        History history = new History();
        Record r1 = new Record(false,(byte)3,(byte)4);
        Thread.sleep(1000);
        Record r2 = new Record(true,(byte)3,(byte)7);
        Thread.sleep(1000);
        Record r3 = new Record(false,(byte)2,(byte)4);
        Thread.sleep(1000);
        Record r4 = new Record(true,(byte)6,(byte)4);
        Thread.sleep(1000);
        Record r5 = new Record(false,(byte)3,(byte)1);
        history.addRecord(r1);
        history.addRecord(r2);
        history.addRecord(r3);
        history.addRecord(r4);
        history.addRecord(r5);
        history.printRecords();

        match.history=history;
        match.saveMatch();
        match.loadMatch();

    }
}
