package com.bizTrack.BizTrack.ExceptionHandler;

import com.bizTrack.BizTrack.Exception.CourseNotFoundException;
import com.bizTrack.BizTrack.Exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BizTrackExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundExceptionNew(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public String handleCourseNotFoundExceptionNew(CourseNotFoundException ex) {
        return ex.getMessage();
    }

}
