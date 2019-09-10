package dev.kaplan.analysis

import dev.kaplan.event.{EventName, UserEvent}
import org.apache.flink.api.scala._

object UniqueEventCount {
  def findUniqueEventCounts(events: DataSet[UserEvent]): DataSet[(EventName, Int)] =
    events
      .distinct
      .map(_.eventName -> 1)
      .groupBy(0)
      .sum(1)
}
