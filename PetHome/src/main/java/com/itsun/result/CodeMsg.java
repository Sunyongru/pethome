package com.itsun.result;

/**
 * 用来向返回前端信息的类
 */
public class CodeMsg {
    private int code;
    private String msg;

    //通用信息
    public static CodeMsg SUCCESS=new CodeMsg(0,"success");
    public static CodeMsg ERROR=new CodeMsg(1000,"未知错误");

    //基础操作
    public static CodeMsg VERIFYCODE_ERROR=new CodeMsg(1001,"验证码输入错误");
    public static CodeMsg DELETE_FAILE=new CodeMsg(1002,"删除失败");
    //用户信息200x
    public static CodeMsg REGIST_FAIL=new CodeMsg(2000,"注册失败");
    public static CodeMsg USER_ACCOUNT_NULL_ERROR=new CodeMsg(2001,"账号不能为空");
    public static CodeMsg USER_PASSWORD_NULL_ERROR=new CodeMsg(2002,"密码不能为空");
    public static CodeMsg USER_NOT_EXIST=new CodeMsg(2003,"用户不存在");
    public static CodeMsg USER_ALREADY_EXIST=new CodeMsg(2004,"用户已存在");
    public static CodeMsg UPDATE_ERROR=new CodeMsg(2005,"修改信息失败");
    public static CodeMsg LOGIN_ERROR=new CodeMsg(2006,"账号或密码错误");
    public static CodeMsg CREATE_INFO_FAILE=new CodeMsg(3000,"发布失败");


    public CodeMsg(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code= this.code;
        String massage=String.format(this.msg,args);
        return new CodeMsg(code,massage);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
