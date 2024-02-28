package afeka.ac.il.timersservice.controllerAPI;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/rsocket/timer")
public class RSocketClientTimerCtrl {

    private RSocketRequester requester;
    private  RSocketRequester.Builder builder;
}
