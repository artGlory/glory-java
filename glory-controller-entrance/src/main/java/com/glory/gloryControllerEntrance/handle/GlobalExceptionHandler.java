package com.glory.gloryControllerEntrance.handle;

import com.glory.gloryUtils.constants.SysConstants;
import com.glory.gloryUtils.domain.vo.ResponseData;
import com.glory.gloryUtils.exception.ServiceException;
import com.glory.gloryUtils.utils.PrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    @Value(value = "${self.isShowException.IllegalArgument.message:false}")
    private boolean isShowIllegalArgumentExceptionMessge;
    @Value(value = "${self.isShowException.IllegalArgument.stackTrace:false}")
    private boolean isShowIllegalArgumentExceptionStackTrace;
    @Value(value = "${self.isShowException.ServiceException.message:true}")
    private boolean isShowServiceExceptionMessge;
    @Value(value = "${self.isShowException.ServiceException.stackTrace:false}")
    private boolean isShowServiceExceptionStackTrace;
    @Value(value = "${self.isShowException.Throwable.message:true}")
    private boolean isShowThrowableMessge;
    @Value(value = "${self.isShowException.Throwable.stackTrace:false}")
    private boolean isShowThrowableStackTrace;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseData<String> handleIllegalArgumentException(HttpServletRequest request
            , HttpServletResponse response, IllegalArgumentException e) {
        String requestUuid = request.getAttribute(SysConstants.http_request_uuid).toString();
        response.setHeader(SysConstants.success_response, "false");

        if (isShowIllegalArgumentExceptionStackTrace) {
            log.error(requestUuid + " " + PrintUtil.getStackTrace(e));
        } else if (isShowIllegalArgumentExceptionMessge) {
            log.error(requestUuid + " " + PrintUtil.getMessge(e));
        }
        return ResponseData.<String>builder()
                .success(false)
                .message(e.getMessage())
                .responseUUID(requestUuid)
                .build();
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseData<String> handleServiceException(HttpServletRequest request
            , HttpServletResponse response, ServiceException e) {
        String requestUuid = request.getAttribute(SysConstants.http_request_uuid).toString();
        response.setHeader(SysConstants.success_response, "false");

        if (isShowServiceExceptionStackTrace) {
            log.error(requestUuid + " " + PrintUtil.getStackTrace(e));
        } else if (isShowServiceExceptionMessge) {
            log.error(requestUuid + " " + PrintUtil.getMessge(e));
        }
        return ResponseData.<String>builder()
                .success(false)
                .message(e.getMessage())
                .responseUUID(requestUuid)
                .build();
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseData<String> handleThrowableException(HttpServletRequest request
            , HttpServletResponse response, Throwable t) {
        String requestUuid = request.getAttribute(SysConstants.http_request_uuid).toString();
        response.setHeader(SysConstants.success_response, "false");

        if (isShowThrowableStackTrace) {
            log.error(requestUuid + " " + PrintUtil.getStackTrace(t));
        } else if (isShowThrowableMessge) {
            log.error(requestUuid + " " + PrintUtil.getMessge(t));
        }
        return ResponseData.<String>builder()
                .success(false)
                .responseUUID(requestUuid)
                .message(t.getMessage())
                .build();
    }

}
