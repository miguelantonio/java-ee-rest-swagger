
package com.variacode.project.rest;

import javax.ws.rs.core.Response.Status;

/**
 *
 * @author miguel@variacode.com
 */
public class RESTException extends Exception {

    private final Status status;

    public RESTException(Status status) {
        this.status = status;
    }
    
    public RESTException(Status status, String msg) {
        super(msg);
        this.status = status;
    }
    
    public RESTException(Status status, String msg, Throwable err) {
        super(msg, err);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
