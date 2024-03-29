package afeka.ac.il.timersservice.logic;

import afeka.ac.il.timersservice.boundaries.*;
import afeka.ac.il.timersservice.data.TimerEntity;
import afeka.ac.il.timersservice.dataAccess.TimerCrud;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class ScheduleTimer {

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


    // This method will be executed every 60 second
    @Scheduled(fixedDelay = 60000) // Cron expression for every 60 seconds
    public void checkTimerBoundary() {

        Flux<TimerEntity> entities = this.timerCrud.findAllByStatusHoldOrActive();
        Date currentDate = new Date();

        entities.map(TimerBoundary::new)
                .filter(timerBoundary -> currentDate.after(timerBoundary.getUpdateTime()))
                .flatMap(this::manageTimer)
                .subscribe(timerBoundary -> {
                    // You can log the result or perform further actions if needed
                    System.err.println("Processed timer: " + timerBoundary);
                });

    }


    public Mono<KMessage> sendToKafka(TimerBoundary timerBoundary, boolean isOn){

        try{
            KMessage km = new KMessage(timerBoundary,isOn);
            System.err.println("sendToKafka " + km);
            Map<String, Object> messageDetails = new HashMap<>();
            messageDetails.put(isOn ? "onStart": "onComplete", km);


            MessageBoundary message = new MessageBoundary()
                    .setMessageType("timerNotification")
                    .setExternalReferences(new ExternalReferenceBoundary()
                            .setService("timerService")
                            .setExternalServiceId(timerBoundary.getTimerId()))
                    .setMessageDetails(messageDetails)
                    .setSummary("message from timers on start or complete timer");

            String messageToKafka = this.jackson.writeValueAsString(message);

            return Mono.just(messageToKafka)
                    .map(msg ->
                            kafka.send(targetTopic, msg))
                    .then(Mono.just(km));
        }catch (Exception err){
            throw new RuntimeException(err);
        }
    }

    public Mono<TimerBoundary> manageTimer(TimerBoundary timerBoundary){


        if (timerBoundary.getStatus().equals("active") &&
                timerBoundary.calculateFinishedTime().before(new Date())) //If timer is active and if the timer is finished
        {
            timerBoundary.clacNewUpdateTime();

            if (checkEndTime(timerBoundary))
                setStatusByCrud(timerBoundary,"complete");
            else
                setStatusByCrud(timerBoundary,"hold"); // Update in the DB
            sendToKafka(timerBoundary,false);
        } else if(timerBoundary.getStatus().equals("hold") &&
                timerBoundary.getUpdateTime().before(new Date())) { //If is hold and if the timer is need to start
            // timerBoundary.getStatus() = hold


            setStatusByCrud(timerBoundary,"active"); // Update in the DB
            sendToKafka(timerBoundary,true);
        }


           System.err.println("manageTimer: " + timerBoundary);

        return Mono.just(timerBoundary)
                .map(TimerBoundary::toEntity) // Assuming you have a method to convert TimerBoundary back to TimerEntity
                .flatMap(timerCrud::save) // Save the entity to the database
                .thenReturn(timerBoundary);

    }

    public boolean checkEndTime(TimerBoundary timerBoundary)
    {
        if(timerBoundary.getRecurrence().getType() == TYPE.ONCE)
        {
            timerBoundary.getRecurrence().setEndDate(new Date());
            return true;
        }

        if(timerBoundary.getRecurrence().getEndDate() == null)
            return false;


        return timerBoundary.getRecurrence().getEndDate().before(new Date()) ||
                timerBoundary.getRecurrence().getEndDate().equals(new Date());
    }

    public void setStatusByCrud(TimerBoundary timerBoundary, String status)
    {
        timerBoundary.setStatus(status);
        this.timerCrud.save(timerBoundary.toEntity());
    }

}
