package com.ahmmud16.exam.service;

import com.ahmmud16.exam.StubApplication;
import com.ahmmud16.exam.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MessageServiceTest extends ServiceTestBase{

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Test
    public void testSendMessages() {

        String sender = "sender@mail.com";
        String senderFirstname = "sender";
        String senderLastname = "sender";
        String senderPassword = "123";

        userService.createUser(sender, senderFirstname, senderLastname, senderPassword, false);

        String receiver = "receiver@mail.com";
        String receiverFirstname = "receiver";
        String receiverLastname = "receiver";
        String receiverPassword = "123";

        userService.createUser(receiver, receiverFirstname, receiverLastname, receiverPassword, false);

        assertNull(messageService.getMessages(sender, true));
        assertNull(messageService.getMessages(receiver, false));

        Long id = messageService.sendMessage(sender, receiver, "Hello !!!");
        assertNotNull(id);

        List<Message> messages = messageService.getMessages(sender, true);
        assertEquals(1, messages.size());

        List<Message> messages1 = messageService.getMessages(receiver, false);
        assertEquals(1, messages1.size());
    }

    @Test
    public void testSendMessageToNonExistsUser() {
        String sender = "sender@mail.com";
        String senderFirstname = "sender";
        String senderLastname = "sender";
        String senderPassword = "123";

        userService.createUser(sender, senderFirstname, senderLastname, senderPassword, false);

        try {
            messageService.sendMessage(sender, "not exists", "Hello !!!");
            fail();
        } catch (Exception e) {
            //excepted
        }
    }

    @Test
    public void testSendMessageFromNonExistingUser() {
        String receiver = "receiver@mail.com";
        String receiverFirstname = "receiver";
        String receiverLastname = "receiver";
        String receiverPassword = "123";

        userService.createUser(receiver, receiverFirstname, receiverLastname, receiverPassword, false);

        try {
            messageService.sendMessage("nonExsists", receiver, "Hello !!!");
            fail();
        } catch (Exception e) {
            //excepted
        }
    }

    @Test
    public void testGetMessagesFromNonRegisteredUser() {

        try {
            messageService.getMessages("notExists", true);
            fail();
        } catch (Exception e) {
            // excepted
        }
    }

    @Test
    public void getMessagesSortedByRecentTime() {

        String sender = "sender@mail.com";
        String senderFirstname = "sender";
        String senderLastname = "sender";
        String senderPassword = "123";

        userService.createUser(sender, senderFirstname, senderLastname, senderPassword, false);

        String receiver = "receiver@mail.com";
        String receiverFirstname = "receiver";
        String receiverLastname = "receiver";
        String receiverPassword = "123";

        userService.createUser(receiver, receiverFirstname, receiverLastname, receiverPassword, false);

        Long a = messageService.sendMessage(sender, receiver, "hello");
        Long b = messageService.sendMessage(sender, receiver, "hello??");
        Long c = messageService.sendMessage(sender, receiver, "hello ?? are you there??");

        List<Message> messages = messageService.getMessages(sender, true);
        assertEquals(3, messages.size());

        assertEquals(c, messages.get(0).getMessageId());
        assertEquals(b, messages.get(1).getMessageId());
        assertEquals(a, messages.get(2).getMessageId());

    }

    @Test
    public void testContentInMessage() {

        String sender = "sender@mail.com";
        String senderFirstname = "sender";
        String senderLastname = "sender";
        String senderPassword = "123";

        userService.createUser(sender, senderFirstname, senderLastname, senderPassword, false);

        String receiver = "receiver@mail.com";
        String receiverFirstname = "receiver";
        String receiverLastname = "receiver";
        String receiverPassword = "123";

        userService.createUser(receiver, receiverFirstname, receiverLastname, receiverPassword, false);

        String text = "hello";

        Long a = messageService.sendMessage(sender, receiver, text);
        assertNotNull(a);

        List<Message> messages = messageService.getMessages(sender, true);

        Message message = messages.get(0);

        assertEquals(a, message.getMessageId());
        assertEquals(sender, message.getSenderEmail());
        assertEquals(receiver, message.getReceiverEmail());
        assertEquals(text, message.getText());
    }

}