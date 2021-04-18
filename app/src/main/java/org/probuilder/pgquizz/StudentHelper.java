package org.probuilder.pgquizz;

public class StudentHelper {

    private String fullname,mail,mobile,password,classChoose,subChoose;

    public StudentHelper(String fullname, String mail, String mobile, String password, String classChoose, String subChoose) {
        this.fullname = fullname;
        this.mail = mail;
        this.mobile = mobile;
        this.password = password;
        this.classChoose = classChoose;
        this.subChoose = subChoose;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClassChoose() {
        return classChoose;
    }

    public void setClassChoose(String classChoose) {
        this.classChoose = classChoose;
    }

    public String getSubChoose() {
        return subChoose;
    }

    public void setSubChoose(String subChoose) {
        this.subChoose = subChoose;
    }
}
