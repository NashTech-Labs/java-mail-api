package com.knoldus.email

import java.util.Properties
import javax.mail._
import javax.mail.internet._

import com.typesafe.config.ConfigFactory
import org.slf4j.{Logger, LoggerFactory}

class MailerAPI {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())

  val conf = ConfigFactory.systemEnvironment()
  val senderEmail = try {
    conf.getString("email")
  } catch {
    case ex: Exception =>
      logger.error("Email key not set.\nPlease add email variable using -> export email=your_email@domain.com")
      ""
  }

  val password = try {
    conf.getString("password")
  } catch {
    case ex: Exception =>
      logger.error("Password key not set.\nPlease add password variable using -> export password=your_password")
      ""
  }
  val hostName = "smtp.gmail.com"

  val port = "587"

  val properties = new Properties
  properties.put("mail.smtp.port", port)
  properties.setProperty("mail.transport.protocol", "smtp")
  properties.setProperty("mail.smtp.starttls.enable", "true")
  properties.setProperty("mail.host", hostName)
  properties.setProperty("mail.user", senderEmail)
  properties.setProperty("mail.password", password)
  properties.setProperty("mail.smtp.auth", "true")

  val session = Session.getDefaultInstance(properties)

  def sendMail(recipient: List[String], subject: String, content: String): Option[Int] = {
    try {
      val message = new MimeMessage(session)
      message.setFrom(new InternetAddress(senderEmail))
      val recipientAddress: Array[Address] = (recipient map { recipient => new InternetAddress(recipient) }).toArray
      message.addRecipients(Message.RecipientType.TO, recipientAddress)
      message.setSubject(subject)
      message.setHeader("Content-Type", "text/plain;")
      message.setContent(content, "text/html")
      val transport = session.getTransport("smtp")
      transport.connect(hostName, senderEmail, password)
      transport.sendMessage(message, message.getAllRecipients)
      logger.info("Email Sent!!")
      Some(recipient.size)
    }
    catch {
      case exception: Exception =>
        logger.error("Mail delivery failed. " + exception)
        None
    }
  }

  def sendMail(recipient: String, subject: String, content: String): Option[Int] = {
    try {
      val message = new MimeMessage(session)
      message.setFrom(new InternetAddress(senderEmail))
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient))
      message.setSubject(subject)
      message.setHeader("Content-Type", "text/plain;")
      message.setContent(content, "text/html")
      val transport = session.getTransport("smtp")
      transport.connect(hostName, senderEmail, password)
      transport.sendMessage(message, message.getAllRecipients)
      logger.info("Email Sent!!")
      Some(recipient.length)
    }
    catch {
      case exception: Exception =>
        logger.error("Mail delivery failed. " + exception)
        None
    }
  }

}
