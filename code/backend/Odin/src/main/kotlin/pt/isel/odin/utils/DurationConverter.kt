package pt.isel.odin.utils

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Converter(autoApply = true)
class DurationConverter : AttributeConverter<Duration, Long> {

    override fun convertToDatabaseColumn(duration: Duration?): Long? {
        return duration?.inWholeMilliseconds
    }

    override fun convertToEntityAttribute(durationInMillis: Long?): Duration? {
        return durationInMillis?.milliseconds
    }
}
