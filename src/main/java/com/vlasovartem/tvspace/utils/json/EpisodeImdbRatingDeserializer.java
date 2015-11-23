package com.vlasovartem.tvspace.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by artemvlasov on 23/11/15.
 */
public class EpisodeImdbRatingDeserializer extends JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if(p.getText().matches("N/A")) {
            return 0.0;
        }
        return Double.valueOf(p.getText());
    }
}
