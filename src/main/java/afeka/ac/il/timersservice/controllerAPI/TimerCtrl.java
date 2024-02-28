package afeka.ac.il.timersservice.controllerAPI;


import afeka.ac.il.timersservice.logic.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/timer")
public class TimerCtrl {
    private TimerService timerService;

    @Autowired
    public void SetTimerService(TimerService timerService){
        this.timerService = timerService;
    }
}
