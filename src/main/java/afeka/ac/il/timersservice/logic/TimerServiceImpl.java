package afeka.ac.il.timersservice.logic;


import afeka.ac.il.timersservice.dataAccess.TimerCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimerServiceImpl implements TimerService{
    private TimerCrud timerCrud;

    @Autowired
    public void SetUpCrud(TimerCrud timerCrud){
        this.timerCrud = timerCrud;
    }


}
