package com.octanner.user.impl

import akka.{Done, NotUsed}
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

  override def getUser(id: Long): ServiceCall[NotUsed, User] = { request =>
    println(s"Calling GET /api/users/$id")
    imageService.getImage(id).invoke() flatMap { im =>
      userEntityRef(id).ask[GetUserReply, GetUser](GetUser())
        .map {_.user.getOrElse(throw new NotFound(s"user $id not found")).copy(image = Some(im))}
    }
  }

  override def createUser(): ServiceCall[User, NotUsed] = { request =>
    println(s"Calling POST /api/users")
    userEntityRef(request.userId).ask[Done, CreateUser](CreateUser(request))
  }

  private def userEntityRef(userId: Long) =
    persistentEntities.refFor(classOf[UserEntity], userId.toString)

}
