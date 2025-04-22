package Service;

import DAO.UserDao;
import Model.User;
import Util.EmailUtil;
import Util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Override
    public Boolean selectUser(String name, String pwd) {
        return dao.selectUser(name, MD5Util.toMD5(pwd));
    }

    @Override
    public Boolean insertUser(User user) {
        user.setPassword(MD5Util.toMD5(user.getPassword()));
        return dao.insertUser(user);
    }

    @Override
    public String register(String email) {
        String code = String.format("%06d", (int) (Math.random() * 1000000));
        new EmailUtil().sendVerificationCode(email, code);
        return code;
    }

    @Override
    public Boolean updateUser(User user) {
        return dao.updateUser(user);
    }

    @Override
    public Boolean deleteUser(String sno) {
        return false;
    }

    @Override
    public Boolean checkUserExist(String name) {
        return dao.checkUserExist(name);
    }

    @Override
    public User getUserByName(String name) {
        return dao.getUserByName(name);
    }

    @Override
    public void reSetPwd(String mail) {
        String newPwd = String.format("%06d", (int) (Math.random() * 1000000));
        new EmailUtil().sendResetPwd(mail, newPwd);
        System.out.println(dao.resetPassword(mail, MD5Util.toMD5(newPwd)));
    }
}