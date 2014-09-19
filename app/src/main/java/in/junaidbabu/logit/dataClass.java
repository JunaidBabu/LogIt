package in.junaidbabu.logit;

import android.location.Location;

import java.util.Date;

/**
 * Created by neo on 24/3/14.
 */
public class dataClass {
    int id;
    String start;
    String end;
    String type;
    String latlong;
    String datatext;
    String timestamp;
    String entryid;
    int issync = 0;

    public dataClass(String tpe, String time, String text) {
        super();
        this.type = tpe;
        this.timestamp = time;
        this.datatext = text;
    }

    public dataClass(String Start, String End, String Type, String Latlng, String Datatext, String time){
        super();
        this.start = Start;
        this.end = End;
        this.type = Type;
        this.latlong = Latlng;
        this.datatext = Datatext;
        this.timestamp = time;
    }

    int getId() {
        return this.id;
    }
    String getType(){
        return this.type;
    }
    String getTimestamp(){
        return this.timestamp;
    }
    String getText(){
        return this.datatext;
    }
    String getStart(){
        return this.start;
    }
    String getEnd(){
        return this.end;
    }
    String getLatlong(){
        return this.latlong;
    }
    String getDatatext(){
        return this.datatext;
    }
    int getIssync(){
        return this.issync;
    }
    String getEntryid(){
        return this.entryid;
    }
}
