package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/15/16.
 */
public class Account {

    public String name;
    public String mobile_num;
   // public String password;
    //public String location;
    public String email_add;

    public String login_mobile;
    public String login_pass;

    public Account(){

    }

    public Account(String name, String mobile_num,String email_add) {
        this.name = name;
        this.mobile_num = mobile_num;
        //this.password = password;
        this.email_add = email_add;
    }


    public Account(int id, String name, String mobile_num, String password, String email_add) {
      //  this.id = id;
        this.name = name;
        this.mobile_num = mobile_num;
       // this.password = password;
        this.email_add = email_add;
    }

    public Account(String login_pass, String login_mobile) {
        this.login_pass = login_pass;
        this.login_mobile = login_mobile;
    }

    // public int getId() {
        //return id;
  //  }

  //  public void setId(int id) {
      //  this.id = id;
   // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_num() {
        return mobile_num;
    }

    public void setMobile_num(String mobile_num) {
        this.mobile_num = mobile_num;
    }

   // public String getPassword() {
      //  return password;
   // }

  //  public void setPassword(String password) {
    //    this.password = password;
  //  }

    public String getEmail_add() {
        return email_add;
    }

    public void setEmail_add(String email_add) {
        this.email_add = email_add;
    }

    public String getLogin_mobile() {
        return login_mobile;
    }

    public void setLogin_mobile(String login_mobile) {
        this.login_mobile = login_mobile;
    }

    public String getLogin_pass() {
        return login_pass;
    }

    public void setLogin_pass(String login_pass) {
        this.login_pass = login_pass;
    }
}
