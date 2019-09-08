package dev.kaplan.analysis

import dev.kaplan.event._
import org.apache.flink.api.scala._
import org.apache.flink.api.scala.extensions._

case class UserEventCount(eventName: String, count: Int)

object UserEventCount {
  def countEventsForUser(userId: Long, events: DataSet[RawUserEvent]): DataSet[UserEventCount] =
    events
      .filter(_.userId == userId)
      .map(event => event.eventName -> 1)
      .groupBy(0)
      .sum(1)
      .mapWith { case (eventName, count) => UserEventCount(eventName, count)}
  }
