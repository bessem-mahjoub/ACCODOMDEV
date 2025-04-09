package accodom.dom.Service;

import accodom.dom.Entities.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User createUser(User user);
    User updateUser(User user);

    User findUserByEmail(String username);



}
