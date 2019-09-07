package dev.kaplan.event

case class RawUserEvent(date: Long, productId: Long, eventName: String, userId: Long) {
  def enrich = UserEvent(date, productId, EventName.fromString(eventName), userId)
}
