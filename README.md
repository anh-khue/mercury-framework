# MyFramework
My Manual Framework for Java Application Development.

I. Model Mapping
1. Database
- So far, this is a "Database First" framework, so Database must be created first.
- Every table must have an "id" column and this must be auto incremental.
- This "id" field is not neccessary to be the primary key (however it should be, except for intermediate tables in many-to-many relationships).
- Try to avoid giving your tables or columns duplicated name with keywords in SQL.
2. Model
- Best practicing is to put all of your models in package ```app.model``` package.
- Every models must ```extends``` abstract class ```Model``` from ```framework.model_mapping.model.``` package.
- Annotation ```@Table``` must be placed before naming your ```public class```. For example:
      ```
      @Table(tableName="user")
      public class User extends Model
      ```. Please notice that ```tableName``` must match your table's name on the Database.
- Annotation ```@Column``` must be placed before naming your field in case that field represents a column in your table. For example:
      ```
      @Column("first_name")
      private String firstName;
      ```. Please notice that value inside ```@Column``` must match your column's name on the Database.
- Annotation ```@ManyToOne``` must be placed before naming your ```getter``` in case that returns another ```model``` through one-to-many relationship. This getter must return ```getManyToOne("methodName")```. For example:
      ```
      @ManyToOne(referencedTable = "role", column = "role_id")
      public Role getRole() {
        return this.manyToOne("getRole");
      }
      ```. Please notice that ```referencedTable``` must match the table's name where ```column``` is referenced to.
- Annotation ```@OneToMany``` must be placed before naming your ```getter``` in case that returns  ```List<model>``` through one-to-many relationship. This getter must return ```getOneToMany("methodName")```. For example:
      ```
      @OneToMany(table = "user", column = "role_id")
      public List<User> getUserList() {
        return this.oneToMany("getUserList");
      }
      ```. Please notice that ```table``` must match the table's name where its ```column``` is referencing to this table'id.
- Annotation ```@CombineKey```  must be placed before naming your field in case that field represents a column in your intermediate table in many-to-many relationship. For example:
      ```
      @Column("major_id")
      @CombineKey
      private int majorId;
      ```.      
3. Repository
- Best practicing is to put all of your repository in package ```app.repository``` package.
- Every repository must ```extends``` abstract class ```CrudRepository``` from ```framework.model_mapping.repository.``` package.
- You must also provide ```model``` as generic class for ```CrudRepository```. For example:
      ```
      public class UserRepository extends CrudRepository<User>
      ```
**Try the magic**      
1. Instantiate a repository that you need.
2. Try the following methods:
  - ```getAll()```
  - ```findById(id)``` for Basic Model or ```findById(id1, id2)``` for Intermediate Model
  - ```save(model)```
  - ```remove(model```
***That's it! You may think that I'm kidding but that is everything you need to do. Not even a single query***
I'll keep updating this framework so that you can dynamically create your own query easily.  
