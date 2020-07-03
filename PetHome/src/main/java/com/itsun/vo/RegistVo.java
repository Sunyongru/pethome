package com.itsun.vo;

import javax.validation.constraints.NotNull;

public class RegistVo {
    @NotNull
    private String userAccount;

    @NotNull
    private String password;

    @NotNull
    private int verifyCode;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }
}
