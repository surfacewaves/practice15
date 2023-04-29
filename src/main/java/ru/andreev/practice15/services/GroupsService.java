package ru.andreev.practice15.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.practice15.models.Group;
import ru.andreev.practice15.repositories.GroupsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupsService {
    private final GroupsRepository groupsRepository;

    @Autowired
    public GroupsService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    public List<Group> findAll() {
        return groupsRepository.findAll();
    }

    public Group findOne(int id) {
        Optional<Group> group = groupsRepository.findById(id);
        return group.orElse(null);
    }

    @Transactional
    public void save(Group group) {
        groupsRepository.save(group);
    }

    @Transactional
    public void update(int id, Group group) {
        group.setId(id);
        groupsRepository.save(group);
    }

    @Transactional
    public void delete(int id) {
        groupsRepository.deleteById(id);
    }
}
