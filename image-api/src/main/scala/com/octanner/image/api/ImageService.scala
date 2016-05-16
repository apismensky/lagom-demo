package com.octanner.image.api

import akka.NotUsed
import com.lightbend.lagom.javadsl.api.ScalaService._
import com.lightbend.lagom.javadsl.api.{Descriptor, Service, ServiceCall}


trait ImageService extends Service {

  def getImage(id: String): ServiceCall[NotUsed, Image]

  override def descriptor(): Descriptor = {
    named("imageapi").`with`(
      pathCall("/api/images/:id", getImage _)
    ).withAutoAcl(true)
  }

}
