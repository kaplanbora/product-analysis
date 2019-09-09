package dev.kaplan.analysis

import dev.kaplan.event.{EventName, UserEvent}
import org.apache.flink.api.scala._
import org.apache.flink.api.scala.extensions._

case class UniqueEventCount(eventName: EventName, count: Int)

object UniqueEventCount {
  def findUniqueEventCounts(events: DataSet[UserEvent]): DataSet[UniqueEventCount] =
    events
      .distinct
      .map(_.eventName -> 1)
      .groupBy(0)
      .sum(1)
      .mapWith { case (event, count) => UniqueEventCount(event, count) }
}
