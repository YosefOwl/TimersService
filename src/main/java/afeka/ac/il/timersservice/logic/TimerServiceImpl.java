package afeka.ac.il.timersservice.logic;


import afeka.ac.il.timersservice.boundaries.TimerBoundary;
import afeka.ac.il.timersservice.dataAccess.TimerCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TimerServiceImpl implements TimerService{
    private TimerCrud timerCrud;

    @Autowired
    public void SetUpCrud(TimerCrud timerCrud){
        this.timerCrud = timerCrud;
    }


    @Override
    public Mono<TimerBoundary> createTimer(TimerBoundary timer) {
        return null;
    }

    @Override
    public Mono<TimerBoundary> updateTimer(TimerBoundary timer) {
        return null;
    }

    @Override
    public Mono<TimerBoundary> getById(String id) {
        return null;
    }

    @Override
    public Flux<TimerBoundary> getByDeviceId(String id) {
        return null;
    }

    @Override
    public Mono<Void> disableTimer(String id) {
        return null;
    }

    @Override
    public Mono<Void> disableDeviceTimers(String id) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return this.timerCrud
                .deleteAll();
    }
}
