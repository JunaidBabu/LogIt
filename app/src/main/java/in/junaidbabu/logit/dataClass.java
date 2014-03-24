package in.junaidbabu.logit;

/**
 * Created by neo on 24/3/14.
 */
public class dataClass {
    int id;
    String type;
    String timestamp;
    String datatext;

    public dataClass(String tpe, String time, String text) {
        super();
        this.type = tpe;
        this.timestamp = time;
        this.datatext = text;
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

}
