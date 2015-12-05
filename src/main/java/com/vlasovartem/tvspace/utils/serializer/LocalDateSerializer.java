package com.vlasovartem.tvspace.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by artemvlasov on 05/12/15.
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(value.format(DateTimeFormatter.ofPattern("d MMM uuuu", Locale.getDefault())));
    }

    @Override
    public Class<LocalDate> handledType() {
        return LocalDate.class;
    }
}
