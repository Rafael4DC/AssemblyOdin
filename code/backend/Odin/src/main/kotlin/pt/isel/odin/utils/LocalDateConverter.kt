package pt.isel.odin.utils

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.sql.Date

@Converter(autoApply = true)
class LocalDateConverter : AttributeConverter<LocalDate, Date> {

    override fun convertToDatabaseColumn(localDate: LocalDate?): Date? {
        return localDate?.toJavaLocalDate()?.let { Date.valueOf(it) }
    }

    override fun convertToEntityAttribute(sqlDate: Date?): LocalDate? {
        return sqlDate?.toLocalDate()?.toKotlinLocalDate()
    }
}