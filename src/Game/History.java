package Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giogio on 9/17/16.
 */
public class History {
    private List<Record> records;

    public History(){
        records = new ArrayList<Record>();
    }

    public void addRecord(Record record){
        records.add(record);
    }

    public void printRecords(){
        for (Record record: records){
            record.printRec();
        }
    }

    public List<Record> getList(){
        return records;
    }
}
