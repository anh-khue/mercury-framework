# AK Framework
AK Framework for Java Application Development - created due to too much of free time.

**Configuration for connecting to Database is required to be suppplied at ```connection.xml``` file in ```resources```**

I. Data Mapping
1. Database
- Database must be already created.
- Every table must have an "id" column. ID is required be auto incremental, even in intermediate tables.
- Avoid naming tables or columns with keywords in SQL.
2. Data - Entity
- Every Entity/DTO must ```extends``` abstract class ```com.akframework.core.data.common.Entity```.
- **DO NOT** declare the "id" field in your Entity.
- Annotation ```@Table``` must be placed before declaring your Entity and ```table``` must match your table's name on the Database. For example:
```
@Table(table="user")
public class User extends Model
```
- Annotation ```@Column``` must be placed before declaring your field (in case that field represents a column in your table). 
- Value inside ```@Column``` must match your column's name on the Database. For example:
```
@Column("first_name")
private String firstName;
```
- Annotation ```@ManyToOne``` must be placed before declaring your ```getter``` if it returns another Entity through one-to-many relationship. ```referencedTable``` must match the table's name where ```column``` is referenced to. This getter must also return ```manyToOne()``` method. For example:
```
@ManyToOne(referencedTable = "role", column = "role_id")
public Role getRole() {
      return this.manyToOne();
}
```
- Annotation ```@OneToMany``` must be placed before declaring your ```getter``` if it returns a Collection of Entities through one-to-many relationship. ```table``` must match the table's name where its ```referenceColumn``` is referencing to this table'id. This getter must also return ```oneToMany()``` method. For example:
```
@OneToMany(table = "user", referenceColumn = "role_id")
public List<User> getUserList() {
      return this.oneToMany();
}
```
- Annotation ```@CombineKey```  must be placed before declaring your field in case that field represents a column in your intermediate table in many-to-many relationship. For example:
```
@Column("major_id")
@CombineKey
private int majorId;
```

II. Repository
- Every repository must ```extends``` abstract class ```com.akframework.core.repository.CrudRepository```.
- An Entity must be provided as generic class for ```CrudRepository<Entity>```. For example:
```
public class UserRepository extends CrudRepository<User> {}
```
       
 **Give it a try**      
 1. Instantiate a repository that you need.
 2. Try the following methods:
   - ```findAll()```
   - ```findById(id)``` for Basic Entity or ```findById(id1, id2,...)``` for Intermediate Model (number of parameters must equal the number of fields which is annotated with ```@CombineKey```, respectively.
   - ```save(model)```
   - ```remove(id)``` or ```remove(id1, id2,...)```
   
 ***Re-constructing packages and fixing bugs on Query Builder feature for handling complex queries***
