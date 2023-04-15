package com.demo.account.Vo;

import lombok.Data;

@Data
public class ReturnVo {
    private Integer code;
    private Integer costTime;
    private String message;
    private ResultVo resultVo;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCostTime() {
        return costTime;
    }

    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultVo getResultVo() {
        return resultVo;
    }

    public void setResultVo(ResultVo resultVo) {
        this.resultVo = resultVo;
    }
}
