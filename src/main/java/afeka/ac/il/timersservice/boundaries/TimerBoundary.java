package afeka.ac.il.timersservice.boundaries;

import afeka.ac.il.timersservice.data.DeviceAction;
import afeka.ac.il.timersservice.data.Duration;
import afeka.ac.il.timersservice.data.Recurrence;
import afeka.ac.il.timersservice.data.TimerEntity;

import java.util.Date;

public class TimerBoundary {

    private String timerId;
    private String name;
    private String description;
    private Date createdAt;
    private String status;
    private Date startTime;
    private Duration duration;
    private Recurrence recurrence;
    private String deviceId;
    private DeviceAction deviceAction;
    public TimerBoundary(){

    }
    public TimerBoundary(String timerId, String name, String description, Date createdAt, String status, Date startTime, Duration duration, Recurrence recurrence, String deviceId, DeviceAction deviceAction) {
        this.timerId = timerId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
        this.recurrence = recurrence;
        this.deviceId = deviceId;
        this.deviceAction = deviceAction;
    }

    public String getTimerId() {
        return timerId;
    }

    public void setTimerId(String timerId) {
        this.timerId = timerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceAction getDeviceAction() {
        return deviceAction;
    }

    public void setDeviceAction(DeviceAction deviceAction) {
        this.deviceAction = deviceAction;
    }

    public TimerEntity toEntity(){
        TimerEntity rv = new TimerEntity();
        rv.setTimerId(this.getTimerId());
        rv.setName(this.getName());
        rv.setDescription(this.getDescription());
        rv.setCreatedAt(this.getCreatedAt());
        rv.setStatus(this.getStatus());
        rv.setStartTime(this.getStartTime());
        rv.setDuration(this.getDuration());
        rv.setRecurrence(this.getRecurrence());
        rv.setDeviceId(this.getDeviceId());
        rv.setDeviceAction(this.getDeviceAction());

        return rv;
    }

    @Override
    public String toString() {
        return "TimerBoundary{" +
                "timerId='" + timerId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", recurrence=" + recurrence +
                ", deviceId='" + deviceId + '\'' +
                ", deviceAction=" + deviceAction +
                '}';
    }
}
