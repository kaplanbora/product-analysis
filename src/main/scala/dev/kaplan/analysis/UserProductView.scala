package dev.kaplan.analysis

import dev.kaplan.event.{UserEvent, View}
import dev.kaplan.util.Extensions.LongOps
import org.apache.flink.api.scala._

object UserProductView {
  def findViewedProductsForUser(userId: Long, events: DataSet[UserEvent]): DataSet[Tuple1[Long]] =
    events
      .filter(event => event.userId == userId && event.eventName == View)
      .map(_.productId.tupled)
}
