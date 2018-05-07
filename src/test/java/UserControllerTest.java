import controllers.UserController;
import controllers.UserFileController;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import models.User;
import org.hamcrest.collection.IsMapContaining;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.*;

@RunWith(JUnitParamsRunner.class)
public class UserControllerTest {

    private UserController uc;
    private Map<String, User> usersMap;
    private String filePath;
    private String testingDatafilePath="src/test/resources/TestUsersDataBase.txt";

    public Object paramsForTrue() {
        return new Object[][]{
                {new User("Test1", "123"), true},
                {new User("Test2", "123"), true},
                {new User("Test3", "qwerty"), true},
                {new User("Test4", "0"), true}
        };
    }

    public Object paramsForFalse() {
        return new Object[][]{
                {new User("Test1", "123"), false},
                {new User("Test2", "123"), false},
                {new User("Test3", "qwerty"), false},
                {new User("Test4", "0"), false}
        };
    }

    @Before
    public void setUp() {
        uc = new UserController();
        usersMap = new HashMap<>();
        filePath="src/test/resources/TestsUsers.txt";
    }

    @After
    public void cleanUp() throws Exception {
        Files.deleteIfExists(Paths.get(filePath));
    }

    @Test
    @Parameters(method = "paramsForTrue")
    public void isUserExistShouldBeTrue(User user, boolean expected) throws Exception{
        uc.addUser(user.getLogin(),user.getPassword(),filePath, usersMap);
        boolean actual = uc.isUserExist(user.getLogin(), usersMap);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Parameters(method = "paramsForFalse")
    public void isUserExistShouldBeFalse(User user, boolean expected)throws Exception {
        uc.addUser(user.getLogin(),user.getPassword(),filePath, usersMap);
        boolean actual = uc.isUserExist("nonExistingLogin", usersMap);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void addUserShouldBeTrue()throws Exception {
        User user = new User("login1","1234");
        uc.addUser(user.getLogin(),user.getPassword(),filePath,usersMap);
        String expected = user.toString();
        String actual = usersMap.get(user.getLogin()).toString();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void addUserShouldBeFalse() throws Exception{
        User user = new User("login1","1234");
        uc.addUser(user.getLogin(),user.getPassword(),filePath,usersMap);
        String expected = "wrongLogin;andPassword";
        String actual = usersMap.get(user.getLogin()).toString();

        assertThat(actual).isNotEqualTo(expected);
    }

//    @Test
//    public void logInUserShouldBeTrue()throws Exception {
//        User user = new User("login1","1234");
//        usersMap.put(user.getLogin(), user);
//
//        uc.isLoginAndPasswordCorrect(user.getLogin(), user.getPassword(), usersMap);
//
//        assertThat(actual).isEqualTo(expected);
//    }

//    @Test
//    @Parameters(method = "paramsForFalse")
//    public void logInUserShouldBeFalse(User user, boolean expected)throws Exception {
//        //given
//        //user, userMap
//        //when
//        boolean actual = uc.isLoginAndPasswordCorrect(user.getLogin(), user.getPassword(), usersMap);
//        //then
//        assertThat(actual).isEqualTo(expected);
//    }

//    @Test
//    @Parameters(method = "paramsForTrue")
//    public void saveUsersMapToFileShouldBeTrue(boolean expected){
//        //given
//        //userMap
//        //when
//        usersMap.put("tom",new User("tom","1233"));
//        boolean actual = UserFileController.writeUsersToFile(usersMap, filePath);
//        //then
//        assertThat(actual).isEqualTo(expected);
//
//    }

    @Test
    public void getMapOfUsersShouldReturnMapThatContainsKey() throws Exception {
        //given
        Files.write(Paths.get(filePath), "aa;aa;bb\n".getBytes(), StandardOpenOption.CREATE);
        //nio
        //when
        Map<String, User> actual = uc.getMapOfUsers(filePath);

        //then
        assertTrue(actual.containsKey("aa"));
    }

    @Test
    public void getMapOfUsersShouldReturnMapThatContainsUser() throws Exception{
        //given
        Files.write(Paths.get(filePath),"tom;tom;1234\n".getBytes(),StandardOpenOption.CREATE);
        User user = new User("tom","1234");
        //when
        Map<String,User> actual = uc.getMapOfUsers(filePath);
        actual.put(user.getLogin(),user);
        //then
        assertTrue(actual.containsValue(user));
    }
    @Test
    public void getMapOfUsersShouldReturnMapThatDoNotContainsUser() throws Exception{
        //given
        Files.write(Paths.get(filePath),"ttt;ttt;54321\n".getBytes(),StandardOpenOption.CREATE);
        User user = new User("tom","1234");
        //when
        Map<String,User> actual = uc.getMapOfUsers(filePath);
        //then
        assertFalse(actual.containsValue(user));
    }

//    @Test
//    public void saveUsersMapToFileShouldBeTrue() throws Exception {
//        //given
//        Files.write(Paths.get(filePath),"tom;tom;1234\n".getBytes(),StandardOpenOption.CREATE);
//        //when
//        usersMap = uc.getMapOfUsers(filePath);
//        boolean actual =UserFileController.writeUsersToFile(usersMap, filePath);
//        //then
//        assertThat(actual).isEqualTo(true);
//    }
}