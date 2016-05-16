package com.octanner.user.impl

import akka.NotUsed
import com.google.inject.Inject
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.lightbend.lagom.javadsl.api.transport.NotFound
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry
import com.octanner.image.api.ImageService
import com.octanner.user.api.{User, UserService}
import converter.ServiceCallConverter._
import scala.concurrent.ExecutionContext

class UserServiceImpl @Inject()(imageService: ImageService,
                                persistentEntities: PersistentEntityRegistry)(implicit ec: ExecutionContext) extends UserService {

  persistentEntities.register(classOf[UserEntity])

  override def getUser(id: String): ServiceCall[NotUsed, User] = { request =>
    println(s"Calling UserAPI: $id")

    val futureImage = imageService.getImage(id).invoke()
    futureImage.flatMap { im =>
      friendEntityRef(id).ask[GetUserReply, GetUser](GetUser())
        .map {_.user.getOrElse(throw new NotFound(s"user $id not found")).copy(image = im)}
    }
  }

  private def friendEntityRef(userId: String) =
    persistentEntities.refFor(classOf[UserEntity], userId)

}
