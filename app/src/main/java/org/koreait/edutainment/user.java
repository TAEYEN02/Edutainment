package org.koreait.edutainment;

public class user {

    String emailId;
    String idToken;
    String password;

    String password2;
    String name;
    String birthyear;
    String birthday;
    String birthdate;

    public user(){}

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

    public String getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(String birthyear) {
        this.birthyear = birthyear;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {

        this.birthdate = birthdate;
    }

    public user(String emailId, String password, String password2,
                String name, String birthyear,
                String birthday, String birthdate ){
        this.emailId = emailId;
        this.password = password;
        this.password2 = password2;
        this.name = name;
        this.birthday = birthday;
        this.birthdate = birthdate;
        this. birthyear= birthyear;



    }
}