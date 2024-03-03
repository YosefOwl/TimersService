package afeka.ac.il.timersservice.data;

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
    private Duration duration;
    private Recurrence recurrence;
    private String deviceId;
    private DeviceAction deviceAction;
    public TimerEntity(){

    }

    public String getTimerId() {
        return timerId;
    }

    public TimerEntity setTimerId(String timerId) {
        this.timerId = timerId;
        return this;
    }

    public String getName() {
        return name;
    }

    public TimerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TimerEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public TimerEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TimerEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public TimerEntity setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Duration getDuration() {
        return duration;
    }

    public TimerEntity setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public TimerEntity setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public TimerEntity setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public DeviceAction getDeviceAction() {
        return deviceAction;
    }

    public TimerEntity setDeviceAction(DeviceAction deviceAction) {
        this.deviceAction = deviceAction;
        return this;
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
