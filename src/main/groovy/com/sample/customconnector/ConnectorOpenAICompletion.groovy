package com.sample.customconnector

import com.sample.customconnector.model.CompletionRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorException;
import org.bonitasoft.engine.connector.ConnectorValidationException;

import groovy.util.logging.Slf4j
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Slf4j
class ConnectorOpenAICompletion extends AbstractConnector {
    
    def static final NOTES_INPUT = "notes"
    def static final URL_INPUT = "url"
    def static final TOKEN_INPUT = "token"
    def static final COMPLETION_OUTPUT = "text"

    def OpenAICompletionService service
    /**
     * Perform validation on the inputs defined on the connector definition (src/main/resources/connector-openai-text-davinci-003.def)
     * You should:
     * - validate that mandatory inputs are presents
     * - validate that the content of the inputs is coherent with your use case (e.g: validate that a date is / isn't in the past ...)
     */
    @Override
    void validateInputParameters() throws ConnectorValidationException {
        checkMandatoryStringInput(NOTES_INPUT)
        checkMandatoryStringInput(URL_INPUT)
        checkMandatoryStringInput(TOKEN_INPUT)
    }
    
    def checkMandatoryStringInput(inputName) throws ConnectorValidationException {
        def value = getInputParameter(inputName)
        if (value in String) {
            if (!value) {
                throw new ConnectorValidationException(this, "Mandatory parameter '$inputName' is missing.")
            }
        } else {
            throw new ConnectorValidationException(this, "'$inputName' parameter must be a String")
        }
    }
    
    /**
     * Core method:
     * - Execute all the business logic of your connector using the inputs (connect to an external service, compute some values ...).
     * - Set the output of the connector execution. If outputs are not set, connector fails.
     */
    @Override
    void executeBusinessLogic() throws ConnectorException {
        def notes = getInputParameter(NOTES_INPUT)
        def token = getInputParameter(TOKEN_INPUT)
        log.info "$NOTES_INPUT : $notes"
        def requestBody = new CompletionRequest(notes)

        def response = getService().call(token, requestBody).execute()
        if (response.isSuccessful()){
            def choices = response.body().getTextChoices()
            if (!choices.isEmpty()){
                setOutputParameter(COMPLETION_OUTPUT, choices[0])
            }
            else{
                throw new ConnectorException("could not generate feedback based on $notes")
            }
        }
        else {
            throw new ConnectorException(response.message())
        }
    }
    
    /**
     * [Optional] Open a connection to remote server
     */
    @Override
    void connect() throws ConnectorException{
        def logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        def httpClient = createHttpClient(logging)
        service = createService(httpClient, getInputParameter(URL_INPUT))

    }

    static OkHttpClient createHttpClient(okhttp3.Interceptor...interceptors){
        def clientBuilder = new OkHttpClient.Builder()
        if (interceptors){
            interceptors.each {clientBuilder.interceptors().add(it)}
        }
        clientBuilder.build()
    }
    static OpenAICompletionService createService(OkHttpClient client, String baseUrl){
        new Retrofit.Builder()
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(OpenAICompletionService.class)
    }

    /**
     * [Optional] Close connection to remote server
     */
    @Override
    void disconnect() throws ConnectorException{}
}
