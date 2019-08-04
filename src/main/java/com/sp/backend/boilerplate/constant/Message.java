package com.sp.backend.boilerplate.constant;

public enum Message {
    NO_INFO("No information available");

    private final String msg;

    private Message(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return this.msg;
    }
}
