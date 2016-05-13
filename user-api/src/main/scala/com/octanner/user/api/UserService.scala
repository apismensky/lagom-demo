package com.octanner.user.api

import akka.NotUsed
import com.lightbend.lagom.javadsl.api.{ServiceCall, Descriptor, Service}
import com.lightbend.lagom.javadsl.api.ScalaService._


trait UserService extends Service {

  def getUser(id: String): ServiceCall[NotUsed, User]

  override def descriptor(): Descriptor = {
    named("userapi").`with`(
      pathCall("/api/users/:id", getUser _)
    ).withAutoAcl(true)
    // @formatter:on
  }

}
