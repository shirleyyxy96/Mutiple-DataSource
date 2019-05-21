package com.augmentum.mediacloud.dao.mongo;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.augmentum.mediacloud.dao.User;

public interface MongodbRepository extends MongoRepository<User, ObjectId> {

    User findByOidAndDeletedFalse(ObjectId oid);

    @Query("{userName: {$regex: ?0}, gender: {$regex: ?1}, position: {$regex: ?2}, birthDate: {$gte: ?3 , $lte: ?4}, deleted: false }")
    Page<User> findByCondition(String username, String gender, String position, Date startDate, Date endDate,
            Pageable pageable);
}