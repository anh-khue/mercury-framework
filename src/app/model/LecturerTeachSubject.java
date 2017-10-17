package app.model;

import framework.model_mapping.annotation.model.Column;
import framework.model_mapping.annotation.model.CombineKey;
import framework.model_mapping.annotation.model.ManyToOne;
import framework.model_mapping.annotation.model.Table;
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
        return lecturer == null ? (Lecturer) manyToOne("getLecturer") : lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
        this.lecturerId = lecturer.getId();
    }

    @ManyToOne(column = "subject_id", referencedTable = "subject")
    public Subject getSubject() {
        return subject == null ? (Subject) manyToOne("getSubject") : subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subjectId = subject.getId();
    }
}
