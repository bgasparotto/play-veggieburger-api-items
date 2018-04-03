FROM openjdk:8
LABEL maintainer 'bgasparotto <bruno.m.gasparotto@gmail.com>'

ENV PROJECT_HOME /usr/src

RUN mkdir -p $PROJECT_HOME/sbt $PROJECT_HOME/app

RUN apt-get update && \
    apt-get install -y apt-utils && \
    apt-get install -y apt-transport-https

WORKDIR $PROJECT_HOME/sbt
RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list && \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823 && \
    apt-get update && \
    apt-get install -y sbt

ENV PATH $PROJECT_HOME/build/target/universal/stage/bin:$PATH
COPY . $PROJECT_HOME/app
WORKDIR $PROJECT_HOME/app
EXPOSE 9000