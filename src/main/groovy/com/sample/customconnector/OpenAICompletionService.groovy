package com.sample.customconnector

import com.sample.customconnector.model.CompletionRequest
import com.sample.customconnector.model.CompletionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAICompletionService {
    @Headers("Accept:application/json")
    @POST("v1/completions")
    Call<CompletionResponse> call(@Header("Authorization") String authorization,
                                  @Body CompletionRequest request);

}