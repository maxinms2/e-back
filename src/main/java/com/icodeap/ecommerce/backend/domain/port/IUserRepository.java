package com.icodeap.ecommerce.backend.domain.port;

import java.util.List;

import com.icodeap.ecommerce.backend.domain.model.User;

public interface IUserRepository {
    User save(User user);
    User findByEmail(String email);
    User findById(Integer id);
    List<User> findByIds(List<Integer> id);
}
