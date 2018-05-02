package com.ahmmud16.exam.service;

import com.ahmmud16.exam.entity.Message;
import com.ahmmud16.exam.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MessageService {

    @Autowired
    private EntityManager em;


    public Long sendMessage(String fromSender, String toReceiver, String text) {

        User sender = em.find(User.class, fromSender);
        User receiver = em.find(User.class, toReceiver);

        if(sender == null) {
            throw new IllegalArgumentException("Sender not exists " + fromSender);
        }
        if(receiver == null) {
            throw new IllegalArgumentException("Receiver not exists " + toReceiver);
        }


        Message message = new Message();
        message.setCreatedTime(new Date());
        message.setSenderEmail(sender.getUsername());
        message.setReceiverEmail(receiver.getUsername());
        message.setText(text);

        em.persist(message);

        return message.getMessageId();
    }

    public List<Message> getMessages(String userEmail, boolean isSender) {

        User user = em.find(User.class, userEmail);

        if(user == null) {
            throw new IllegalArgumentException("User " + userEmail + " not exists");
        }

        TypedQuery<Message> query;

        if(isSender) { // if true
            query = em.createQuery("select m from Message m where m.senderEmail=?1 order by m.createdTime desc ", Message.class);
        } else { // if false
            query = em.createQuery("select m from Message m where m.receiverEmail=?1 order by m.createdTime desc ", Message.class);
        }

        query.setParameter(1, userEmail);

        List<Message> messages = query.getResultList();

        if(messages.size() == 0) {
            return null;
        }

        return messages;
    }

}
