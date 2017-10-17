package app.model;

import framework.model_mapping.annotation.model.Column;
import framework.model_mapping.annotation.model.OneToMany;
import framework.model_mapping.annotation.model.Table;
import framework.model_mapping.model.Model;

import java.util.List;

@Table(tableName = "session")
public class Session extends Model {
    @Column("name")
    private String name;

    private List<Learner> learnerList;

    public Session() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(table = "learner", column = "session_id")
    public List<Learner> getLearnerList() {
        return learnerList == null ? (List<Learner>) this.oneToMany("getLearnerList") : learnerList;
    }

    public void setLearnerList(List<Learner> learnerList) {
        this.learnerList = learnerList;
    }
}
