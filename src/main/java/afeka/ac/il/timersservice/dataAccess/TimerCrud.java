package afeka.ac.il.timersservice.dataAccess;

import afeka.ac.il.timersservice.data.TimerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TimerCrud extends ReactiveMongoRepository<TimerEntity, String> {
}
