package com.augmentum.mediacloud;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.augmentum.mediacloud.dao.User;
import com.augmentum.mediacloud.dao.postgres.PostgresRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TestPostgreRepository {

    @Autowired
    private PostgresRepository postgresRepository;

    private User createUser() {
        return new User("yxy", "male", new Date(), "hahah", "汉族", "asdas", false, new Date(), new Date());
    }

    @Test
    @Transactional
    public void testSave() {
        User user = createUser();
        User result = postgresRepository.save(user);
        Assert.notNull(result, "failed save");
        Assert.isTrue(user.getUserName().equals(result.getUserName()), "save not consistant");
    }

    @Test
    @Transactional
    public void testModify() {
        User user = createUser();
        User userSave = postgresRepository.save(user);
        User userFind = postgresRepository.findByIdAndDeletedFalse(userSave.getId());
        userFind.setGender("female");
        userFind.setPosition("testetsettest");
        User userModify = postgresRepository.save(userFind);
        Assert.notNull(userModify, "failed save");
        Assert.isTrue(userFind.getUserName().equals(userModify.getUserName()), "save not consistant");
    }

    @Test
    @Transactional
    public void testFindById() {
        User user = createUser();
        User userSave = postgresRepository.save(user);
        User result = postgresRepository.findByIdAndDeletedFalse(userSave.getId());
        Assert.notNull(result, "failed find");
    }

    @Test
    @Transactional
    public void testFindByDeleteFalse() {
        Pageable pageable = new PageRequest(0, 3);
        Page<User> userPage = postgresRepository.findAll(pageable);
        Assert.notNull(userPage, "page is not exist");
    }

}
