package afeka.ac.il.timersservice.controllerAPI;

import afeka.ac.il.timersservice.boundaries.DeviceId;
import afeka.ac.il.timersservice.boundaries.TimerBoundary;
import afeka.ac.il.timersservice.boundaries.TimerId;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path = "/timers")
public class RSocketClientTimerCtrl {

    private RSocketRequester requester;
    private RSocketRequester.Builder requesterBuilder;
    private String rsocketHost;
    private int rsocketPort;

    @Autowired
    public void setRequesterBuilder(RSocketRequester.Builder requesterBuilder) {
        this.requesterBuilder = requesterBuilder;
    }

    @Value("${demo-app.client.rsocket.host:127.0.0.1}")
    public void setRsocketHost(String rsocketHost) {
        this.rsocketHost = rsocketHost;
    }

    @Value("${demo-app.client.rsocket.port:7007}")
    public void setRsocketPort(int rsocketPort) {
        this.rsocketPort = rsocketPort;
    }

    @PostConstruct
    public void init() {
        this.requester = this.requesterBuilder
                .tcp(rsocketHost, rsocketPort);
    }

    @PostMapping(
            path = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<TimerBoundary> createTimer(
            @RequestBody TimerBoundary timer) {
        return this.requester
                .route("create-timer-req-resp")
                .data(timer)
                .retrieveMono(TimerBoundary.class)
                .log();
    }


    @PutMapping(
            path = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<TimerBoundary> updateTimer(
            @RequestBody TimerBoundary timer) {

        return this.requester
                .route("update-timer-req-resp")
                .data(timer)
                .retrieveMono(TimerBoundary.class)
                .log();
    }
    @GetMapping(
            path = {"/timerId={timerId}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<TimerBoundary> getTimerById(
            @PathVariable("timerId") String timerId) {

        return this.requester
                .route("get-timer-by-id-req-resp")
                .data(new TimerId().setId(timerId))
                .retrieveMono(TimerBoundary.class)
                .log();
    }

    @GetMapping(
            path = {"/deviceId={deviceId}"},
            produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<TimerBoundary> getTimerByDeviceId(
            @PathVariable("deviceId") String deviceId) {

        return this.requester
                .route("get-timers-by-device-id-req-stream")
                .data(new DeviceId().setId(deviceId))
                .retrieveFlux(TimerBoundary.class)
                .log();
    }


    @DeleteMapping(path = {"cancel/timerId={timerId}"})
    public Mono<Void> cancelTimerById(
            @PathVariable("timerId") String timerId) {
        return this.requester
                .route("disable-timer-by-id-fnf")
                .data(new TimerId().setId(timerId))
                .send()
                .log();
    }

    @DeleteMapping(path = {"cancel/deviceId={deviceId}"})
    public Mono<Void> cancelTimerByDeviceId(
            @PathVariable("deviceId") String deviceId) {
        return this.requester
                .route("disable-timers-by-device-id-fnf")
                .data(new DeviceId().setId(deviceId))
                .send()
                .log();
    }


    @DeleteMapping(path = {"delete-all"})
    public Mono<Void> cleanup() {
        return this.requester
                .route("delete-all-timers-fnf")
                .send()
                .log();
    }



}
