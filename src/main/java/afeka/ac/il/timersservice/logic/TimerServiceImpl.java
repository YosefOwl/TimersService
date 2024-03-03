package afeka.ac.il.timersservice.logic;


import afeka.ac.il.timersservice.boundaries.TimerBoundary;
import afeka.ac.il.timersservice.data.Duration;
import afeka.ac.il.timersservice.boundaries.Recurrence;
import afeka.ac.il.timersservice.boundaries.TYPE;
import afeka.ac.il.timersservice.dataAccess.TimerCrud;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TimerServiceImpl implements TimerService{
    private final TimerCrud timerCrud;

    private ScheduleTimer scheduleTimer;

    public TimerServiceImpl(TimerCrud timerCrud, ScheduleTimer scheduleTimer){
        this.timerCrud = timerCrud;
        this.scheduleTimer = scheduleTimer;
    }

    @Override
    public Mono<TimerBoundary> createTimer(TimerBoundary timer) {

        timer.setTimerId(null);
        timer.setCreatedAt(new Date());
        timer.setStatus("hold");

        if (timer.getRecurrence().getType() == null)
            timer.getRecurrence().setType(TYPE.ONCE);


        if (!isValidTimer(timer)){
            return Mono.error(new RuntimeException());
        }

        this.scheduleTimer.addTimerBoundary(timer);

        return Mono.just(timer)
                .map(TimerBoundary::toEntity)
                .flatMap(this.timerCrud::save)
                .map(TimerBoundary::new);
    }


    private boolean isValidTimer(TimerBoundary timer) {
        String deviceId = timer.getDeviceId();

        if (deviceId == null || deviceId.isEmpty())
            return false;

        Date startTime = timer.getStartTime();
        Date currentTime = new Date();

        if (startTime.before(currentTime))
            return false;

        if (!isValidDuration(timer.getDuration()))
            return false;

        Recurrence recurrence = timer.getRecurrence();

        // TODO: verify type

        // Check if interval is a positive integer
        if (recurrence.getInterval() <= 0)
            return false;


        // Check if endDate is optional and has a valid format if present
        if (recurrence.getEndDate() != null && recurrence.getEndDate().before(startTime)) {
            return false;
        }

        return true;
    }
    private boolean isValidDuration(Duration duration) {
        return isNonNegative(duration.getHours()) &&
                isInRange(duration.getMinutes()) &&
                isInRange(duration.getSeconds());
    }

    private boolean isNonNegative(Integer value) {
        return value != null && value >= 0;
    }

    private boolean isInRange(Integer value) {
        return value != null && value >= 0 && value <= 59;
    }

    @Override
    public Mono<TimerBoundary> updateTimer(TimerBoundary timer) {

        String id = timer.getTimerId();
        if (id == null || id.isEmpty()) {
            return Mono.error(new RuntimeException());
        }
        else {
            return this.timerCrud.existsById(id)
                    .flatMap(exists -> {
                        if (exists) {
                            // TODO: validate info, update entity save
                            return Mono.just(timer);
                        }
                        else {
                            return Mono.error(new RuntimeException());
                        }
                    });
        }
    }

    @Override
    public Mono<TimerBoundary> getById(String id) {
        return this.timerCrud
                .findById(id)
                .map(TimerBoundary::new);
    }

    @Override
    public Flux<TimerBoundary> getByDeviceId(String id) {
        return this.timerCrud
                .findAllByDeviceId(id)
                .map(TimerBoundary::new);
    }

    @Override
    public Mono<Void> disableTimer(String id) {

        return this.timerCrud
                .findById(id)
                .flatMap(timerEntity -> {
                    timerEntity.setStatus("canceled");
                    return this.timerCrud.save(timerEntity);
                })
                .then();
    }

    @Override
    public Mono<Void> disableDeviceTimers(String id) {
        return this.timerCrud
                .findAllByDeviceId(id)
                .flatMap(timerEntity -> {
                    timerEntity.setStatus("canceled");
                    return this.timerCrud.save(timerEntity);
                })
                .then();
    }

    @Override
    public Mono<Void> deleteAll() {
        return this.timerCrud
                .deleteAll();
    }
}
