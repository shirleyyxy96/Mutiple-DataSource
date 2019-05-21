package com.augmentum.mediacloud.dao.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.augmentum.mediacloud.dao.User;

public interface PostgresRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByIdAndDeletedFalse(Long id);

    @Modifying
    @Query("update user u set u.deleted='true' where u.id in ?1")
    void updateByIdIn(Long[] ids);
}
