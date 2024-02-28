package afeka.ac.il.timersservice.controllerAPI;

import afeka.ac.il.timersservice.logic.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RSockerCtrl {
    private TimerService timerService;

    @Autowired
    public void SetTimerService(TimerService timerService){
        this.timerService = timerService;
    }
}
