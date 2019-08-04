package com.sp.backend.boilerplate.web.exception;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sp.backend.boilerplate.constant.AcError;
import com.sp.backend.boilerplate.constant.Message;
import com.sp.backend.boilerplate.exception.ErrorDetails;

@RestController
@RequestMapping("${error.path:/error}")
public class NotFoundErrorController implements ErrorController {

    @Value("${error.path:/error}")
    private String errorPath;

    @Override
    public String getErrorPath() {
        return this.errorPath;
    }

    @GetMapping
    public ResponseEntity<ErrorDetails> error(WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(new Date(), AcError.RESOURCE_NOT_FOUND.msg(), Message.NO_INFO.msg()),
                HttpStatus.NOT_FOUND);
    }

}
