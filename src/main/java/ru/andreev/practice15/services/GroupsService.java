package ru.andreev.practice15.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.practice15.models.Group;
import ru.andreev.practice15.models.Student;
import ru.andreev.practice15.repositories.GroupsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class GroupsService {
    private final GroupsRepository groupsRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    public GroupsService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    public List<Group> findAll() {
        log.info("find all groups");
        return groupsRepository.findAll();
    }

    public Group findOne(int id) {
        Optional<Group> group = groupsRepository.findById(id);
        log.info("find one group");
        return group.orElse(null);
    }

    @Transactional
    public void save(Group group) {
        groupsRepository.save(group);
        emailService.send("klch-o@yandex.ru", "Add Group object", "Group:\n" +
                group + "\nwas added");
        log.info("add group: {}", group);
    }

    @Transactional
    public void update(int id, Group group) {
        group.setId(id);
        groupsRepository.save(group);
        log.info("update group: {}", group);
    }

    @Transactional
    public void delete(int id) {
        groupsRepository.deleteById(id);
        log.info("delete group by id: {}", id);
    }

    public List<Student> getStudentsByGroupId(int id) {
        Optional<Group> group = groupsRepository.findById(id);
        log.info("students by group's id: {}", id);
        if (group.isPresent()) {
            Hibernate.initialize(group.get().getStudents());
            return group.get().getStudents();
        }
        else {
            return Collections.emptyList();
        }
    }
}
