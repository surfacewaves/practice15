package ru.andreev.practice15.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andreev.practice15.models.Group;

@Repository
public interface GroupsRepository extends JpaRepository<Group, Integer> {
}
