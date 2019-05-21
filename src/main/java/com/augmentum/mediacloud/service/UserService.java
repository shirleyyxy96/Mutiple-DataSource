package com.augmentum.mediacloud.service;

import org.springframework.data.domain.Page;

import com.augmentum.mediacloud.dao.User;
import com.augmentum.mediacloud.dao.UserQuery;

public interface UserService {

    /**
     * save a user to database
     * @param user
     * @return
     */
    User create(User user);

    /**
     * find all uses by specific
     * @param page
     * @param size
     * @param user
     * @return
     */
    Page<User> findAllByCondition(Integer page, Integer size, UserQuery user);

    /**
     * soft-delete users by matching user's ids
     * @param ids
     */
    void deleteUsersById(String[] ids);

    /**
     * modify the user by user's id
     * @param user
     * @return
     */
    User modifyUser(User user);

    /**
     * find the user by user's id
     * @param id
     * @return
     */
    User findById(String id);
}
