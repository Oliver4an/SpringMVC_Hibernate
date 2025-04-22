package Service;

import Model.User;

public interface UserService {

    // sign up
    Boolean insertUser(User User);

    String register(String email);

    Boolean checkUserExist(String name);

    // logIn
    Boolean selectUser(String name, String pwd);

    Boolean updateUser(User User);

    Boolean deleteUser(String sno);

    User getUserByName(String name);

    void reSetPwd(String mail);

}
