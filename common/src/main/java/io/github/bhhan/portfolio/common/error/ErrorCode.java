package io.github.bhhan.portfolio.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    BAD_REQUEST("잘못된 인자를 전달했습니다.", 400, "AC_400"),
    INTERNAL_SERVER_ERROR("서버 내부 오류입니다.", 500, "AC_500");

    private String message;
    private int status;
    private String code;

    ErrorCode(String message, int status, String code){
        this.message = message;
        this.status = status;
        this.code = code;
    }
}
