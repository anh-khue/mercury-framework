package app.model;

import framework.annotation.model.Column;
import framework.annotation.model.ManyToOne;
import framework.annotation.model.Table;
import framework.model_mapping.model.Model;

import java.sql.Timestamp;

@Table(tableName = "learner")
public class Learner extends Model {
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("dob")
    private Timestamp dob;
    @Column("session_id")
    private int sessionId;

    private Session session;

    public Learner() {
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    @ManyToOne(referencedTable = "session", column = "session_id")
    public Session getSession() {
        return this.session == null ? (Session) this.manyToOne() : session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " | Name: " + this.getFirstName() + " " + this.getLastName() + " | DOB: " + this.dob.toString();
    }
}
