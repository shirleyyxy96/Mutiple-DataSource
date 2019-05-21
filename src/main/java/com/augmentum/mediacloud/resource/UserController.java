package com.augmentum.mediacloud.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.augmentum.mediacloud.dao.User;
import com.augmentum.mediacloud.dao.UserQuery;
import com.augmentum.mediacloud.exception.BusinessException;
import com.augmentum.mediacloud.service.UserService;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * find conditional user's information by paging
     * @params page size userQuery
     * @return Page<User>
     */
    @GetMapping("/find")
    public Page<User> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size, UserQuery userQuery) {
        Page<User> datas = null;
        datas = userService.findAllByCondition(page, size, userQuery);
        return datas;
    }

    /**
     * save user's information to database
     * @param user
     * @return User
     */
    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.create(user);
    }

    /**
     * delete mutiple users
     * @param ids
     * @throws BusinessException
     */
    @DeleteMapping("/delete")
    public void deleteUsers(@RequestParam String[] ids) throws BusinessException {
        userService.deleteUsersById(ids);
    }

    /**
     * update user's information to database
     * @param user
     * @return User
     */
    @PutMapping("/update")
    public void modifyUser(@RequestBody User user) {
        userService.modifyUser(user);
    }

    /**
     * find a user by id
     * @param id
     * @return user
     */
    @GetMapping("/{id}")
    public User findUserById(@PathVariable String id) {
        User user = userService.findById(id);
        return user;
    }

}
