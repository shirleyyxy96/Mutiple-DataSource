package com.augmentum.mediacloud.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.augmentum.mediacloud.constants.GenderConstants;
import com.augmentum.mediacloud.dao.User;
import com.augmentum.mediacloud.dao.UserQuery;
import com.augmentum.mediacloud.dao.mongo.MongodbRepository;
import com.augmentum.mediacloud.service.UserService;
import com.augmentum.mediacloud.utils.DateUtils;
import com.augmentum.mediacloud.utils.EmptyUtils;

@Service
@Profile("mongo")
public class MongoServiceimpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(MongoServiceimpl.class);

    @Autowired
    private MongodbRepository mongodbRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User create(User user) {
        user.setCreateDate(new Date());
        user.setUpdateDate(user.getCreateDate());
        User saveUser = mongodbRepository.save(user);
        if (saveUser == null) {
            LOG.error("save user failed");
            throw new RuntimeException("save user failed");
        }

        return saveUser;
    }

    @Override
    public Page<User> findAllByCondition(Integer page, Integer size, UserQuery userQuery) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, User.UPDATE_DATE);
        if (EmptyUtils.isEmpty(userQuery.getUserName())) {
            userQuery.setUserName(".");
        } else {
            userQuery.setUserName(userQuery.getUserName().toLowerCase());
        }

        if (userQuery.getGender().equals(GenderConstants.UNLIMITED)) {
            userQuery.setGender(".");
        } else {
            userQuery.setGender("^" + userQuery.getGender());
        }

        if (EmptyUtils.isEmpty(userQuery.getPosition())) {
            userQuery.setPosition(".");
        } else {
            userQuery.setPosition(userQuery.getPosition().toLowerCase());
        }

        if (EmptyUtils.isEmpty(userQuery.getStartDate())) {
            userQuery.setStartDate(DateUtils.mixDate());
        }

        if (EmptyUtils.isEmpty(userQuery.getEndDate())) {
            userQuery.setEndDate(DateUtils.maxDate());
        }

        Page<User> datas = mongodbRepository.findByCondition(userQuery.getUserName(), userQuery.getGender(),
                userQuery.getPosition(), userQuery.getStartDate(), userQuery.getEndDate(), pageable);
        if (datas == null) {
            LOG.error("find user's information failed");
            throw new RuntimeException("find user's information failed");
        }

        return datas;
    }

    @Override
    public void deleteUsersById(String[] ids) {
        ObjectId[] objectIds = new ObjectId[ids.length];
        for (int i = 0; i < ids.length; i++) {
            objectIds[i] = new ObjectId(ids[i]);
        }

        List<ObjectId> idList = Arrays.asList(objectIds);
        mongoTemplate.updateMulti(new Query(Criteria.where(User.O_ID).in(idList)), new Update().set(User.DELETED, true),
                User.class);
    }

    @Override
    public User findById(String id) {
        User user = mongodbRepository.findByOidAndDeletedFalse(new ObjectId(id));
        if (user == null) {
            LOG.error("the user does not exists");
            throw new RuntimeException("the user does not exists");
        }

        return user;
    }

    @Override
    public User modifyUser(User user) {
        User newUser = null;
        User userFind = mongodbRepository.findByOidAndDeletedFalse(user.getOid());
        if (userFind == null) {
            LOG.error("the user does not exists");
            throw new RuntimeException("the user does not exists");
        } else {
            user.setUpdateDate(new Date());
            newUser = mongodbRepository.save(user);
        }

        return newUser;
    }
}
