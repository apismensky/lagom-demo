/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.octanner.user.impl

import java.util.UUID
import javax.inject.Inject

import akka.Done
import com.datastax.driver.core.PreparedStatement
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag
import com.lightbend.lagom.javadsl.persistence.cassandra.{CassandraReadSideProcessor, CassandraSession}
import converter.ServiceCallConverter._

import scala.collection.JavaConverters._
import scala.collection.immutable.Seq
import scala.concurrent.{Future, ExecutionContext}

//class UserEventProcessor @Inject()(implicit ec: ExecutionContext) extends CassandraReadSideProcessor[UserEvent] {

//  override def aggregateTag: AggregateEventTag[UserEvent] = UserEvent.Tag
//
//  override def prepare(session: CassandraSession) = {
//    // @formatter:off
//    prepareCreateTables(session).thenCompose(b =>
//    prepareWriteUsers(session).thenCompose(c =>
//    selectOffset(session)))
//    // @formatter:on
//  }
//
//
//  private def prepareCreateTables(session: CassandraSession) = {
//    // @formatter:off
//    session.executeCreateTable(
//      """CREATE TABLE IF NOT EXISTS users
//        | (userId bigint, firstName text, lastName text, email text,
//        | PRIMARY KEY (userId))
//        |""".stripMargin)
//      .thenCompose(a => session.executeCreateTable(
//        "CREATE TABLE IF NOT EXISTS friend_offset ("
//          + "partition int, offset timeuuid, "
//          + "PRIMARY KEY (partition))"))
//    // @formatter:on
//  }
//
//  private def prepareWriteUsers(session: CassandraSession): Future[Done.type] = {
//    val statement = session.prepare("INSERT INTO user (userId, followedBy) VALUES (?, ?)")
//    statement.map(ps => {
//      Done
//    })
//  }
//
//  override def defineEventHandlers(builder: EventHandlersBuilder): EventHandlers = {
//    builder.setEventHandler(classOf[UserCreated], processFriendChanged)
//    builder.build()
//  }
//
//  private def processFriendChanged(event: UserCreated, offset: UUID) = {
//    val bindWriteFollowers = writeFollowers.bind()
//    bindWriteFollowers.setString("userId", event.friendId)
//    bindWriteFollowers.setString("followedBy", event.userId)
//    val bindWriteOffset = writeOffset.bind(offset)
//    completedStatements(Seq(bindWriteFollowers, bindWriteOffset).asJava)
//  }

//}
