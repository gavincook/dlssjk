package com.greejoy.dlssjk.exception;

import java.net.ConnectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianRuntimeException;

/**
 * @author Gavin
 * @version 1.0
 * @date 2013-1-9
 */
@Component
public class DlssjkExceptionHandler implements HandlerExceptionResolver   {
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if(ex instanceof ErrorServerException||ex instanceof HessianRuntimeException||ex instanceof ConnectException){	return new ModelAndView("pages/dlssjk/ErrorServer");}
		return null;
	}

}
