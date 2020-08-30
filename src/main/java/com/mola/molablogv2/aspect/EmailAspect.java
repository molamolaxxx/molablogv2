package com.mola.molablogv2.aspect;

import com.mola.molablogv2.annotation.SendEmail;
import com.mola.molablogv2.common.EmailParser;
import com.mola.molablogv2.config.EmailConfig;
import com.mola.molablogv2.emun.EmailSenderEmun;
import com.mola.molablogv2.exception.EmailSenderException;
import com.mola.molablogv2.runnable.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: molamola
 * @Date: 19-7-31 上午9:36
 * @Version 1.0
 */
@Aspect
@Component
@ComponentScan(basePackages = "com.mola.molablogv2")
@Slf4j
public class EmailAspect {

    /**
     * 用作发送的线程池
     */
    private ThreadPoolExecutor executor;

    /**
     * 共享session
     */
    private Session session;

    @Autowired
    private EmailConfig config;

    public EmailAspect(){
        initThreadPool();
    }

    @Pointcut("@annotation(com.mola.molablogv2.annotation.SendEmail)")
    public void pointCut(){
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){

        //获得方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SendEmail annotation = method.getAnnotation(SendEmail.class);

        //获取收件人地址
        String responseAddress = annotation.responseAddress();

        //读取配置
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        if (null == result){
            throw new EmailSenderException(EmailSenderEmun.RETURN_NON_NULL);
        }

        //获取parser
        EmailParser emailParser = null;
        try {
            emailParser = (EmailParser)result;
        } catch (ClassCastException e) {
            throw new EmailSenderException(EmailSenderEmun.RETURN_ERROR,e.getMessage());
        }

        if (null == emailParser){
            throw new EmailSenderException(EmailSenderEmun.RETURN_ERROR);
        }

        if (null == executor){
            throw new EmailSenderException(EmailSenderEmun.THREAD_POOL_INIT_ERROR);
        }

        Session mailSession = (null == session ? initMailSession() : session);
        Message mailMessage = null;
        try {
            mailMessage = initMessage(emailParser.getSubject()
                    ,emailParser.getContent()
                    ,mailSession,responseAddress);
        } catch (Exception e) {
            throw new EmailSenderException(EmailSenderEmun.MESSAGE_INIT_ERROR,
                    EmailSenderEmun.MESSAGE_INIT_ERROR.getMsg()+":"+e.getMessage());
        }

        //线程池执行发送
        Runnable runnable = new EmailSender(mailSession, mailMessage, config);
        executor.execute(runnable);

        return result;
    }


    /**
     * 初始化邮箱
     */
    private Session initMailSession(){

        //邮箱配置
        Properties prop = new Properties();
        prop.setProperty("mail.host", config.getMailHost());
        prop.setProperty("mail.transport.protocol", config.getMailTransportProtocol());
        prop.setProperty("mail.smtp.auth", config.getMailSmtpAuth());
        prop.setProperty("mail.smtp.port" , config.getPort().toString());
        prop.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");//设置为SSL协议

        //创建session
        Session session = Session.getInstance(prop);
        //setDebug模式
        session.setDebug(false);

        this.session = session;
        return session;
    }

    /**
     * 初始化邮件
     */
    private Message initMessage(String sub, String content, Session session, String address) throws Exception{

        Message message = new MimeMessage(session);
        //指明发件人
        message.setFrom(new InternetAddress(config.getUserName()+"@sohu.com"));
        //收件人
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(address));
        //设置标题和正文
        message.setSubject(sub);
        message.setContent(content,"text/html;charset=UTF-8");

        return message;
    }

    /**
     * 线程池,失败重试
     */
    private void initThreadPool(){
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(10);
        this.executor = new ThreadPoolExecutor(4,6,
                30, TimeUnit.SECONDS,blockingQueue,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
