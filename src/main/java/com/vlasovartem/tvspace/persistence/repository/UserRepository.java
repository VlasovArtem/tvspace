package com.vlasovartem.tvspace.persistence.repository;

import com.vlasovartem.tvspace.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by artemvlasov on 07/12/15.
 */
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{$and : [{$or : [{email : ?0}, {login : ?9}]}, {finished : false}]}")
    User loginUser (String loginData);
}