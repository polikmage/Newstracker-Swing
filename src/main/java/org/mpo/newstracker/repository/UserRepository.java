package org.mpo.newstracker.repository;

import org.mpo.newstracker.entity.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDao,Integer> {
    UserDao findByUsername(String username);
    Boolean existsByUsername(String username);
}
