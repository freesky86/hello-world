package com.freesky.test;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailSenderTest
{
//   private String host = "smtp.163.com";
//   private String port = "25";
//   private String userName = "zhwy_seu@163.com";
//   private String password = "Freesky@8818";
	
	   private String host = "smtp-mail.outlook.com";
	   private String port = "587";
	   private String userName = "xwc2018@outlook.com";
	   private String password = "Whitecoin@163.com";
   
   private String to = "zhwyv@163.com";
   
   
   public static void main(String[] args) throws Exception {
	   MailSenderTest sender = new MailSenderTest();
	   String subject = "BTC price change reminder";
	   String content = "24hour high: 7600 USD" + "\n" + "24hour low: 7500 USD";
	   
	   sender.sendTextMail(subject, content);
   }
   
   /**
    * 发送文本邮件
    * 
    * @throws Exception
    */
   public void sendTextMail(String subject, String content) throws Exception
   {
      Properties pro = System.getProperties();
      pro.put("mail.smtp.host", host);
      pro.put("mail.smtp.port", port);
      pro.put("mail.smtp.auth", "true");
      
      //for outlook begin ---
      pro.put("mail.smtp.starttls.enable","true");
      //for outlook end ---

      // 根据邮件会话属性和密码验证器构造一个发送邮件的session
      Session sendMailSession = Session.getDefaultInstance(pro,
            new Authenticator()
            {
               @Override
               protected PasswordAuthentication getPasswordAuthentication()
               {
                  return new PasswordAuthentication(userName, password);
               }
            });
      // 根据session创建一个邮件消息
      Message mailMessage = new MimeMessage(sendMailSession);
      // 设置邮件消息的发送者
      mailMessage.setFrom(new InternetAddress(userName));
      // 创建邮件的接收者地址，并设置到邮件消息中
//      mailMessage.setRecipient(Message.RecipientType.TO,
//            new InternetAddress(to));
      mailMessage.setRecipients(Message.RecipientType.TO, new InternetAddress[] {
    		  new InternetAddress("zhwyv@163.com"), 
    		  new InternetAddress("zhwy_seu@163.com"), 
    		  new InternetAddress("xwc2018@outlook.com")
      });
      // 设置邮件消息的主题
      mailMessage.setSubject(subject);
      // 设置邮件消息发送的时间
      mailMessage.setSentDate(new Date());
      // 设置邮件消息的主要内容
      mailMessage.setText(content);
      // 发送邮件
      Transport.send(mailMessage);
      
      System.out.println("---------done--------");
   }

   /**
    * 发送文本邮件
    * 
    * @throws Exception
    */
   public void sendTextMail() throws Exception
   {
      Properties pro = System.getProperties();
      pro.put("mail.smtp.host", host);
      pro.put("mail.smtp.port", port);
      pro.put("mail.smtp.auth", "true");
      
      //for outlook begin ---
      pro.put("mail.smtp.starttls.enable","true");
      //for outlook end ---

      // 根据邮件会话属性和密码验证器构造一个发送邮件的session
      Session sendMailSession = Session.getDefaultInstance(pro,
            new Authenticator()
            {
               @Override
               protected PasswordAuthentication getPasswordAuthentication()
               {
                  return new PasswordAuthentication(userName, password);
               }
            });
      // 根据session创建一个邮件消息
      Message mailMessage = new MimeMessage(sendMailSession);
      // 设置邮件消息的发送者
      mailMessage.setFrom(new InternetAddress(userName));
      // 创建邮件的接收者地址，并设置到邮件消息中
      mailMessage.setRecipient(Message.RecipientType.TO,
            new InternetAddress(to));
      // 设置邮件消息的主题
      mailMessage.setSubject("Test Email");
      // 设置邮件消息发送的时间
      mailMessage.setSentDate(new Date());
      // 设置邮件消息的主要内容
      mailMessage.setText("this is a test Text mail");
      // 发送邮件
      Transport.send(mailMessage);
      
      System.out.println("---------done--------");
   }

   /**
    * 发送Html邮件
    * 
    * @throws Exception
    */
   public void sendHtmlMail() throws Exception
   {
      Properties pro = System.getProperties();
      pro.put("mail.smtp.host", host);
      pro.put("mail.smtp.port", port);
      pro.put("mail.smtp.auth", "true");

      // 根据邮件会话属性和密码验证器构造一个发送邮件的session
      Session sendMailSession = Session.getDefaultInstance(pro,
            new Authenticator()
            {
               @Override
               protected PasswordAuthentication getPasswordAuthentication()
               {
                  return new PasswordAuthentication(userName, password);
               }
            });
      // 根据session创建一个邮件消息
      Message mailMessage = new MimeMessage(sendMailSession);
      // 设置邮件消息的发送者
      mailMessage.setFrom(new InternetAddress(userName));
      // 创建邮件的接收者地址，并设置到邮件消息中
      mailMessage.setRecipient(Message.RecipientType.TO,
            new InternetAddress(to));
      // 设置邮件消息的主题
      mailMessage.setSubject("Test Email");
      // 设置邮件消息发送的时间
      mailMessage.setSentDate(new Date());

      // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
      Multipart mainPart = new MimeMultipart();
      // 创建一个包含HTML内容的MimeBodyPart
      BodyPart html = new MimeBodyPart();
      // 设置HTML内容
      html.setContent(
            "<html><body><img src='http://avatar.csdn.net/A/C/A/1_jianggujin.jpg'/><div>this is a HTML email.</div></body></html>",
            "text/html; charset=utf-8");
      mainPart.addBodyPart(html);
      // 将MiniMultipart对象设置为邮件内容
      mailMessage.setContent(mainPart);
      // 发送邮件
      Transport.send(mailMessage);
   }

   /**
    * 发送内嵌图片邮件
    * 
    * @throws Exception
    */
   public void sendImageMail() throws Exception
   {
      Properties pro = System.getProperties();
      pro.put("mail.smtp.host", host);
      pro.put("mail.smtp.port", port);
      pro.put("mail.smtp.auth", "true");

      // 根据邮件会话属性和密码验证器构造一个发送邮件的session
      Session sendMailSession = Session.getDefaultInstance(pro,
            new Authenticator()
            {
               @Override
               protected PasswordAuthentication getPasswordAuthentication()
               {
                  return new PasswordAuthentication(userName, password);
               }
            });
      // 根据session创建一个邮件消息
      Message mailMessage = new MimeMessage(sendMailSession);
      // 设置邮件消息的发送者
      mailMessage.setFrom(new InternetAddress(userName));
      // 创建邮件的接收者地址，并设置到邮件消息中
      mailMessage.setRecipient(Message.RecipientType.TO,
            new InternetAddress(to));
      // 设置邮件消息的主题
      mailMessage.setSubject("Test Email");
      // 设置邮件消息发送的时间
      mailMessage.setSentDate(new Date());

      MimeMultipart multipart = new MimeMultipart("related");

      BodyPart messageBodyPart = new MimeBodyPart();
      String htmlText = "<html><body><img src='cid:image'/><div>this is a HTML email.</div></body></html>";
      messageBodyPart.setContent(htmlText, "text/html; charset=utf-8");
      multipart.addBodyPart(messageBodyPart);

      MimeBodyPart imageBodyPart = new MimeBodyPart();
      DataSource fds = new FileDataSource("1_jianggujin.jpg");
      imageBodyPart.setDataHandler(new DataHandler(fds));
      imageBodyPart.setContentID("image");
      multipart.addBodyPart(imageBodyPart);

      mailMessage.setContent(multipart);
      // 发送邮件
      Transport.send(mailMessage);
   }

   /**
    * 发送附件邮件
    * 
    * @throws Exception
    */
   public void sendAttachmentMail() throws Exception
   {
      Properties pro = System.getProperties();
      pro.put("mail.smtp.host", host);
      pro.put("mail.smtp.port", port);
      pro.put("mail.smtp.auth", "true");

      // 根据邮件会话属性和密码验证器构造一个发送邮件的session
      Session sendMailSession = Session.getDefaultInstance(pro,
            new Authenticator()
            {
               @Override
               protected PasswordAuthentication getPasswordAuthentication()
               {
                  return new PasswordAuthentication(userName, password);
               }
            });
      // 根据session创建一个邮件消息
      Message mailMessage = new MimeMessage(sendMailSession);
      // 设置邮件消息的发送者
      mailMessage.setFrom(new InternetAddress(userName));
      // 创建邮件的接收者地址，并设置到邮件消息中
      mailMessage.setRecipient(Message.RecipientType.TO,
            new InternetAddress(to));
      // 设置邮件消息的主题
      mailMessage.setSubject("Test Email");
      // 设置邮件消息发送的时间
      mailMessage.setSentDate(new Date());

      MimeMultipart multipart = new MimeMultipart("mixed");

      BodyPart messageBodyPart = new MimeBodyPart();
      String htmlText = "<html><body><div>this is a Attachment email.this email has a attachment!</div></body></html>";
      messageBodyPart.setContent(htmlText, "text/html; charset=utf-8");
      multipart.addBodyPart(messageBodyPart);

      MimeBodyPart imageBodyPart = new MimeBodyPart();
      File imageFile = new File("1_jianggujin.jpg");
      DataSource fds = new FileDataSource(imageFile);
      imageBodyPart.setDataHandler(new DataHandler(fds));
      imageBodyPart.setFileName(MimeUtility.encodeWord(imageFile.getName()));
      multipart.addBodyPart(imageBodyPart);

      mailMessage.setContent(multipart);
      // 发送邮件
      Transport.send(mailMessage);
   }

   /**
    * 发送内嵌图片和附件邮件
    * 
    * @throws Exception
    */
   public void sendImageAndAttachmentMail() throws Exception
   {
      Properties pro = System.getProperties();
      pro.put("mail.smtp.host", host);
      pro.put("mail.smtp.port", port);
      pro.put("mail.smtp.auth", "true");

      // 根据邮件会话属性和密码验证器构造一个发送邮件的session
      Session sendMailSession = Session.getDefaultInstance(pro,
            new Authenticator()
            {
               @Override
               protected PasswordAuthentication getPasswordAuthentication()
               {
                  return new PasswordAuthentication(userName, password);
               }
            });
      // 根据session创建一个邮件消息
      Message mailMessage = new MimeMessage(sendMailSession);
      // 设置邮件消息的发送者
      mailMessage.setFrom(new InternetAddress(userName));
      // 创建邮件的接收者地址，并设置到邮件消息中
      mailMessage.setRecipient(Message.RecipientType.TO,
            new InternetAddress(to));
      // 设置邮件消息的主题
      mailMessage.setSubject("Test Email");
      // 设置邮件消息发送的时间
      mailMessage.setSentDate(new Date());

      // 正文
      MimeBodyPart text = new MimeBodyPart();
      text.setContent("我的头像：<img src='cid:headImg'>",
            "text/html;charset=UTF-8");

      // 正文图片
      MimeBodyPart image = new MimeBodyPart();
      image.setDataHandler(
            new DataHandler(new FileDataSource("1_jianggujin.jpg")));
      image.setContentID("headImg");

      // 附件
      MimeBodyPart attach = new MimeBodyPart();
      DataHandler dh = new DataHandler(new FileDataSource("1_jianggujin.jpg"));
      attach.setDataHandler(dh);
      attach.setFileName(MimeUtility.encodeWord(dh.getName()));

      // 描述关系:正文和图片
      MimeMultipart mp1 = new MimeMultipart();
      mp1.addBodyPart(text);
      mp1.addBodyPart(image);
      mp1.setSubType("related");

      // 正文
      MimeBodyPart content = new MimeBodyPart();
      content.setContent(mp1);

      MimeMultipart multipart = new MimeMultipart("mixed");
      multipart.addBodyPart(content);
      multipart.addBodyPart(attach);

      mailMessage.setContent(multipart);
      // 发送邮件
      Transport.send(mailMessage);
   }
}
