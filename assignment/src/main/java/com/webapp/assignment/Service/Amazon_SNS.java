package com.webapp.assignment.Service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Amazon_SNS {

    @Value("${sns.topic.arn}")
    private String snsTopicARN;

    @Value("${aws.access.key.id}")
    private String awsAccessKey;

    @Value("${aws.access.key.secret}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    private AmazonSNS amazonSNS;


    @PostConstruct
    private void postConstructor() {

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)
        );

        this.amazonSNS = AmazonSNSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion)
                .build();
    }

    public void doEmail(String email) {

//        System.out.println("in sns+++++++++++++++++++++++++++++++++++++");
        PublishResult result = this.amazonSNS.publish(this.snsTopicARN, email);
    }
}
