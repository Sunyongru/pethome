package com.itsun.result;

/**
 * 用来向前端返回json样式的类
 * @param <T>
 */
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(T data) {
        this.code=0;
        this.message="success";
        this.data=data;
    }

    public Result(CodeMsg msg) {
        if (msg==null)
            return;
        this.code=msg.getCode();
        this.message=msg.getMsg();
    }

    //成功调用的方法
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    //失败调用的方法
    public static <T> Result<T> error(CodeMsg msg){
        return new Result<T>(msg);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
