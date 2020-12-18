package com.ge.processes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkerController {

    private static final int WORK_DURATION_MS = 3000;

    Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @GetMapping("/work")
    @ResponseStatus(HttpStatus.OK)
    public void doSomeWork() {
        long threadId = Thread.currentThread().getId();
        logger.info("Now doing some work for {}ms - thread id: {}", WORK_DURATION_MS, threadId);
        try {
            Thread.sleep(WORK_DURATION_MS);
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
        logger.info("Work in thread {} is done.", threadId);
    }

}


