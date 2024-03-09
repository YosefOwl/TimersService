package afeka.ac.il.timersservice.boundaries;

import afeka.ac.il.timersservice.data.DeviceAction;

public class KMessage {

    private String timerId;

    private String deviceId;

    private String deviceType;

    private DeviceAction deviceAction;


    public KMessage(TimerBoundary timerBoundary)
    {
        this.setTimerId(timerBoundary.getTimerId())
                .setDeviceId(timerBoundary.getDeviceId())
                .setDeviceAction(timerBoundary.getDeviceAction())
                .setDeviceId(timerBoundary.getDeviceId());
    }

    public String getTimerId() {
        return timerId;
    }

    public KMessage setTimerId(String timerId) {
        this.timerId = timerId;
        return this;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public KMessage setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
