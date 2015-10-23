@email
Feature: Verify email functionality

  Scenario: Successful email registration
    Given the MailCatcher is running
    And there is a valid template called "test-html"
    And there is a valid template called "test-text"
    When I send a POST request to "/outbound" with the following:
     """
     {
      "sender": "example@example.org",
      "subject": "Test Email",
      "htmlTemplate": "test-html",
      "textTemplate": "test-text",
      "variables": {"user":"user"},
      "recipients": [
      "test@example.com"
     ]
    }
    """
    Then the response code should be "204"
    When I send a GET request to "/messages" on MailCatcher client
    Then response should contain an email with subject "Test Email"


  Scenario: Unable to register a user with an invalid email
    Given the MailCatcher is running
    When I send a POST request to "/outbound" with the following:
     """
     {
      "sender": "exampleexample.org",
      "subject": "Test Email",
      "htmlTemplate": "test-html",
      "textTemplate": "test-text",
      "variables": {"user":"user"},
      "recipients": [
      "test@example.com"
     ]
    }
    """
    Then the response code should be "422"
    And the error message must be "sender not a well-formed email address"


  Scenario: email registration failure - invalid json
    Given the MailCatcher is running
    When I send a POST request to "/outbound" with the following:
     """
     {
      "sender": "example@example.org",
      "subject": "Test Email",
      "htmlTemplate": "test-html"
      "textTemplate": "test-text",
      "variables": {"user":"user"},
      "recipients": [
      "test@example.com"
     ]
    }
    """
    Then the response code should be "400"
    And the error message must be "Unable to process JSON"
