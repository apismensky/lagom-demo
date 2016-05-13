package com.octanner.user.impl

import akka.NotUsed
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.octanner.user.api.{User, UserService}
import converter.ServiceCallConverter._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserServiceImpl extends UserService {
  override def getUser(id: String): ServiceCall[NotUsed, User] = { request =>
    Future(User(id.toLong, s"firstName$id", s"lastName$id", s"firstName$id.lastName$id@octanner.com"))
  }
}
