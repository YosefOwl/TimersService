package afeka.ac.il.timersservice.data;

import java.util.Date;

enum TYPE{ONCE,DAILY,MONTHLY,ANNUALLY}
public class Recurrence {
    private TYPE type;
    private int interval;
    private Date endDate;

    public Recurrence() {

    }
    public Recurrence(TYPE type, int interval, Date endDate) {
        super();
        this.type = type;
        this.interval = interval;
        this.endDate = endDate;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Recurrence{" +
                "type=" + type +
                ", interval=" + interval +
                ", endDate=" + endDate +
                '}';
    }
}
