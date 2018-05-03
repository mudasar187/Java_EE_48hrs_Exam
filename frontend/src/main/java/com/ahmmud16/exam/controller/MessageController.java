package com.ahmmud16.exam.controller;

import com.ahmmud16.exam.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class MessageController implements Serializable {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserInfoController userInfoController;

    private String receiver;
    private String messageText;
    private boolean isMessageSent = false;

    public String sendMessage(String receiver) {
        this.receiver = receiver;
        return "/messages.jsf?faces-redirect=true";
    }

    public void sendMessageToUser() {
        messageService.sendMessage(userInfoController.getUserName(), receiver, messageText);
        isMessageSent = true;
        clearText();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    private void clearText() {
        messageText="";
    }

    public boolean isMessageSent() {
        return isMessageSent;
    }
}
