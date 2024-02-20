package az.code.agency.exception;

import lombok.Getter;

@Getter
public class AgentNotFoundException extends RuntimeException {

    public final ErrorCodes errorCode;
    public final transient Object[] arguments;


    public AgentNotFoundException(ErrorCodes errorCode, Object... args) {
        this.errorCode = errorCode;
        this.arguments = args == null ? new Object[0] : args;
    }
}
