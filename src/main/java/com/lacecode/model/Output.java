package com.lacecode.model;

public class Output {
    private String error;
    private String output;

    public Output() {
    }

    public Output(String error, String output) {
        this.error = error;
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

}
