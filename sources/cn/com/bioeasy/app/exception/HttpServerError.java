package cn.com.bioeasy.app.exception;

public class HttpServerError {
    private int code;
    private String developerMessage;
    private String message;
    private String moreInfoUrl;
    private String status;
    private Throwable throwable;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public String getDeveloperMessage() {
        return this.developerMessage;
    }

    public void setDeveloperMessage(String developerMessage2) {
        this.developerMessage = developerMessage2;
    }

    public String getMoreInfoUrl() {
        return this.moreInfoUrl;
    }

    public void setMoreInfoUrl(String moreInfoUrl2) {
        this.moreInfoUrl = moreInfoUrl2;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public void setThrowable(Throwable throwable2) {
        this.throwable = throwable2;
    }
}
