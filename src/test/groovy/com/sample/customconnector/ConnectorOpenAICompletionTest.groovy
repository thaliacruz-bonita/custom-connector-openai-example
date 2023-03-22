package com.sample.customconnector

import org.bonitasoft.engine.connector.ConnectorValidationException

import spock.lang.Specification

class ConnectorOpenAICompletionTest extends Specification {

    def should_throw_exception_if_mandatory_input_is_missing() {
        given: 'A connector without input'
        def connector = new ConnectorOpenAICompletion()

        when: 'Validating inputs'
        connector.validateInputParameters()

        then: 'ConnectorValidationException is thrown'
        thrown ConnectorValidationException
    }

    def should_throw_exception_if_mandatory_input_is_empty() {
        given: 'A connector without an empty input'
        def connector = new ConnectorOpenAICompletion()
        connector.setInputParameters([(ConnectorOpenAICompletion.NOTES_INPUT):''])

        when: 'Validating inputs'
        connector.validateInputParameters()

        then: 'ConnectorValidationException is thrown'
        thrown ConnectorValidationException
    }

    def should_throw_exception_if_mandatory_input_is_not_a_string() {
        given: 'A connector without an integer input'
        def connector = new ConnectorOpenAICompletion()
        connector.setInputParameters([(ConnectorOpenAICompletion.NOTES_INPUT):38])

        when: 'Validating inputs'
        connector.validateInputParameters()

        then: 'ConnectorValidationException is thrown'
        thrown ConnectorValidationException
    }
}