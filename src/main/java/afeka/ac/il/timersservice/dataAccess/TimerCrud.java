package afeka.ac.il.timersservice.dataAccess;

import afeka.ac.il.timersservice.data.TimerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TimerCrud extends ReactiveMongoRepository<TimerEntity, String> {

    //@Query("{'externalReferences.deviceId': ?0}")
    public Flux<TimerEntity> findAllByDeviceId(String deviceId);
}
