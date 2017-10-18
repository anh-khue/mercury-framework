package app.model;

import framework.annotation.model.Column;
import framework.annotation.model.OneToMany;
import framework.annotation.model.Table;
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

    @SuppressWarnings("unchecked")
    @OneToMany(table = "learner", column = "session_id")
    public List<Learner> getLearnerList() {
        return learnerList == null ? (List<Learner>) this.oneToMany() : learnerList;
    }

    public void setLearnerList(List<Learner> learnerList) {
        this.learnerList = learnerList;
    }
}
