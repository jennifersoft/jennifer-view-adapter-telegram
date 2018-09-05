package com.aries.telegram.entity;

/**
 * @author Khalid Saeed <khalid@jennifersoft.com>
 * @Created 11/22/17 12:10 PM.
 */
public class TelegramResponse {

    private boolean ok;
    private int error_code;
    private String description;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
