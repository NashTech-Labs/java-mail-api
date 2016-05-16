package com.knoldus.email

import org.scalatest.FunSuite

class MailerAPITest extends FunSuite {

  val emailAgent = new MailerAPI
  val recipientList = List("prabhatkashyap33@gmail.com")
  val subject = "This is my Subject"
  val message = "This is my Message"

  val recipient = "prabhatkashyap33@gmail.com"

  test("Send Email to one address"){
    val result = emailAgent.sendMail(recipient, subject, message)
    assert(result.isDefined)
  }

  test("Send Email to multiple address"){
    val result = emailAgent.sendMail(recipientList, subject, message)
    assert(result.isDefined)
  }

}
