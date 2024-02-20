package org.koreait.edutainment;

public class User {

    String emailId;
    String idToken;
    String password;

    String password2;
    String name;
    int age;

    public User(){}
    public String getIdToken() {
        return idToken;
    }
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword2() {
        return password2;
    }
    public void setPassword2(String passwordcheck) {
        this.password2 = password2;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return this.age;
    }

    public User(String emailId, String password, String password2,
                String name, int age){
        this.emailId = emailId;
        this.password = password;
        this.password2 = password2;
        this.name = name;
        this.age= age;
    }
}