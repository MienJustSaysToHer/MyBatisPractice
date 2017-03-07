package com.heyuhuan;

import com.heyuhuan.inter.UserOperation;
import com.heyuhuan.model.Article;
import com.heyuhuan.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * SessionFactory测试类
 *
 * @author Heyuhuan
 * @create 2017-01-15-20:26
 */
public class CrudTest {

    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    @Before
    public void getSessionFactory() {
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession() {
        return sqlSessionFactory;
    }

    @Test
    public void first() throws IOException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = session.selectOne("com.heyuhuan.inter.UserOperation.selectUserByID", 1);
            System.out.println(user.getUserAddress());
            System.out.println(user.getUserName());
        } finally {
            session.close();
        }
    }

    @Test
    public void second() throws IOException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            User user = userOperation.selectUserByID(1);
            System.out.println(user.getUserAddress());
            System.out.println(user.getUserName());
        } finally {
            session.close();
        }
    }

    @Test
    public void getUserList() throws IOException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            List<User> users = userOperation.selectUsers("%");
            System.out.println(users);
        } finally {
            session.close();
        }
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setUserAddress("人民广场");
        user.setUserName("飞鸟");
        user.setUserAge("80");
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            userOperation.addUser(user);
            session.commit();
            System.out.println("当前增加的用户 id为:" + user.getId());
        } finally {
            session.close();
        }
    }

    @Test
    public void updateUser() {
        //先得到用户,然后修改，提交。
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            User user = userOperation.selectUserByID(1);
            user.setUserAddress("原来是魔都的浦东创新园区");
            userOperation.updateUser(user);
            session.commit();
        } finally {
            session.close();
        }
    }

    @Test
    public void deleteUser() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            userOperation.deleteUser(2);
            session.commit();
        } finally {
            session.close();
        }
    }

    @Test
    public void getUserArticles() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            List<Article> articles = userOperation.getUserArticles(1);
            for (Article article : articles) {
                System.out.println(article.getTitle() + ":" + article.getContent() + ":作者是:" + article.getUser().getUserName() + ":地址:" + article.getUser().getUserAddress());
            }
        } finally {
            session.close();
        }
    }

}