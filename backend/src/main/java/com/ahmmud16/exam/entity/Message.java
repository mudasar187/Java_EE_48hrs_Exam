package com.ahmmud16.exam.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long messageId;

    @NotBlank
    @Size(max = 4096)
    private String text;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @NotBlank
    @Size(max = 128)
    private String senderEmail;

    @NotBlank
    @Size(max = 128)
    private String receiverEmail;


    public Message() {
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
