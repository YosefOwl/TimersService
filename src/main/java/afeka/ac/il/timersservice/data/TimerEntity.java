package afeka.ac.il.timersservice.data;

import afeka.ac.il.timersservice.boundaries.Recurrence;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "timers")
public class TimerEntity {
    @Id
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
    private String deviceType;
    private DeviceAction deviceAction;
    public TimerEntity(){

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

    public String getDeviceType(){
        return deviceType;
    }
    public void setDeviceType(String deviceType){
        this.deviceType = deviceType;
    }

    public DeviceAction getDeviceAction() {
        return deviceAction;
    }

    public void setDeviceAction(DeviceAction deviceAction) {
        this.deviceAction = deviceAction;
    }

    @Override
    public String toString() {
        return "TimerEntity{" +
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
                ", deviceType='"+ deviceType +'\''+
                ", deviceAction=" + deviceAction +
                '}';
    }
}
