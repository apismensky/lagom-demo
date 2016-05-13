package com.octanner.image.impl

import akka.NotUsed
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.octanner.image.api.{Image, ImageService}
import converter.ServiceCallConverter._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class ImageServiceImpl extends ImageService {
  override def getImage(id: String): ServiceCall[NotUsed, Image] = { request =>
    println(s"Calling ImageAPI: $id")
    Future(Image(id.toLong, s"http://site.com/image/small/$id", s"http://site.com/image/large/$id"))
  }
}
