FROM java:8-alpine

COPY entrypoint.sh entrypoint.sh

# Create non-root user and group
RUN addgroup as && adduser -D -G as as

# Create directory for the JAR
RUN ["mkdir", "-p", "/opt/as"]
RUN ["chown", "-R", "as", "/opt/as"]

# Copy the JAR file to the container
COPY target/hello-echo-*.jar  /opt/as/hello-echo.jar

CMD ["/opt/as/hello-echo.jar"]

EXPOSE 8080

# Switch to the non-root user
USER as

ENTRYPOINT ["./entrypoint.sh"]
