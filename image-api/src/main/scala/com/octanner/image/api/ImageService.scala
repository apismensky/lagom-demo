package com.octanner.image.api

import akka.NotUsed
import com.lightbend.lagom.javadsl.api.ScalaService._
import com.lightbend.lagom.javadsl.api.{Descriptor, Service, ServiceCall}


trait ImageService extends Service {

  def getImage(id: Long): ServiceCall[NotUsed, Image]
  def health(): ServiceCall[NotUsed, String]

  override def descriptor(): Descriptor = {
    named("imageapi").`with`(
      pathCall("/api/health", health _),
      pathCall("/api/users/:id/images", getImage _)
    ).withAutoAcl(true)
  }

}
