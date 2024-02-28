package afeka.ac.il.timersservice.logic;

import afeka.ac.il.timersservice.boundaries.TimerBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TimerService {
    public Mono<TimerBoundary> createTimer(TimerBoundary timer);
    public Mono<TimerBoundary> updateTimer(TimerBoundary timer);
    public Mono<TimerBoundary> getById(String id);
    public Flux<TimerBoundary> getByDeviceId(String id);
    public Mono<Void> disableTimer(String id);
    public Mono<Void> disableDeviceTimers(String id);
    // TODO: this need to be hide from consumers
    public Mono<Void> deleteAll();
}
