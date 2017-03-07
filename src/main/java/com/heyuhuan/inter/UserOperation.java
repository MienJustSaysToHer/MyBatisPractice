package com.heyuhuan.inter;

import com.heyuhuan.model.Article;
import com.heyuhuan.model.User;

import java.util.List;

public interface UserOperation {

    User selectUserByID(int id);

    List<User> selectUsers(String userName);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    List<Article> getUserArticles(int id);

}