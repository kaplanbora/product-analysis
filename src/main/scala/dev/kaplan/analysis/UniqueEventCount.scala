package dev.kaplan.analysis

import dev.kaplan.event.RawUserEvent
import org.apache.flink.api.scala._
import org.apache.flink.api.scala.extensions._

case class UniqueEventCount(eventName: String, count: Int)

object UniqueEventCount {
  def findUniqueEventCounts(events: DataSet[RawUserEvent]): DataSet[UniqueEventCount] =
    events
      .distinct
      .map(event => event.eventName -> 1)
      .groupBy(0)
      .sum(1)
      .mapWith { case (event, count) => UniqueEventCount(event, count) }
}
