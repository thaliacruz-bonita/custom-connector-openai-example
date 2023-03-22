package com.sample.customconnector.model

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

class CompletionRequest {
    def static final DEFAULT_MODEL = "text-davinci-003"

    String model = DEFAULT_MODEL;
    String prompt;

    CompletionRequest(String notes)
    {
        prompt = notes
    }
}
