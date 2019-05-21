package com.augmentum.mediacloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.augmentum.mediacloud.constants.GenderConstants;
import com.augmentum.mediacloud.dao.User;
import com.augmentum.mediacloud.dao.UserQuery;
import com.augmentum.mediacloud.dao.postgres.PostgresRepository;
import com.augmentum.mediacloud.service.UserService;
import com.augmentum.mediacloud.utils.EmptyUtils;

@Service
@Profile("postgres")
public class PostgresServiceimpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(PostgresServiceimpl.class);

    @Autowired
    private PostgresRepository postgresRepository;

    @Override
    public User create(User user) throws RuntimeException {
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        User save = postgresRepository.save(user);
        if (save == null) {
            LOG.error("save user failed");
            throw new RuntimeException("save user failed");
        }

        return save;
    }

    @Override
    public Page<User> findAllByCondition(Integer page, Integer size, UserQuery userQuery) throws RuntimeException {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, User.UPDATE_DATE);
        Page<User> userPage = (Page<User>) postgresRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (EmptyUtils.isNotEmpty(userQuery.getUserName())) {
                    list.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(User.USER_NAME).as(String.class)),
                            ("%" + userQuery.getUserName() + "%").toUpperCase()));
                }

                if (EmptyUtils.isNotEmpty(userQuery.getGender())
                        && !userQuery.getGender().equals(GenderConstants.UNLIMITED)) {
                    list.add(criteriaBuilder.equal(root.get(User.GENDER).as(String.class), userQuery.getGender()));
                }

                if (EmptyUtils.isNotEmpty(userQuery.getPosition())) {
                    list.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(User.POSITION).as(String.class)),
                            ("%" + userQuery.getPosition() + "%").toUpperCase()));
                }

                if (EmptyUtils.isNotEmpty(userQuery.getStartDate())) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get(User.BIRTH_DATE).as(Date.class),
                            userQuery.getStartDate()));
                }

                if (EmptyUtils.isNotEmpty(userQuery.getEndDate())) {
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get(User.BIRTH_DATE).as(Date.class),
                            userQuery.getEndDate()));
                }

                list.add(criteriaBuilder.equal(root.get(User.DELETED).as(boolean.class), false));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        if (userPage == null) {
            LOG.error("find user's information failed");
            throw new RuntimeException("find user's information failed");
        }

        return userPage;
    }

    @Transactional
    @Override
    public void deleteUsersById(String[] ids) {
        Long[] longIds = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            longIds[i] = Long.parseLong(ids[i]);
        }

        postgresRepository.updateByIdIn(longIds);
    }

    @Override
    public User findById(String id) throws RuntimeException {
        User user = postgresRepository.findByIdAndDeletedFalse(Long.parseLong(id));
        if (user == null) {
            LOG.error("the user does not exist");
            throw new RuntimeException("the user does not exist");
        }

        return user;
    }

    @Override
    public User modifyUser(User user) throws RuntimeException {
        User newUser = null;
        User userFind = postgresRepository.findByIdAndDeletedFalse(user.getId());
        if (userFind == null) {
            LOG.error("the user does not exist");
            throw new RuntimeException("the user does not exist");
        } else {
            user.setUpdateDate(new Date());
            newUser = postgresRepository.save(user);
        }

        return newUser;
    }
}