package br.com.synchro.infrack;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason="My Reason")
public class ExceptionDemo extends RuntimeException {
    public ExceptionDemo(String msg) {
		super(msg);
	}
}
