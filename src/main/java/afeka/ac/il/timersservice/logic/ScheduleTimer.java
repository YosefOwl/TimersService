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
    @Scheduled(fixedDelay = 5000) // Cron expression for every 60 seconds
    public void checkTimerBoundary() {

        Flux<TimerEntity> entities = this.timerCrud.findAllByStatusHoldOrActice();

        entities.map(TimerBoundary::new)
                .filter(timerBoundary -> (new Date()).after(timerBoundary.getStartTime()))
                .subscribe(timer -> manageTimer((TimerBoundary) timer));
    }


    public Mono<TimerBoundary> sendToKafka(TimerBoundary timerBoundary){

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

    public void manageTimer(TimerBoundary timerBoundary){

        if (timerBoundary.getStatus().equals("active") &&
                timerBoundary.getFinishedTime().before(new Date())) //If timer is active and if the timer is finished
        {
            setStatusByCrud(timerBoundary,"hold"); // Update in the DB
            // TO DO - SEND TO KAFKA ?
        } else if(timerBoundary.getUpdateTime().after(new Date())) { //else (hold) and if the timer is need to start
            // timerBoundary.getStatus() = hold
            // TO DO - SEND TO KAFKA ?
            setStatusByCrud(timerBoundary,"active"); // Update in the DB
        }else if(checkEndTime(timerBoundary)){ // timerBoundary.getStatus() = hold and need to complete
                setStatusByCrud(timerBoundary,"complete"); // Update in the DB
        }

        System.err.println(timerBoundary);

    }

    public boolean checkEndTime(TimerBoundary timerBoundary)
    {
        if(timerBoundary.getRecurrence().getEndDate() == null)
            return false;
        return timerBoundary.getUpdateTime().before(new Date());
    }

    public void setStatusByCrud(TimerBoundary timerBoundary, String status)
    {
        timerBoundary.setStatus(status);
        this.timerCrud.save(timerBoundary.toEntity());
    }

}
