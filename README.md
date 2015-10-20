# Email API

[![Build Status](https://travis-ci.org/UKHomeOffice/email-api.svg?branch=master)](https://travis-ci.org/UKHomeOffice/email-api)

This is an service that will allow you to send templated emails via a RESTful API. 

## Getting Started

These instructions will cover usage information for developing stand alone, and running the docker container.

### Clients

* [Java](https://github.com/UKHomeOffice/email-api-client-java)

### Usage

The first step to using this API is to create some templates for it to use. To do this we use 
[freemarker](http://freemarker.org/). An example of this is below.

```freemarker
Hello ${user},

You have received a test email.

From
The Email API.
```

The location that this application looks for templates is set in the `TEMPLATE_PATH` environment variable. 

The template is then referred to by the filename when we try to load it. If we were to put it as "test-text" in the 
directory referred to by that environment variable, we could use it like below.

It's worth mentioning that this email service will send an MIME email with both text and html sections. The Html 
templates work in the exact same way, and you could even use the same template for both if you really wanted to. Below 
is an example of that.



To send the email, send a http `POST` request to the email service `http://localhost:8080/outbound` (assuming you're 
running the service at localhost on the default port).

```json
{
  "sender": "example@example.org",
  "subject": "Subject of the email",
  "htmlTemplate": "test-html",
  "textTemplate": "test-text",
  "variables": {"user":"user"},
  "recipients": [
    "test@example.com"
  ]
}
```

This will send an email.

Most of these are self explanatory, however I just want to mention that the "variables" parameter can contain any JSON,
data, including objects, and arrays (on top of normal things like Numbers and Strings).

If you had made your email template the same as the one below, you should get an email which looks like the one below.

```
to: test@example.com
from: example@example.org
Subject: Subject of the email

Hello ${user},

You have received a test email.

From
The Email API.
```

For further documentation and a test client see the swagger documentation at `http://localhost:8080/swagger` 

### Running Stand Alone

You can run this application as a stand alone Java application

#### Building

The tests will be automatically ran.

```shell
./gradlew install
```

#### Running

We configure the application through environment variables. See below.

```shell
SMTP_REQUIRE_TSL=false \
SMTP_START_TSL_ENABLED=false \
SMTP_ON_SSL_CONNECT=false \
SMTP_PASSWORD="" SMTP_USERNAME="" \
SMTP_PORT=1025 \
SMTP_HOSTNAME=localhost \
TEMPLATE_PATH=/tmp \
./build/install/email-api/bin/email-api server src/main/resources/configuration.yaml 
```

More details on these environment variables can be found in the docker section.

#### Tests

```shell
./gradlew test
```

### Docker

#### Prerequisites

In order to run this container you'll need docker installed.

* [Windows](https://docs.docker.com/windows/started)
* [OS X](https://docs.docker.com/mac/started/)
* [Linux](https://docs.docker.com/linux/started/)

#### Usage

##### Kubernetes Examples

You can find Kubernetes examples in the [k8s directory](k8s).

##### Container Parameters

If you run the application without parameters it'll start the application.

```shell
docker run quay.io/ukhomeofficedigital/email-api:v1.0.0
```

Otherwise it'll run the command you provide. For example the command below will run bash.

```shell
docker run quay.io/ukhomeofficedigital/email-api:v1.0.0 bash
```

##### Environment Variables

* `SMTP_HOSTNAME` The hostname of the SMTP server to use.
* `SMTP_PORT` Server port (Default: 25)
* `SMTP_REQUIRE_TSL` Require TSL (Default: false)
* `SMTP_START_TSL_ENABLED` Start TSL Enabled (Default: false)
* `SMTP_ON_SSL_CONNECT` SSL On Connect (Default: false)
* `SMTP_USERNAME` If this is populated we will attempt to authenticate with the SMTP server (Default: "")
* `SMTP_PASSWORD` Used if SMTP_USERNAME is set (Default: "")
* `SMTP_PASSWORD_PATH` If used this will overwrite SMTP_PASSWORD with the contents of this file.
* `TEMPLATE_PATH` Where to look for templates to use (Default: "/templates")

##### Useful File Locations

* `/templates` - Where to put your Freemarker templates

## Built With

* Dropwizard 0.8.2
* Freemarker 2.3.23

## Find Us

* [GitHub](https://github.com/UKHomeOffice/email-api)
* [Quay.io](https://quay.io/repository/ukhomeofficedigital/email-api)

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the 
[tags on this repository](https://github.com/UKHomeOffice/email-api/tags). 

## Authors

See the list of [contributors](https://github.com/UKHomeOffice/email-api/contributors) who 
participated in this project.

## License

This project is licensed under the GPL v2 License - see the [LICENSE.md](LICENSE.md) file for details.
