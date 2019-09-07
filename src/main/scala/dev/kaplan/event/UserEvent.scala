package dev.kaplan.event

case class UserEvent(date: Long, productId: Long, eventName: EventName, userId: Long)
