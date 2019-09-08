package dev.kaplan.analysis

import dev.kaplan.event.{UserEvent, View}
import org.apache.flink.api.scala._

case class UniqueProductViewCount(productId: Long, uniqueViews: Int)

object UniqueProductViewCount {
  def findUniqueProductViews(events: DataSet[UserEvent]): DataSet[UniqueProductViewCount] =
    events
      .filter(_.eventName == View)
      .groupBy(_.productId)
      .reduceGroup(reduceByUser _)
  
  private def reduceByUser(groupedEvents: Iterator[UserEvent]): UniqueProductViewCount = {
    val events = groupedEvents.toList
    val uniqueViews = events.map(_.userId).distinct.length
    val productId = events.head.productId
    
    UniqueProductViewCount(productId, uniqueViews)
  }
}
