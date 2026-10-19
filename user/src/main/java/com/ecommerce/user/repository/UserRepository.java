package com.ecommerce.user.repository;

import com.ecommerce.user.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//JpaRepository provides JPA related methods such as saving, deleting, and finding entities
//CrudRepository provides CRUD functions
//Here User is the entity type and Long is the type of the entity's primary identifier
@Repository
public interface UserRepository  extends MongoRepository<User, String> {
}
