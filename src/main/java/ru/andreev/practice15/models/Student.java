package ru.andreev.practice15.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Student")
@Getter
@Setter
public class Student {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @ManyToOne
    //название колонки в student / group_id ссылается на id в родителе-group
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Group owner;

    public Student() {
    }

    public Student(int id, String lastName, String firstName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", owner=" + owner +
                '}';
    }
}
