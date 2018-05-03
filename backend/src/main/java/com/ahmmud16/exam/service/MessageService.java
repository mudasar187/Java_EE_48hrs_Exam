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


    public Long sendMessage(String sender, String receiver, String text) {

        User senderUser = em.find(User.class, sender);
        User receiverUser = em.find(User.class, receiver);

        if(senderUser == null || receiverUser == null) {
            throw new IllegalArgumentException("User not exists!");
        }


        Message message = new Message();
        message.setCreatedTime(new Date());
        message.setSenderEmail(sender);
        message.setReceiverEmail(receiver);
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
