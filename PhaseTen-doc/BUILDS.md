## Automated Builds

* Automated Builds are realised using the a glassfish docker container and gradle

### Gradle

Gradle supports building ears for deployment. For that reason Gradle was setup for multi-project building the ear and the client seperatly. The *settings.gradle* defines the different projects and the root *build.gradle* defines default repositories.

The gui, client, common and ejb are build like java projects to a corresponding jar lib. Tests inside the test folder will be executed at build time.

The ear project is setup with the ear-plugin to build an ear file containing the ear itself and the ejb with its dependencies.

### Docker

Oracle offers a Glassfish Docker Container under the name *oracle/glassfish:4.1.2*. It is further configured in the *gitlab-ci.yml* file and allows deploying an ear file and testing against it.

### Gitlab CI

The *gitlab-ci.yml* defines the Docker Container sets it up before each build (username and password for glassfish deployment). At first it builds and deploys the ear file (it builds common and ejb as well 'cause they are set as dependencies). Afterwards the other projects are build and all tests are executed. Tests against the server will work, as it is deployed and listening. 
