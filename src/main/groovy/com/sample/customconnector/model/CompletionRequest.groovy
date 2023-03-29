package com.sample.customconnector.model

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

class CompletionRequest {
    def static final DEFAULT_MODEL = "text-davinci-003"
    def static final DEFAULT_MAX_TOKENS = 2048

    String model = DEFAULT_MODEL;
    String prompt;
    int max_tokens = DEFAULT_MAX_TOKENS;

    CompletionRequest(String notes)
    {
        prompt = notes
    }
}
