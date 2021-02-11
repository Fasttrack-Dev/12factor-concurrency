## Worker processes example
This is a simple CloudFormation example to showcase how to
setup worker processes using AWS Elastic Beanstalk.

### Build and Deploy
Simply run the `pipline` script. It will build the application and
deploy it to the worker environment which is setup beforehand, if
necessary. It will also show you how to test the environment.

### See it in action 

To test, send messages to the worker queue:
```sh
$ aws sqs send-message --queue-url https://sqs.REGION.amazonaws.com/ACCOUNT/twelve-factor-worker-queue --message-body 'Some message body'
```

Verify, the worker setup with request and retrieve the environment logs.<br>
The following command requests logs from an environment named `twelve-factor-worker-environment`
```sh
$ aws elasticbeanstalk request-environment-info --environment-name twelve-factor-worker-environment --info-type tail
```
After requesting logs, retrieve their location with
```sh
$ aws elasticbeanstalk retrieve-environment-info --environment-name twelve-factor-worker-environment --info-type tail
```
Now open the logs with the browser and you should find the following sections.
```console
----------------------------------------
/var/log/web.stdout.log
----------------------------------------
...
Feb  2 12:41:53 ip-172-31-0-40 web: 2021-02-02 12:41:53.303  INFO 2804 --- [nio-5000-exec-1] com.ge.processes.WorkerController        : Body is Some message body
...
----------------------------------------
/var/log/aws-sqsd/default.log
----------------------------------------
2021-02-02T12:35:11Z init: initializing aws-sqsd 3.0.3 (2020-11-04)
2021-02-02T12:35:11Z pollers: start initializting poller timer...
2021-02-02T12:35:11Z pollers: start auto running poller...
2021-02-02T12:35:11Z start: polling https://sqs.eu-west-1.amazonaws.com/652129918095/twelve-factor-worker-queue
2021-02-02T12:41:53Z message: sent to http://localhost:80/work
...

```

### Local testing
You can verify the worker concept local before you do it in the cloud. 
This could be helpful if you are not familiar with the worker concept.

Run the `bootRun` gradle task to launch the application. Then use
e.g. Postman to send POST requests to the `/work` endpoint. Post a 
text-body with any content, it's logged to `stdout`. The service will
take 3s to provide a response.

here are the steps to do it local:
```sh
$ gradle bootRun
```
open a second shell and send the post to the worker
```sh
$ curl -X POST -iv http://localhost:8080/work --data "lets work"
```
in the shell of your java process you should see the following log entry
```console
...
2021-02-02 14:06:48.113  INFO 95816 --- [nio-8080-exec-2] com.ge.processes.WorkerController        : Body is lets+work=
...
```
