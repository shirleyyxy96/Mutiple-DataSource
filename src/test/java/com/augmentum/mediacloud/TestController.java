package com.augmentum.mediacloud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSONObject;
import com.augmentum.mediacloud.dao.User;
import com.augmentum.mediacloud.dao.UserQuery;
import com.augmentum.mediacloud.resource.UserController;
import com.augmentum.mediacloud.service.UserService;

/**
 * 
 * This is a test class for controller
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestController {
    @MockBean
    private UserService userService;

    @Autowired
    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private User user;

    @Before
    public void beforeAll() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private User createUser() {
        user = new User("yxy", "male", new Date(), "hahah", "汉族", "asdas", false, new Date(), new Date());
        return user;
    }

    @Test
    public void testSave() throws Exception {
        User user = createUser();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user))).andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    /**
     * before test this method you need to change @Value("{spring.size}") into a
     * constant
     * 
     * @throws Exception
     */
    @Test
    public void testGetUsers() throws Exception {
        UserQuery userQuery = new UserQuery();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/search?page=0&size=3").contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(userQuery)).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testDeletUsers() throws Exception {
        List<String> ids = new ArrayList<String>();
        ids.add("1");
        ids.add("2");
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete?ids=" + ids)).andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void modifyUser() throws Exception {
        User user = createUser();
        user.setId(1l);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/update").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user))).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
