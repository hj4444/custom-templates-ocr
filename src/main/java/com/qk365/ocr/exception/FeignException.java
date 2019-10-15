package com.qk365.ocr.exception;

public class FeignException extends RuntimeException  {
    private static final long serialVersionUID = -8315607115106350418L;

    private int status;
    private String reason;

    public FeignException(int status, String reason) {
        super();
        this.status = status;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "OcrClientException [status=" + status + ", reason=" + reason + "]";
    }
}
