package com.house.rent.entity;

import com.house.rent.util.ResultCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public Result(){

    }

    public Result(ResultCode resultCode,Object data){
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public static Result success(){
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public  static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result failure(ResultCode rc){
        Result result = new Result();
        result.setResultCode(rc);
        return result;
    }

    public  static Result failure(ResultCode rc,Object data) {
        Result result = new Result();
        result.setResultCode(rc);
        result.setData(data);
        return result;
    }

    private void setResultCode(ResultCode rc) {
        this.code = rc.code();
        this.message = rc.message();
    }
}
