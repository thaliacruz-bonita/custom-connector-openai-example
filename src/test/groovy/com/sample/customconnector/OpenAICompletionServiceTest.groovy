package com.sample.customconnector

import com.sample.customconnector.model.CompletionRequest
import com.sample.customconnector.model.CompletionResponse
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import spock.lang.Ignore
import spock.lang.Specification

class OpenAICompletionServiceTest extends Specification{
    def static final TOKEN = "Bearer"


    @Ignore
    def should_retrieve_feedback_data_using_retrofit(){
        given: 'A service'
        def httpClient = ConnectorOpenAICompletion.createHttpClient(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        def service = ConnectorOpenAICompletion.createService(httpClient, "https://api.openai.com/")

        when: 'sending test notes'
        def request = new CompletionRequest("This is a test")
        def call = service.call(TOKEN, request)
        def Response<CompletionResponse> response = call.execute()

        then: 'Should contain test answer'
        assert response.isSuccessful()
        assert response.body.textChoices.size() == 1
    }
}
