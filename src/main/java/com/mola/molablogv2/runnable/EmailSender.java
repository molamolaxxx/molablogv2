package com.mola.molablogv2.runnable;

import com.mola.molablogv2.config.EmailConfig;
import com.mola.molablogv2.emun.EmailSenderEmun;
import com.mola.molablogv2.exception.EmailSenderException;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * @Author: molamola
 * @Date: 19-7-31 下午3:37
 * @Version 1.0
 */
@Slf4j
public class EmailSender implements Runnable {

    /**
     * 邮件发送session
     */
    private Session session;

    /**
     * 信息
     */
    private Message message;

    /**
     * 邮箱配置
     */
    private EmailConfig config;

    public EmailSender(Session session, Message message, EmailConfig config){
        this.session = session;
        this.message = message;
        this.config = config;
    }

    private void sendEmail(){
        try {
            Transport ts = session.getTransport();
            ts.connect(config.getMailHost(),config.getUserName(),config.getPassword());
            ts.sendMessage(message,message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            throw new EmailSenderException(EmailSenderEmun.EMAIL_SEND_ERROR,
                    EmailSenderEmun.EMAIL_SEND_ERROR.getMsg()+":"+e.getMessage());
        }
    }

    @Override
    public void run() {
        sendEmail();
    }
}
