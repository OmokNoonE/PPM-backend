package org.omoknoone.ppm.domain.employee.exception;

public class PasswordMismatchException extends RuntimeException {
    /**
     * 새로운 비밀번호와 확인용 비밀번호가 일치하지 않을 때 발생하는 예외입니다.
     * <br>
     * 비밀번호 변경 요청 처리 중 두 비밀번호가 다른 경우 이 예외를 던집니다.
     */
    public PasswordMismatchException() {
        super("새로운 비밀번호와 확인용 비밀번호가 일치하지 않습니다.");
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
