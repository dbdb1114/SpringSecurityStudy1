package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * CRUD 함수를 JPARepository가 들고잇음.
 * @Repository라는 어노테이션이 없어도 ioc 가능. 이유는 JpaRepository를 상속했기 때문에.
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * findBy규칙 -> Username 문법
     * select * from user where username = ? 이 쿼리가 호출됨
     *
     * */
    public User findByUsername(String username); // JPA Query Method
}
