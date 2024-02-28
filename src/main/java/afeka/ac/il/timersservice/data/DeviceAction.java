package afeka.ac.il.timersservice.data;

public class DeviceAction {
    private Object onStart; //TODO: what object is it
    private Object onComplete; //TODO: wat object is it

    public DeviceAction(){
    }

    public DeviceAction(Object onStart, Object onComplete) {
        super();
        this.onStart = onStart;
        this.onComplete = onComplete;
    }

    public Object getOnStart() {
        return onStart;
    }

    public void setOnStart(Object onStart) {
        this.onStart = onStart;
    }

    public Object getOnComplete() {
        return onComplete;
    }

    public void setOnComplete(Object onComplete) {
        this.onComplete = onComplete;
    }

    @Override
    public String toString() {
        return "DeviceAction{" +
                "onStart=" + onStart +
                ", onComplete=" + onComplete +
                '}';
    }
}
