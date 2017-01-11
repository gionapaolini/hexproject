package Game;

/**
 * Created by giogio on 10/18/16.
 */
public class TimeMatch {
     short s,m,h;

    public void increment(){
        s++;
        if(s==60){
            s=0;
            m++;
            if(m==60){
                m=0;
                h++;
            }
        }
    }

    public String toString(){
        return h+":"+m+":"+s;
    }
}
