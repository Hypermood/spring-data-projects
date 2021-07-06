package course.springdata.gamestore.services.interfaces;

import course.springdata.gamestore.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User persistUser(User user);
    void registerUser(String email,String password,String repeatPass,String fullName);
    User loginUser(User currentUser, String email, String password);
    User logout(User currentUser);
    User buyGame(User currentUser,String title);
    User addItem(User currentUser,String title);
    User removeItem(User currentUser,String title);
    User buyAllItems(User currentUser);

}
