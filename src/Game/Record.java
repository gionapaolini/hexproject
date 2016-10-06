package Game;

import Game.Enums.ColorMode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by giogio on 9/17/16.
 */
public class Record {

    private boolean status;
    private ColorMode player;
    private byte row, column;
    private Date time;
    private SimpleDateFormat format;

    public Record(ColorMode colorMode, byte row, byte column) {
        format=new SimpleDateFormat("HH:mm:ss");
        this.player = colorMode;
        this.row = row;
        this.column = column;
        time = new Date();
        status = true;
    }
    public Record(ColorMode colorMode, byte row, byte column, Date date, boolean status) {
        format=new SimpleDateFormat("HH:mm:ss");
        this.player = colorMode;
        this.row = row;
        this.column = column;
        this.time = date;
        this.status = status;
    }

    public void unDone(){
        status=false;
    }

    public String toString(){
        return player+" "+row+" "+column+" "+ format.format(time)+" "+status;
    }
    public void printRec(){
        String stat;
        if(status){
            stat=new String("Active");
        }else {
            stat=new String("Undone");
        }
        System.out.println("Player "+player+" moved on "+row+", "+column+" - "+ format.format(time)+" |  Current Status: "+stat);
    }

    public ColorMode getPlayer() {
        return player;
    }

    public byte getRow() {
        return row;
    }

    public byte getColumn() {
        return column;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
