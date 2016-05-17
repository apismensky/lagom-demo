package com.octanner.image.impl

import javax.inject.Inject

import akka.NotUsed
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.octanner.image.api.{Image, ImageService}
import com.octanner.image.impl.dao.ImageDao
import converter.ServiceCallConverter._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class ImageServiceImpl @Inject()(imageDao: ImageDao) extends ImageService {
  override def getImage(id: Long): ServiceCall[NotUsed, Image] = { request =>
    println(s"Calling GET /api/images/$id")
    val image = imageDao.getByUserId(id).getOrElse(throw new IllegalArgumentException(s"Can not find image for userId: $id"))
    Future(image)
  }

  override def health(): ServiceCall[NotUsed, String] = { request =>
    if (imageDao.isHealthy) Future("All good") else Future("Ops!!!")
  }
}
