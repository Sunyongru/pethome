package com.itsun.vo;

import javax.validation.constraints.NotNull;

public class LoginVo {

    @NotNull
    private String userAccount;

    @NotNull
    private String password;

    public String getUserAccount() {
        return userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
