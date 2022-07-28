# General Questions

## What are you?

_Give an overview of what this component is, why its important, etc_

## What is a ...

_Explain any domain concepts used in the component in subsequent sub-sections here_

## What do you do?

_Explain, in detail, what this component does and how: use some examples if possible_

## Do you have any conventions?

_Explain any coding conventions used here which affect the behaviour/maintenance of the component_

# Deployment

Here's my basic needs from any machine you deploy me in context of:

- Java 11 (to run my app!)
- [Docker](https://www.docker.com/get-started) (to run unit tests, and to deploy to the live JLR environment)

## Kafka

If you want to deploy me, you'll always need a pre-existing Kafka cluster for me to connect to. This cluster
should contain the relevant topic that I need to connect to in order to produce output.

You may not always need to deploy me in context of secure Kafka clusters. Consider my unit tests: these run 
in a local Docker container context, so connecting to a secure Kafka cluster isn't necessary. Therefore, to 
ensure that you can run me in secure and unsecure Kafka cluster contexts, you'll need to specify some profiles
to do this!

### Deploying against Kafka hosted on machine

1. [Start Kafka](https://kafka.apache.org/quickstart) on the local machine
1. Create the topic required by this application via the broker started in the previous step

### Deploying against Kafka hosted in Minikube

:construction: _Setting up Kafka to work with the Spring Boot app "out-off-the-box" is not supported by this template, yet!_ :construction:

## Spring Boot Application

After ensuring that the Kafka cluster I'm to connect to is set-up correctly, you can then run my Spring Boot application to
process some data!

### Deploying on own machine (no Minikube)

Run `./mvnw spring-boot:run` via a terminal from my root directory on the machine I'm being deployed from! Without 
specifying any profiles, I will try to connect to a Kafka instance running on the machine you issued that command from 
(provided you didn't alter any of the default Apache Kafka configuration when setting the local cluster up!)

### Deploying on own machine (Minikube)

1. Since my Docker images are built via [JIB](https://github.com/GoogleContainerTools/jib), you'll need to build my image via
JIB, in the context of Minikube (since Minikube has its own Docker registry, separate to your local machine's!). Instructions on
how to do this can be found [here](https://github.com/GoogleContainerTools/jib/blob/master/jib-maven-plugin/README.md#build-to-docker-daemon)

1. Run `kubectl apply -k k8s/environments/local` from a terminal at the root level of my repository. This should start up the application
in a `{{cookiecutter._project_name_kebab_case}}` namespace.

1. Run `./k8s/environments/local/access-app-via-minikube.sh` from a terminal at 
the root level of my repository, and you should then be able to run the following from the same terminal and get a 200 response back: `curl 'http://{{cookiecutter._project_name_kebab_case}}.local/actuator/health' -i -X GET -H 'Accept: application/json'`.

# Maintenance

_Explain how to do any maintenance steps here, i.e. if the repository uses a strategy pattern somewhere, how would you extend it?_

## Can you tell me about your tests?

- [Spock](https://spockframework.org/) is used to implement my unit tests.
- I use the [Kafka module for Testcontainers](https://www.testcontainers.org/modules/kafka/) for unit tests, so you
need Docker to run my unit tests on a machine!
    - Since I use the Kafka Testcontainer module, the container that's spun up during my unit tests assigns a random
    port to the Kafka bootstrap URL that's set inside the container. To ensure that the Spring application fired up for
    unit tests can connect correctly to the topics, I need to provide the container's Kafka bootstrap URL to the test 
    properties **after** the container has been spun up. Have a look at 
    `src/test/unit/com/{{ cookiecutter._group_name_lowercase_no_spaces }}/{{ cookiecutter._project_name_lowercase_no_spaces }}/{{ cookiecutter._project_name_camel_case }}UnitTest.groovy` to see how I do this :)

## Design Decisions

_Explain any significant design decisions made here._