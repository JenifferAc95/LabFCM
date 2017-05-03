package co.edu.udea.compumovil.gr07_20171.labfcm.data;

/**
 * Created by Jeniffer Acosta on 25/04/2017.
 */

public class User {
    private String nickName;
    private String mail;
    private String password;
    private String age;
    private String picture;

    public User() {
    }

    public User(String nickName, String mail, String password, String age, String picture) {
        this.nickName = nickName;
        this.mail = mail;
        this.password = password;
        this.age = age;
        this.picture = picture;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
