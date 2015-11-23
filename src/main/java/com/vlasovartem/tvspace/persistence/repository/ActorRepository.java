package com.vlasovartem.tvspace.persistence.repository;

import com.vlasovartem.tvspace.entity.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by artemvlasov on 20/11/15.
 */
public interface ActorRepository extends MongoRepository<Actor, String> {
}
