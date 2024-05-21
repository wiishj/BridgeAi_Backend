package bridge.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    NOT_FOUND_BUSINESS_ID(1001, "요청한 ID에 해당하는 사업 목록이 존재하지 않습니다."),
    NOT_FOUND_BUSINESS_TYPE(1002, "요청한 TYPE에 해당하는 사업 목록이 존재하지 않습니다."),
    NOT_FOUND_BUSINESS_MONTH(1003, "요청한 달에 해당하는 사업 목록이 존재하지 않습니다."),
    NOT_FOUND_BUSINESS_MONTH_AND_TYPE(1004, "요청한 달과 TYPE에 해당하는 사업 목록이 존재하지 않습니다."),
    NOT_FOUND_BUSINESS(1005, "사업 목록이 존재하지 않습니다."),
    NOT_SET_TYPE(1006, "TYPE을 1개이상 설정해주세요."),
    NOT_FOUND_TYPE(1007, "요청하신 TYPE명이 잘못되었습니다."),
    INVALID_BUSINESS_REQUEST(1008, "필요한 사업 입력정보가 누락되었습니다."),

    NOT_FOUND_PLAN_ID(2000, "요청한 ID에 해당하는 사계서가 존재하지 않습니다."),
    INVALID_PLAN_REQUEST(2001, "필요한 사계서 입력정보가 누락되었습니다."),
    INVALID_ITEM1_SIZE(2002, "1번 항목을 60자이상 입력해주세요."),
    INVALID_ITEM2_SIZE(2003, "2번 항목을 140자이상 입력해주세요."),
    INVALID_NECESSARY_TERM(2004, "필수 이용약관에 동의해주세요."),
    NOT_FOUND_PLAN(2005, "사계서 목록이 존재하지 않습니다."),

    INVALID_MEMBER_REQUEST(3001, "필요한 사용자 정보가 누락되었습니다."),
    INVALID_MEMBER_PHONENUMBER(3002, "휴대폰 번호 형식이 잘못되었습니다."),
    INVALID_MEMBER_EMAIL(3003, "이메일 형식이 잘못되었습니다."),
    INVALID_MEMBER_BIRTH(3004, "생년월일 형식이 잘못되었습니다."),
    NOT_FOUND_MEMBER_ID(3005, "요청한 ID에 해당하는 사용자가 존재하지 않습니다.");

    private final int code;
    private final String message;
}
