package com.octanner.image.impl.dao

import anorm.SqlParser._
import anorm._
import com.google.inject.Inject
import com.octanner.image.api.Image
import play.api.db.DBApi

class ImageDao  @Inject() (dBApi: DBApi) {
  private val db = dBApi.database("default")
  val select = "SELECT USER_ID, URL_SMALL, URL_LARGE FROM IMAGES"

  val imageParser = get[Long]("USER_ID") ~
    get[String]("URL_SMALL") ~
    get[String]("URL_LARGE") map {
    case userId ~ urlSmall ~ urlLarge => Image(userId, urlSmall, urlSmall)
  }

  def getByUserId(userId: Long): Option[Image] = db.withConnection { implicit connection =>
    SQL(s"$select WHERE USER_ID={userId}").on('userId -> userId).as(imageParser.singleOpt)
  }

  def isHealthy: Boolean = db.withConnection { implicit connection =>
    SQL("SELECT 2 + 2 as FOUR").as(SqlParser.int("FOUR").single) == 4
  }
}
