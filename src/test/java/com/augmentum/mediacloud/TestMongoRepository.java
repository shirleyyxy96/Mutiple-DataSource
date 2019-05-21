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
import org.springframework.util.Assert;

import com.augmentum.mediacloud.dao.User;
import com.augmentum.mediacloud.dao.mongo.MongodbRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TestMongoRepository {
    @Autowired
    private MongodbRepository mongodbRepository;

    private User createUser() {
        return new User("yxy", "male", new Date(), "hahah", "汉族", "asdas", false, new Date(), new Date());
    }

    @Test
    public void testSave() {
        User user = createUser();
        User result = mongodbRepository.save(user);
        Assert.notNull(result, "failed save");
        Assert.isTrue(user.getUserName().equals(result.getUserName()), "save not consistant");
    }

    @Test
    public void testFindById() {
        User user = createUser();
        User result = mongodbRepository.save(user);
        User userFind = mongodbRepository.findByOidAndDeletedFalse(result.getOid());
        System.out.println("asdfghjkl...."+userFind);
        Assert.notNull(userFind, "failed find");
    }

    @Test
    public void testGetAll() {
        Pageable pageable = new PageRequest(0, 3);
        Page<User> userPage = mongodbRepository.findByCondition("yxy", "male", "hahah", null, null, pageable);
        Assert.notNull(userPage, "page is not exist");
    }
    
    @Test
    public void testModify() {
        User user = createUser();
        User userSave = mongodbRepository.save(user);
        User userFind = mongodbRepository.findByOidAndDeletedFalse(userSave.getOid());
        userFind.setBirthDate(new Date());
        userFind.setPosition("testtesttest");
        User userModify = mongodbRepository.save(userFind);
        System.out.println("userModify..."+userModify);
        Assert.notNull(userModify, "failed save");
        Assert.isTrue(userFind.getUserName().equals(userModify.getUserName()), "save not consistant");
    }
}
