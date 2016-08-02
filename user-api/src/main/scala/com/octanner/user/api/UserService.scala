package com.octanner.user.api

import akka.NotUsed
import com.lightbend.lagom.javadsl.api.{ServiceCall, Descriptor, Service}
import com.lightbend.lagom.javadsl.api.ScalaService._


trait UserService extends Service {

  def getUser(id: Long): ServiceCall[NotUsed, User]

  /**
     curl -v -XPOST -d '{"userId":1,"firstName":"Ivan","lastName":"Drago","email":"ivan.drago@octanner.com"}' http://localhost:9000/api/users
     curl -v -XPOST -d '{"userId":2,"firstName":"Rocky","lastName":"Balboa","email":"rocky.balboa@octanner.com"}' http://localhost:9000/api/users
    * @return
    */
  def createUser(): ServiceCall[User, NotUsed]

  override def descriptor(): Descriptor = {
    named("userapi").`with`(
      pathCall("/api/users/:id", getUser _),
      namedCall("/api/users", createUser _)
    ).withAutoAcl(true)
    // @formatter:on
  }

}
