package ru.andreev.practice15.jmx;

import ru.andreev.practice15.services.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ManagedResource(description = "Reload database archives")
public class SchedulerMBean {
    private final SchedulerService schedulerService;

    @Autowired
    public SchedulerMBean(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @ManagedOperation(description = "do reload")
    public void reloadData() throws IOException {
        schedulerService.doScheduledTask();
    }


}
