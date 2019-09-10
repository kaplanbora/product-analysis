package dev.kaplan.analysis

import dev.kaplan.event.{UserEvent, View}
import org.apache.flink.api.scala._

object UniqueProductViewCount {
  def findUniqueProductViews(events: DataSet[UserEvent]): DataSet[(Long, Int)] =
    events
      .filter(_.eventName == View)
      .groupBy(_.productId)
      .reduceGroup(reduceByUser _)
  
  private def reduceByUser(groupedEvents: Iterator[UserEvent]): (Long, Int) = {
    val events = groupedEvents.toList
    val uniqueViews = events.map(_.userId).distinct.length
    val productId = events.head.productId
    
    productId -> uniqueViews
  }
}
