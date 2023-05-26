package ru.andreev.practice15.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Slf4j
public class SchedulerService {
    GroupsService groupsService;
    StudentsService studentsService;

    @Autowired
    public SchedulerService(GroupsService groupsService, StudentsService studentsService) {
        this.groupsService = groupsService;
        this.studentsService = studentsService;
    }

    @Transactional(readOnly = true)
    @Scheduled(cron="0 1 * * * *")
    public void doScheduledTask()throws IOException {
        File directory = new File("./src/main/resources/archives");
        FileUtils.cleanDirectory(directory);
        log.info("clean directory"+ directory);

        File groupFile = new File("./src/main/resources/archives/GroupArchive.txt");
        groupFile.createNewFile();
        FileWriter groupWriter = new FileWriter(groupFile);
        groupWriter.write(groupsService.findAll().toString());
        groupWriter.close();

        File studentFile = new File("./src/main/resources/archives/StudentArchive.txt");
        studentFile.createNewFile();
        FileWriter studentWriter = new FileWriter(studentFile);
        studentWriter.write(studentsService.findAll().toString());
        studentWriter.close();

        log.info("Reload files in directory: " + directory);
    }
}

