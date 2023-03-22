package com.sample.customconnector.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CompletionResponse implements Serializable{
    @JsonProperty("choices")
    List<Completion> textChoices = []

}
