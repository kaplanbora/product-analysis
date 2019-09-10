package dev.kaplan.analysis

import dev.kaplan.event._
import org.apache.flink.api.scala._

object UserEventCount {
  def countEventsForUser(userId: Long, events: DataSet[UserEvent]): DataSet[(EventName, Int)] =
    events
      .filter(_.userId == userId)
      .map(_.eventName -> 1)
      .groupBy(0)
      .sum(1)
  }
