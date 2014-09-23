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
    String extratext="";
    String timestamp;
    String entryid;
    int issync = 0;

    public dataClass(String tpe, String time, String text) {
        super();
        this.type = tpe;
        this.timestamp = time;
        this.datatext = text;
    }

    public dataClass(String Start, String End, String Type, String Latlng, String Datatext, String extra, String time){
        super();
        this.start = Start;
        this.end = End;
        this.type = Type;
        this.latlong = Latlng;
        this.datatext = Datatext;
        this.extratext = extra;
        this.timestamp = time;
    }

    public dataClass(int i, String Start, String End, String Type, String Latlng, String Datatext, String extra, String time, String enid, int iss){
        super();
        this.id = i;
        this.start = Start;
        this.end = End;
        this.type = Type;
        this.latlong = Latlng;
        this.datatext = Datatext;
        this.extratext = extra;
        this.timestamp = time;
        this.entryid = enid;
        this.issync = iss;
    }

    public dataClass() {

    }

    String getEverything(){
        return id+start+end+type+latlong+datatext+extratext+timestamp;
    }
    public void setIsSync(int a){
        this.issync = a;
    }
    public int getId() {
        return this.id;
    }
    public String getType(){
        return this.type;
    }
    public String getTimestamp(){
        return this.timestamp;
    }
    public String getText(){
        return this.datatext;
    }
    public String getStart(){
        return this.start;
    }
    public String getEnd(){
        return this.end;
    }
    public String getLatlong(){
        return this.latlong;
    }
    public int getIssync(){
        return this.issync;
    }
    public String getEntryid(){
        return this.entryid;
    }

    public String getTextExtra() {
        return this.extratext;
    }
}
