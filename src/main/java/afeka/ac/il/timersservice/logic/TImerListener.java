package afeka.ac.il.timersservice.logic;

import java.util.function.Consumer;


import afeka.ac.il.timersservice.boundaries.TimerBoundary;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
public class TImerListener {

    private Log logger = LogFactory.getLog(TImerListener.class);

    @Bean
    public Consumer<String> demoTimerSink(){
        return stringInput->{
            try {
                System.err.println("*** received: " + stringInput);
            }
            catch (Exception e) {
                this.logger.error(e);
            }
        };
    }
}
