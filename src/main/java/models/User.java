package models;

public class User {

    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (login.equals(((User) o).getLogin()) && password.equals(((User) o).getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" + "login='" + login + '\'' + ", password='" + password + '\'' + '}';
    }
}
