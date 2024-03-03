package afeka.ac.il.timersservice.controllerAPI;

import afeka.ac.il.timersservice.boundaries.DeviceId;
import afeka.ac.il.timersservice.boundaries.TimerBoundary;
import afeka.ac.il.timersservice.boundaries.TimerId;
import afeka.ac.il.timersservice.logic.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class RSocketCtrl {
    private TimerService timerService;

    @Autowired
    public void SetTimerService(TimerService timerService){
        this.timerService = timerService;
    }

    @MessageMapping("create-timer-req-resp")
    public Mono<TimerBoundary> createTimer(@Payload TimerBoundary timer) {
        return timerService
                .createTimer(timer)
                .log();
    }


    // TODO: shall be Mono<Void> and fnf operation and change the rest API also to PUT.
    // also need to update the timers spec
    @MessageMapping("update-timer-req-resp")
    public Mono<TimerBoundary> updateTimer(@Payload TimerBoundary timer) {
        return timerService
                .updateTimer(timer)
                .log();
    }

    @MessageMapping("get-timer-by-id-req-resp")
    public Mono<TimerBoundary> getTimerById(@Payload TimerId timerId) {

        return timerService
                .getById(timerId.getId())
                .log();
    }

    @MessageMapping("get-timers-by-device-id-req-stream")
    public Flux<TimerBoundary> getTimerByDeviceId(@Payload DeviceId deviceId) {

        return timerService
                .getByDeviceId(deviceId.getId())
                .log();
    }

    @MessageMapping("disable-timer-by-id-fnf")
    public Mono<Void> disableTimerById(@Payload TimerId timerId) {

        return timerService
                .disableTimer(timerId.getId())
                .log();
    }

    @MessageMapping("disable-timers-by-device-id-fnf")
    public Mono<Void> disableTimerByDeviceId(@Payload DeviceId deviceId) {

        return timerService
                .disableDeviceTimers(deviceId.getId())
                .log();
    }

    @MessageMapping("delete-all-timers-fnf")
    public Mono<Void> deleteAll() {

        return timerService
                .deleteAll()
                .log();
    }

}
