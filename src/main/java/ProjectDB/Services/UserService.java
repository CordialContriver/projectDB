package ProjectDB.Services;

import ProjectDB.Repositories.UserRepository;
import ProjectDB.Tables.User;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    public UserService() { userRepository = new UserRepository(); }

    public void createNewUser(User user){ userRepository.createUser(user); }

    public List<User> getUserList() {   return userRepository.getUserList();    }

    public User getUserByUsername(String nameLogin) {
        return userRepository.getUserByUsername(nameLogin);
//        List<User> users = getUserList();
//        return users.stream().filter(user -> user.getUsername().equals(nameLogin)).findFirst().get();
    }

    public void updateUser(User user) { userRepository.updateUser(user);    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public int getAdminCount() {
        return userRepository.getAdminCount();
    }
}