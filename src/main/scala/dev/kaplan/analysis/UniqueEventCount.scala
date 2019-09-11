package dev.kaplan.analysis

import dev.kaplan.event.{EventName, UserEvent}
import org.apache.flink.api.scala._

object UniqueEventCount {
  def findUniqueEventCounts(events: DataSet[UserEvent]): DataSet[(EventName, Int)] =
    events
      .groupBy(_.eventName)
      .reduceGroup { groupedEvents =>
        val events = groupedEvents.toList
        val eventName = events.head.eventName
        val uniqueCount = events.map(_.userId).distinct.length
        eventName -> uniqueCount
      }
}
