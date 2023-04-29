package ru.andreev.practice15.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andreev.practice15.models.Student;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Integer> {
}
