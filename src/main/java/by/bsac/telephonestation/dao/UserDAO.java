package by.bsac.telephonestation.dao;

import by.bsac.telephonestation.domain.User;

import java.util.List;

public interface UserDAO extends GenericDAO<Long,User> {

    List<User> findAllUsers();

    @Override
    User findEntityById(Long id);

    @Override
    boolean remove(Long id);

    @Override
    boolean create(User user);

    @Override
    boolean update(User user);
}
