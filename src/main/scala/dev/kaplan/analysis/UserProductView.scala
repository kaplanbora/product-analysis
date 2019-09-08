package dev.kaplan.analysis

import dev.kaplan.event.{UserEvent, View}
import org.apache.flink.api.scala._

object UserProductView {
  def findViewedProductsForUser(userId: Long, events: DataSet[UserEvent]): DataSet[Long] =
    events
      .filter(event => event.userId == userId && event.eventName == View)
      .map(_.productId)
}
