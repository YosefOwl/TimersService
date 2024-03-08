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
import java.util.UUID;

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

        timer.setTimerId(UUID.randomUUID().toString());
        timer.setCreatedAt(new Date());
        timer.setStatus("hold");

        if (timer.getRecurrence().getType() == null)
            timer.getRecurrence().setType(TYPE.ONCE);


        if (!isValidTimer(timer)){
            return Mono.error(new RuntimeException());
        }

        return Mono.just(timer)
                .map(TimerBoundary::toEntity)
                .flatMap(this.timerCrud::save)
                .map(TimerBoundary::new);

    }


    private boolean isValidTimer(TimerBoundary timer) {
        if (timer == null || timer.getTimerId() == null || timer.getTimerId().isEmpty()
                || timer.getDeviceId() == null || timer.getDeviceId().isEmpty()
                || timer.getStartTime() == null || timer.getStartTime().before(new Date())
                || !isValidDuration(timer.getDuration()) || timer.getRecurrence() == null
                || timer.getRecurrence().getEndDate() == null || timer.getRecurrence().getEndDate().before(timer.getStartTime())
                || timer.getRecurrence().getInterval() <= 0 || timer.getRecurrence().getType() == null)
            return false;

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
            System.out.println("******* updating timer *******");
            return this.timerCrud.findById(id)
                    .flatMap(exists -> {
                                if (timer.getDescription() != null && !timer.getDescription().isEmpty())
                                    exists.setDescription(timer.getDescription());
                                if (timer.getDuration() != null || isValidDuration(timer.getDuration()))
                                    exists.setDuration(timer.getDuration());
                                if (timer.getName() != null && !timer.getName().isEmpty())
                                    exists.setName(timer.getName());
                                if (timer.getUpdateTime() != null && !timer.getUpdateTime().before(exists.getStartTime()))
                                    exists.setUpdateTime(timer.getUpdateTime());
                                if (timer.getRecurrence() != null && timer.getRecurrence().getInterval() > 0
                                        && timer.getRecurrence().getEndDate() != null && timer.getRecurrence().getEndDate().after(exists.getStartTime()))
                                    exists.setRecurrence(timer.getRecurrence());
                            return this.timerCrud.save(exists);
                            }).switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException())))
                    .map(TimerBoundary::new);


//                        if (exists) {
//                            // TODO: validate info, update entity save
//                            return Mono.just(timer);
//                        }
//                        else {
//                            return Mono.error(new RuntimeException());
//                        }
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
