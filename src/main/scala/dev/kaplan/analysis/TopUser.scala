package dev.kaplan.analysis

import dev.kaplan.event._
import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala._
import org.apache.flink.api.scala.extensions._

object TopUser {
  case class EventCounts(userId: Long, add: Int, view: Int, click: Int, remove: Int)
  
  def findTopUsersByMostEvents(numOfUsers: Int, events: DataSet[UserEvent]): DataSet[(Long, Int)] =
    events
      .groupBy(_.userId)
      .reduceGroup(x => minEventCount(x))
      .filterWith { case (_, count) => count > 0}
      .sortPartition(1, Order.DESCENDING)
      .first(numOfUsers)
      .sortPartition(1, Order.DESCENDING)
  
  private def minEventCount(userEvents: Iterator[UserEvent]): (Long, Int) = {
    val events = userEvents.toList
    val userId = events.head.userId
    
    val eventCounts: EventCounts = events.foldLeft(EventCounts(userId, 0, 0, 0, 0)) { (counts, event) =>
      event.eventName match {
        case Add    => counts.copy(add = counts.add + 1)
        case View   => counts.copy(view = counts.view + 1)
        case Click  => counts.copy(click = counts.click + 1)
        case Remove => counts.copy(remove = counts.remove + 1)
      }
    }
    
    val EventCounts(_, add, view, click, remove) = eventCounts
    
    (userId, add min view min click min remove)
  }
}
