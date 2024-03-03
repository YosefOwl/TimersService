package afeka.ac.il.timersservice.logic;

import java.util.function.Consumer;


import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
public class TImerListener {

    private ObjectMapper jackson;
    private TimerService demoService;
    private Log logger = LogFactory.getLog(TImerListener.class);

    public TImerListener(TimerService demoService) {
        this.demoService = demoService;
    }
    @PostConstruct
    public void init() {
        this.jackson = new ObjectMapper();
    }
    @Bean
    public Consumer<String> TimerSink() {
        return  null;
    }
}
