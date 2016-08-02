package com.octanner.user.impl

import akka.{Done, NotUsed}
import com.google.inject.Inject
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.lightbend.lagom.javadsl.api.transport.NotFound
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession
import com.octanner.image.api.ImageService
import com.octanner.user.api.{User, UserService}
import converter.ServiceCallConverter._
import scala.concurrent.ExecutionContext

// To access an entity from a service implementation you first need to inject the PersistentEntityRegistry
class UserServiceImpl @Inject()(imageService: ImageService,
                                persistentEntities: PersistentEntityRegistry,
                                db: CassandraSession)(implicit ec: ExecutionContext) extends UserService {

  // And at startup (in the constructor) register the class that implements the PersistentEntity
  persistentEntities.register(classOf[UserEntity])

  override def getUser(id: Long): ServiceCall[NotUsed, User] = { request =>
    println(s"Calling GET /api/users/$id")
    val futureImage = imageService.getImage(id).invoke()
    futureImage flatMap { image =>
      val futureUserReply = userEntityRef(id).ask[GetUserReply, GetUser](GetUser())
      futureUserReply map { userReply =>
        val user: User = userReply.user.getOrElse(throw new NotFound(s"user $id not found"))
        user.copy(image = Some(image))
      }
    }
  }

  override def createUser(): ServiceCall[User, NotUsed] = { request =>
    println(s"Calling POST /api/users")
    userEntityRef(request.userId).ask[Done, CreateUser](CreateUser(request))
  }

  private def userEntityRef(userId: Long) =
    persistentEntities.refFor(classOf[UserEntity], userId.toString)

}
