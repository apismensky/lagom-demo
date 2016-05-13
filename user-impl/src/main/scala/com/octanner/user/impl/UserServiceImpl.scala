package com.octanner.user.impl

import akka.NotUsed
import com.google.inject.Inject
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.octanner.image.api.ImageService
import com.octanner.user.api.{User, UserService}
import converter.ServiceCallConverter._

import scala.concurrent.ExecutionContext.Implicits.global

class UserServiceImpl @Inject()(imageService: ImageService) extends UserService {
  override def getUser(id: String): ServiceCall[NotUsed, User] = { request =>
    println(s"Calling UserAPI: $id")
    val futureImage = imageService.getImage(id).invoke()
    futureImage.map { image =>
      User(id.toLong,
        s"firstName$id",
        s"lastName$id",
        s"firstName$id.lastName$id@octanner.com",
        image)
    }
  }
}
