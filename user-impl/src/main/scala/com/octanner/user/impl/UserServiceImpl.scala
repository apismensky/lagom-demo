package com.octanner.user.impl

import akka.{Done, NotUsed}
import com.google.inject.Inject
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.lightbend.lagom.javadsl.api.transport.NotFound
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry
import com.lightbend.lagom.javadsl.persistence.cassandra.{CassandraReadSide, CassandraSession}
import com.octanner.image.api.ImageService
import com.octanner.user.api.{User, UserService}
import converter.ServiceCallConverter._
import scala.concurrent.ExecutionContext
import scala.collection.JavaConverters._

// To access an entity from a service implementation you first need to inject the PersistentEntityRegistry
class UserServiceImpl @Inject()(imageService: ImageService,
                                persistentEntities: PersistentEntityRegistry,
                                readSide: CassandraReadSide,
                                db: CassandraSession)(implicit ec: ExecutionContext) extends UserService {

  // And at startup (in the constructor) register the class that implements the PersistentEntity
  persistentEntities.register(classOf[UserEntity])

  //readSide.register(classOf[UserEventProcessor])

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

  override def users() = { request =>
    db.selectAll("SELECT userId, firstName, lastName, email FROM users;").map { jrows =>
      val rows = jrows.asScala.toVector
      rows.map { row =>  User(row.getLong("userId"), row.getString("firstName"), row.getString("lastName"), row.getString("email"), None)}
    }
  }
}
