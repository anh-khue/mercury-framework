package app.model;

import framework.annotation.model.Column;
import framework.annotation.model.CombineKey;
import framework.annotation.model.ManyToOne;
import framework.annotation.model.Table;
import framework.model_mapping.model.Model;

@Table(tableName = "lecturer_teach_subject")
public class LecturerTeachSubject extends Model {
    @Column("lecturer_id")
    @CombineKey
    private int lecturerId;
    @Column("subject_id")
    @CombineKey
    private int subjectId;

    private Lecturer lecturer;
    private Subject subject;

    public LecturerTeachSubject() {
    }

    @ManyToOne(column = "lecturer_id", referencedTable = "lecturer")
    public Lecturer getLecturer() {
        return lecturer == null ? (Lecturer) manyToOne() : lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
        this.lecturerId = lecturer.getId();
    }

    @ManyToOne(column = "subject_id", referencedTable = "subject")
    public Subject getSubject() {
        return subject == null ? (Subject) manyToOne() : subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subjectId = subject.getId();
    }
}
