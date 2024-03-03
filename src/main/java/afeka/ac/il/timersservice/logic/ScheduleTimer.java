package afeka.ac.il.timersservice.logic;

import afeka.ac.il.timersservice.boundaries.TimerBoundary;
import afeka.ac.il.timersservice.data.TimerEntity;
import afeka.ac.il.timersservice.dataAccess.TimerCrud;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class ScheduleTimer {

    private Flux<TimerBoundary> timerBoundaries;
    private ObjectMapper jackson;
    private final StreamBridge kafka;
    private final TimerCrud timerCrud;

    private String targetTopic;

    public  ScheduleTimer(TimerCrud timerCrud,StreamBridge kafka){
        this.kafka = kafka;
        this.timerCrud = timerCrud;
    }

    @PostConstruct
    public void init(){
        this.jackson = new ObjectMapper();
    }

    @Value("$target.topic.name:anyTopic")
    public void setTargetTopic(String targetTopic){ this.targetTopic = targetTopic; }


    // This method will be executed every second
    @Scheduled(fixedDelay = 60000) // Cron expression for every 60 seconds
    public void checkTimerBoundary() {

        Flux<TimerEntity> entities = this.timerCrud.findAll();

        entities.map(TimerBoundary::new)
                .filter(timerBoundary -> (new Date()).after(timerBoundary.getStartTime()))
                .subscribe(timer -> sendToKafka((TimerBoundary) timer));
    }


    public Mono<TimerBoundary> sendToKafka(TimerBoundary timerBoundary){
        System.err.println("ON: " + timerBoundary);
        try{
            String messageToKafka = this.jackson.writeValueAsString(timerBoundary);
            return Mono.just(messageToKafka)
                    .map(msg ->
                            kafka.send(targetTopic, msg))
                    .then(Mono.just(timerBoundary));
        }catch (Exception err){
            throw new RuntimeException(err);
        }
    }

}
