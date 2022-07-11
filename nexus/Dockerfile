# simple docker file
FROM sonatype/nexus3:latest

USER root

RUN yum install python3-pip -y && \
    pip3 install --upgrade pip && \
    pip3 install --upgrade setuptools && \
    pip3 install --upgrade wheel && \
    pip3 install nexus3-cli

USER nexus
