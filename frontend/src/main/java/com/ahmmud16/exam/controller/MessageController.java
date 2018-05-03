package com.ahmmud16.exam.controller;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class MessageController {

    private String receiver;

    public String sendMessage(String receiver) {
        this.receiver = receiver;
        return "/messages.jsf?faces-redirect=true";
    }

    public String getReceiver() {
        return receiver;
    }
}
