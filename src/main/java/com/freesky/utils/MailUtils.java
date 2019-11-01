package com.freesky.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailUtils {
	private static final Logger logger = Logger.getLogger(MailUtils.class);

	/**
	 * 邮箱信息
	 */
	private static String host = "smtp-mail.outlook.com";
	private static String port = "587";
	private static String userName = "ssr_apple_2017@outlook.com";
	private static String password = "Max@2017";

	public static void main(String[] args) {
		try {
			MailUtils.sendTextMail("test subject", "test content", new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送文本邮件
	 * 
	 * @throws Exception
	 */
	public static void sendTextMail(String subject, String content, Date sendDate) throws Exception {
		long t1 = System.currentTimeMillis();
		
		Properties pro = System.getProperties();
		pro.put("mail.smtp.host", host);
		pro.put("mail.smtp.port", port);
		pro.put("mail.smtp.auth", "true");

		// for outlook begin ---
		pro.put("mail.smtp.starttls.enable", "true");
		// for outlook end ---

		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 设置邮件消息的发送者
		mailMessage.setFrom(new InternetAddress(userName));
		// 创建邮件的接收者地址，并设置到邮件消息中
		// mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		mailMessage.setRecipients(Message.RecipientType.TO,
				new InternetAddress[] { new InternetAddress("zhwyv@163.com") });
//		mailMessage.setRecipients(Message.RecipientType.CC, new InternetAddress[] {
//				new InternetAddress("hwt69love@163.com") });
		// , new InternetAddress("517935294@qq.com")

		// 设置邮件消息的主题
		mailMessage.setSubject(subject);
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(sendDate);
		// 设置邮件消息的主要内容
		mailMessage.setText(content);
		// 发送邮件
		Transport.send(mailMessage);

		long t2 = System.currentTimeMillis();
		System.out.println("--Send email consumes: " + (t2 - t1) +" ms");
		logger.info("--Send email consumes: " + (t2 - t1) +" ms");
		System.out.println("---------" + subject + " Mail is Sent.--------");
	}

}
