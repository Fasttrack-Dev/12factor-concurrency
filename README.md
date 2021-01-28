## Worker processes example
This is a simple CloudFormation example to showcase how to
setup worker processes using AWS Elastic Beanstalk.

### Build and Deploy
Simply run the `pipline` script. It will build the application and
deploy it to the worker environment which is setup beforehand, if
necessary. It will also show you how to test the environment.

### Local testing
Run the `bootRun` gradle task to launch the application. Then use
e.g. Postman to send POST requests to the `/work` endpoint. Post a 
text-body with any content, it's logged to `stdout`. The service will
take 3s to provide a response.
