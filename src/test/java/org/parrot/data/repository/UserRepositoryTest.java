package org.parrot.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parrot.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;

@DataMongoTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
    }

    @Test
    public void testThatRepositoryCan_SaveUsers(){
        User user = new User();
        user.setEmail("esther");
        user.setPassword("password");
        userRepository.save(user);

        assertThat(userRepository.count(), is(1L));
    }


}