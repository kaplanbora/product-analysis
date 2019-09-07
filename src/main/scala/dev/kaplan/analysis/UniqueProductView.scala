package dev.kaplan.analysis

import dev.kaplan.event.{UserEvent, View}
import org.apache.flink.api.scala._

case class UniqueProductView(productId: Long, uniqueViews: Int)

object UniqueProductView {
  def findUniqueProductViews(cases: DataSet[UserEvent]): DataSet[UniqueProductView] =
    cases
      .filter(_.eventName == View)
      .groupBy(_.productId)
      .reduceGroup(reduceByUser _)
  
  private def reduceByUser(groupedEvents: Iterator[UserEvent]): UniqueProductView = {
    val events = groupedEvents.toList
    val uniqueViews = events.map(_.userId).distinct.length
    val productId = events.head.productId
    
    UniqueProductView(productId, uniqueViews)
  }
}
