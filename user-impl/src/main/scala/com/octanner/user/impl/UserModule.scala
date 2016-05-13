package com.octanner.user.impl

import com.google.inject.AbstractModule
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport
import com.octanner.user.api.UserService


class UserModule extends AbstractModule with ServiceGuiceSupport {
  override protected def configure(): Unit = {
    bindServices(serviceBinding(classOf[UserService], classOf[UserServiceImpl]))
  }
}
