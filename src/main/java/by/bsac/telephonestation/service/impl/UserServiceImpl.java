package by.bsac.telephonestation.service.impl;

import by.bsac.telephonestation.dao.UserDAO;
import by.bsac.telephonestation.domain.User;
import by.bsac.telephonestation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    public User findEntityById(Long id) {
        return userDAO.findEntityById(id);
    }

    @Override
    public boolean remove(Long id) {
        return userDAO.remove(id);
    }

    @Override
    public boolean create(User user) {
        log.debug("enter create method!");
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userDAO.create(user);
    }

    @Override
    public boolean update(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userDAO.update(user);
    }
}
