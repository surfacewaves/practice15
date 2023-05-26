package ru.andreev.practice15.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.practice15.models.Group;
import ru.andreev.practice15.models.Student;
import ru.andreev.practice15.repositories.StudentsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class StudentsService {
    private final StudentsRepository studentsRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    public StudentsService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public List<Student> findAll() {
        log.info("find all students");
        return studentsRepository.findAll();
    }

    public Student findOne(int id) {
        Optional<Student> student = studentsRepository.findById(id);
        log.info("find one student");
        return student.orElse(null);
    }

    @Transactional
    public void save(Student student) {
        studentsRepository.save(student);
        emailService.send("klch-o@yandex.ru", "Add Student object", "Student:\n" +
                student + "\nwas added");
        log.info("add student: {}", student);
    }

    @Transactional
    public void update(int id, Student student) {
        student.setId(id);
        studentsRepository.save(student);
        log.info("update student: {}", student);
    }

    @Transactional
    public void delete(int id) {
        studentsRepository.deleteById(id);
        log.info("delete student by id: {}", id);
    }

    public Group getStudentOwner(int id) {
        log.info("get student's owner by id: {}", id);
        return studentsRepository.findById(id).map(Student::getOwner).orElse(null);
    }

    @Transactional
    public void assign(int id, Group group) {
        log.info("assign student to group");
        studentsRepository.findById(id).ifPresent(student -> student.setOwner(group));
    }
}
