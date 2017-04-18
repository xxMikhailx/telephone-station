package by.bsac.telephonestation.service;

import by.bsac.telephonestation.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findEntityById(Long id);

    boolean remove(Long id);

    boolean create(User user);

    boolean update(User user);

}
