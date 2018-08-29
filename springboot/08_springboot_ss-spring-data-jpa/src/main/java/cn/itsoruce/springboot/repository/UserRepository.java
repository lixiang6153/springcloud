package cn.itsoruce.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itsoruce.springboot.domian.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
