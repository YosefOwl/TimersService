package afeka.ac.il.timersservice.boundaries;

import afeka.ac.il.timersservice.data.DeviceAction;
import afeka.ac.il.timersservice.data.Duration;
import afeka.ac.il.timersservice.boundaries.Recurrence;
import afeka.ac.il.timersservice.data.TimerEntity;

import java.util.Calendar;
import java.util.Date;

public class TimerBoundary implements Comparable<TimerBoundary> {

    private String timerId;
    private String name;
    private String description;
    private Date createdAt;
    private String status;
    private Date startTime;
    private Date updateTime;
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
        this.updateTime = this.startTime;
        this.duration = duration;
        this.recurrence = recurrence;
        this.deviceId = deviceId;
        this.deviceAction = deviceAction;
    }
    public TimerBoundary(TimerEntity entity){
        this.timerId = entity.getTimerId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.createdAt = entity.getCreatedAt();
        this.status = entity.getStatus();
        this.startTime = entity.getStartTime();
        this.updateTime = entity.getUpdateTime();
        this.duration = entity.getDuration();
        this.recurrence = entity.getRecurrence();
        this.deviceId = entity.getDeviceId();
        this.deviceAction = entity.getDeviceAction();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        rv.setUpdateTime(this.getUpdateTime());
        rv.setDuration(this.getDuration());
        rv.setRecurrence(this.getRecurrence());
        rv.setDeviceId(this.getDeviceId());
        rv.setDeviceAction(this.getDeviceAction());

        return rv;
    }

    public Date getFinishedTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.updateTime);
        calendar.add(Calendar.HOUR_OF_DAY, this.duration.getHours());
        calendar.add(Calendar.MINUTE, this.duration.getMinutes());
        calendar.add(Calendar.SECOND, this.duration.getSeconds());
        return calendar.getTime();
    }

    public void clacNewUpdateTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.updateTime);
        switch (this.getRecurrence().getType()){
            case DAILY:
                calendar.add(Calendar.DAY_OF_WEEK ,this.getRecurrence().getInterval());
                break;
            case MONTHLY:
                calendar.add(Calendar.MONTH, this.getRecurrence().getInterval());
                break;
            case ANNUALLY:
                calendar.add(Calendar.YEAR, this.getRecurrence().getInterval());
                break;
            case ONCE:
                break;
        }

        this.updateTime = calendar.getTime();

    }

    @Override
    public int compareTo(TimerBoundary other) {
        return this.updateTime.compareTo(other.updateTime);
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
                ", updateTime=" + updateTime +
                ", duration=" + duration +
                ", recurrence=" + recurrence +
                ", deviceId='" + deviceId + '\'' +
                ", deviceAction=" + deviceAction +
                '}';
    }
}
