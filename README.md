# MyFramework
My Manual Framework for Java Application Development.

**So far, please edit connection to database in ```makeConnection()``` method in ```app.util.DbUtils```. I'll make it easier with XML**

I. Model Mapping
1. Database
- So far, this is a "Database First" framework, so Database must be created first.
- **Every*** table must have an "id" column and this must be auto incremental, even in intermediate tables.
- This "id" field is not neccessary to be the primary key (however it should be, except for intermediate tables in many-to-many relationships).
- Try to avoid giving your tables or columns duplicated name with keywords in SQL.
2. Model
- Best practicing is to put all of your models in package ```app.model``` package.
- Every models must ```extends``` abstract class ```Model``` from ```framework.model_mapping.model.``` package.
- Annotation ```@Table``` must be placed before declaring your ```public class```. For example:
      ```
      @Table(tableName="user")
      public class User extends Model
      ```. Please notice that ```tableName``` must match your table's name on the Database.
- Annotation ```@Column``` must be placed before declaring your field in case that field represents a column in your table. For example:
      ```
      @Column("first_name")
      private String firstName;
      ```. Please notice that value inside ```@Column``` must match your column's name on the Database.
- **DO NOT** declare the "id" field in ```model```      
- Annotation ```@ManyToOne``` must be placed before declaring your ```getter``` in case that returns another ```model``` through one-to-many relationship. This getter must return ```getManyToOne("methodName")```. For example:
      ```
      @ManyToOne(referencedTable = "role", column = "role_id")
      public Role getRole() {
        return this.manyToOne("getRole");
      
