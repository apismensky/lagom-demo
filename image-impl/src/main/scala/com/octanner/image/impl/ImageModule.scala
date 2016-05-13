package com.octanner.image.impl

import com.google.inject.AbstractModule
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport
import com.octanner.image.api.ImageService


class ImageModule extends AbstractModule with ServiceGuiceSupport {
  override protected def configure(): Unit = {
    bindServices(serviceBinding(classOf[ImageService], classOf[ImageServiceImpl]))
  }
}
