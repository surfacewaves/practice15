package ru.andreev.practice15.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.practice15.models.Student;
import ru.andreev.practice15.repositories.StudentsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentsService {
    private final StudentsRepository studentsRepository;

    @Autowired
    public StudentsService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public List<Student> findAll() {
        return studentsRepository.findAll();
    }

    public Student findOne(int id) {
        Optional<Student> student = studentsRepository.findById(id);
        return student.orElse(null);
    }

    @Transactional
    public void save(Student student) {
        studentsRepository.save(student);
    }

    @Transactional
    public void update(int id, Student student) {
        student.setId(id);
        studentsRepository.save(student);
    }

    @Transactional
    public void delete(int id) {
        studentsRepository.deleteById(id);
    }
}
