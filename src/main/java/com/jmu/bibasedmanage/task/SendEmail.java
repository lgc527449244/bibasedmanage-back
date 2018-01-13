package com.jmu.bibasedmanage.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by ljc on 2018/1/12.
 */
@Component
public class SendEmail {

    private final Logger log = LoggerFactory.getLogger(SendEmail.class);

    public void execute(){
        log.info("进来了");
    }

}
