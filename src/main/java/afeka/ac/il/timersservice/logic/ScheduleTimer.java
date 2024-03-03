package afeka.ac.il.timersservice.logic;

import afeka.ac.il.timersservice.boundaries.TimerBoundary;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class ScheduleTimer {

    private List<TimerBoundary> timerBoundaries;
    private ObjectMapper jackson;
    private final StreamBridge kafka;

    private String targetTopic;

    public  ScheduleTimer(StreamBridge kafka){
        this.kafka = kafka;
        this.timerBoundaries  = new ArrayList<>();
    }

    @PostConstruct
    public void init(){
        this.jackson = new ObjectMapper();
    }

    @Value("$target.topic.name:anyTopic")
    public void setTargetTopic(String targetTopic){ this.targetTopic = targetTopic; }


    // This method will be executed every second
    @Scheduled(cron = "* * * * * *") // Cron expression for every second
    public void checkTimerBoundary() {

        if (timerBoundaries.isEmpty()){
            System.err.println("Empty list");
            return;
        }

        Date now = new Date();

        TimerBoundary timer = timerBoundaries.get(0);

        if (now.after(timer.getStartTime())) {
            // TO DO
            System.err.println("ON: " + timer);


            sendToKafka(timer);

            timerBoundaries.remove(timer);
        }
    }

    public void addTimerBoundary(TimerBoundary timerBoundary) {
        timerBoundaries.add(timerBoundary);
        Collections.sort(timerBoundaries);
        //for (TimerBoundary t : timerBoundaries) {
        //    System.err.println(t);
        //}
    }
    public void removeTimerBoundaryById(String timerId) {
        Iterator<TimerBoundary> iterator = timerBoundaries.iterator();

        while (iterator.hasNext()) {
            TimerBoundary currentTimerBoundary = iterator.next();
            if (currentTimerBoundary.getTimerId().equals(timerId)) {
                iterator.remove();
                break;
            }
        }
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

}
