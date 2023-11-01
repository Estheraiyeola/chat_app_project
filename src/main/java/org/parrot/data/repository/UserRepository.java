package org.parrot.data.repository;

import org.parrot.data.model.User;
import org.parrot.dto.LoginRequest;
import org.parrot.dto.LoginResponse;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String>{
    LoginResponse findDistinctByEmailAndPassword(String email, String password);

}
