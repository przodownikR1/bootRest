package pl.java.scalatech.domain.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConvertor implements AttributeConverter<LocalDateTime, Date>
{

  @Override
  public Date convertToDatabaseColumn(LocalDateTime date) {
    return Date.from(Instant.from(date));
  }

  @Override
  public LocalDateTime convertToEntityAttribute(Date value) {
    return LocalDateTime.from(value.toInstant());
  }
}