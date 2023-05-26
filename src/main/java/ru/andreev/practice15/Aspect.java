package ru.andreev.practice15;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    StopWatch stopWatch;

    @Before("allServiceMethods()")
    public void logStartTime(JoinPoint joinPoint) {
        stopWatch = new StopWatch();
        stopWatch.start();
        log.info("the method is now executed: {} ", joinPoint.getSignature());
    }

    @After("allServiceMethods()")
    public void logTime(JoinPoint joinPoint) {
        stopWatch.stop();
        log.info("the method {} ended with time: {}s", joinPoint.getSignature(), stopWatch.getTotalTimeSeconds());
    }

    @Pointcut("within(ru.andreev.practice15.services.GroupsService)" + "within(ru.andreev.practice15.services.StudentsService)")
    public void allServiceMethods() {
    }
}
