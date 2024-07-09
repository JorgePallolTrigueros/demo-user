package com.shoppingcart.user.dao.repository;

import com.shoppingcart.user.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <UserEntity ,String>{

}
