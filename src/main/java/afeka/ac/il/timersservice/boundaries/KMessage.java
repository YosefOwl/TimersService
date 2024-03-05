package afeka.ac.il.timersservice.boundaries;

import afeka.ac.il.timersservice.data.DeviceAction;

public class KMessage {

    private String timerId;

    private String deviceId;

    private DeviceAction deviceAction;


    public KMessage(TimerBoundary timerBoundary)
    {
        setTimerId(timerBoundary.getTimerId())
                .setDeviceId(timerBoundary.getDeviceId())
                .setDeviceAction(timerBoundary.getDeviceAction());
    }

    public String getTimerId() {
        return timerId;
    }

    public KMessage setTimerId(String timerId) {
        this.timerId = timerId;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public KMessage setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public DeviceAction getDeviceAction() {
        return deviceAction;
    }

    public KMessage setDeviceAction(DeviceAction deviceAction) {
        this.deviceAction = deviceAction;
        return this;
    }


    @Override
    public String toString()
    {
        return "KMessage{" +
                "timerId: " + timerId +
                ", deviceId: " + deviceId +
                ", deviceAction: " + deviceAction +
                "}";
    }
}
