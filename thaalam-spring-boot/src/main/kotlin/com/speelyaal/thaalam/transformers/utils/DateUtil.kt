package com.speelyaal.thaalam.transformers.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


class DateUtil {

    companion object {
        fun asDate(localDate: LocalDate): Date? {
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        }

        fun asDate(localDateTime: LocalDateTime): Date? {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
        }

        fun asLocalDate(date: Date): LocalDate? {
            return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
        }

        fun asLocalDateTime(date: Date): LocalDateTime? {
            return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
    }
}