package com.sample.customconnector.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
public class Completion implements Serializable {
    String text;
}