package afeka.ac.il.timersservice.logic;


import afeka.ac.il.timersservice.boundaries.TimerBoundary;
import afeka.ac.il.timersservice.dataAccess.TimerCrud;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TimerServiceImpl implements TimerService{
    private TimerCrud timerCrud;
    private StreamBridge kafka;
    private ObjectMapper jackson;

    public TimerServiceImpl(TimerCrud timerCrud, StreamBridge kafka){
        this.timerCrud = timerCrud;
        this.kafka = kafka;
    }

    @PostConstruct
    public void init(){
        this.jackson = new ObjectMapper();
    }

    @Override
    public Mono<TimerBoundary> createTimer(TimerBoundary timer) {

        timer.setTimerId(null);
        timer.setCreatedAt(new Date());

        //TODO: add validate object

        return Mono.just(timer)
                .map(TimerBoundary::toEntity)
                .flatMap(this.timerCrud::save)
                .map(TimerBoundary::new);
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
