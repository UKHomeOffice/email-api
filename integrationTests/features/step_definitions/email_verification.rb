
Given(/^I send a POST request to "([^"]*)" with the following:$/) do |path, json_body|
  @response = HTTParty.post('http://localhost:8080'+ path, :body => json_body, :headers => {'Content-Type' => 'application/json'})
end

Then(/^the response code should be "([^"]*)"$/) do |expected_code|
  @response.code.should == expected_code.to_i
end

Given(/^there is a valid template called "([^"]*)"$/) do |expected_template_name|
  File.write(File.join("/tmp", expected_template_name), <<EXAMPLETEMPLATE
Hello ${user},

You have received a test email.

From
The Email API.
EXAMPLETEMPLATE
)

end

Given(/^the MailCatcher is running$/) do
  HTTParty.get('http://127.0.0.1:1080/').code.should == 200
end

When(/^I send a GET request to "([^"]*)" on MailCatcher client$/) do |path|
  @last_response = HTTParty.get('http://127.0.0.1:1080/' + path)
end

Then(/^response should contain an email with subject "([^"]*)"$/) do |email_subject|
  @last_response.body.should include(email_subject)
end

Then(/^the error message must be "([^"]*)"$/) do |error_message|
  @response.body.should include(error_message)
end
