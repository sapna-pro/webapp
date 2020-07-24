package com.webapp.assignment.Service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private Logger logger = LoggerFactory.getLogger(Amazon_SNS.class);


    @PostConstruct
    private void postConstructor() {

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey));
        logger.info("awsCredentials");
        this.amazonSNS = AmazonSNSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion)
                .build();
        logger.info("amazonSNS");
    }

    public void doEmail(String email) {

        logger.info("insidedoEmail");
//        UUID uuid = UUID.randomUUID();
//        Map<String, String> map = new HashMap<>();
//        map.put("email",email);
//        JSONObject jo = new JSONObject(map);
//        logger.info("json String created");



        PublishRequest publishRequest = new PublishRequest(snsTopicARN,email);
        logger.info("publishrequest");
        PublishResult publishResult = this.amazonSNS.publish(publishRequest);
        //PublishResult result = this.amazonSNS.publish(this.snsTopicARN, email);
        logger.info("publishresult");
    }
}
