package com.variacode.project.rest.interceptors;

import com.variacode.project.rest.RESTException;
import com.wordnik.swagger.annotations.ApiParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author miguel@variacode.com
 */
@Interceptor
@SecurityChecked
public class RESTInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Object[] params = context.getParameters();

        boolean notNullChecked = false;
        SecurityChecked securityCheck = null;

        Method m = context.getMethod();

        if (m.getAnnotations() != null) {
            for (Annotation a : context.getMethod().getAnnotations()) {
                if (a instanceof SecurityChecked) {
                    securityCheck = (SecurityChecked) a;
                } else if (a instanceof NotNullChecked) {
                    notNullChecked = true;
                }
            }
        }
        
        if (securityCheck!=null) {
            //TODO
        }

        //this will mark required = true
        if (notNullChecked && params != null && params.length > 0) {
            boolean[] notNull = new boolean[params.length];
            Arrays.fill(notNull, false);

            Annotation[][] a = m.getParameterAnnotations();
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    boolean checkNull = false;
                    
                    if (a[i] != null && a[i].length > 0) {
                        for (Annotation a2 : a[i]) {
                            Class<? extends Annotation> type = a2.annotationType();
                            if (a2 instanceof ApiParam) {
                                for (Method method : type.getDeclaredMethods()) {
                                    try {
                                        Object value = method.invoke(a2, (Object[]) null);
                                        if (method.getName().equals("required") && value.equals(Boolean.TRUE)) {
                                            checkNull = true;
                                        }
                                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                        Logger.getLogger(RESTInterceptor.class.getName()).log(Level.SEVERE, null, ex);
                                        throw new RESTException(Response.Status.INTERNAL_SERVER_ERROR, ex.getMessage());
                                    }
                                }
                            }
                        }
                    }
                    
                    if(checkNull && i<params.length && params[i] == null){
                        //TODO: tell what params was null
                        throw new RESTException(Response.Status.BAD_REQUEST, "Param not valid/null");
                    }
                }
            }
        }

        // TODO check for abuse HTTP429
        Object ret;
        try {
            ret = context.proceed();
        } catch (RESTException ex) {
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(RESTInterceptor.class.getName()).log(Level.SEVERE, null, ex);
            throw new RESTException(Response.Status.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        return ret;
    }
}
