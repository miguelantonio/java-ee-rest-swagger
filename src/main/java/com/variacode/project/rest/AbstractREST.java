package com.variacode.project.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;

/**
 *
 * @author miguel@variacode.com
 */
public abstract class AbstractREST {

    public void log(String log) {
        Logger.getLogger(AbstractREST.class.getName()).log(Level.FINE, log);
    }

    public Response responseWithBodyAndLog(Response.Status status, String log) {
        if (log == null) {
            log = status.toString();
        }
        Logger.getLogger(AbstractREST.class.getName()).log(Level.FINE, log);
        return Response.status(status).type("text/plain").entity(log).build();
    }

    public Response responseWithBody(Response.Status status, String log) {
        return Response.status(status).type("text/plain").entity(log).build();
    }

    public void checkNulls(Object... objs) throws RESTException {
        for (Object o : objs) {
            if (o == null) {
                throw new RESTException(Response.Status.BAD_REQUEST, "Null params error");
            }
        }
    }

    public void checkNullsOrEmptyString(Object... objs) throws RESTException {
        checkNulls(objs);
        for (Object o : objs) {
            if (o instanceof String && ((String) o).trim().isEmpty()) {
                throw new RESTException(Response.Status.BAD_REQUEST, "String param shouldn't be empty");
            }
        }
    }

    public Date stringToDate(String date) throws RESTException {
        if (date == null) {
            throw new RESTException(Response.Status.BAD_REQUEST, "Null date");
        }
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {
            throw new RESTException(Response.Status.BAD_REQUEST, "Date format invalid'" + date + "'");
        }
    }

}
