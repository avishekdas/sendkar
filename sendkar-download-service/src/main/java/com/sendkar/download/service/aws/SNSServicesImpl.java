package com.sendkar.download.service.aws;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SNSServicesImpl implements SNSServices {

    private Logger logger = LoggerFactory.getLogger(SNSServicesImpl.class);

    @Autowired
    private AmazonSNSClient snsClient;

    @Value("${aws.sns.senderid}")
    private String senderID;

    private Map<String, MessageAttributeValue> smsAttributes =
            new HashMap<String, MessageAttributeValue>();

    public SNSServicesImpl() {
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("sendkar") //The sender ID shown on the device.
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.50") //Sets the max price to 0.50 USD.
                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Transactional") //Sets the type to promotional.
                .withDataType("String"));
    }

    @Override
    public boolean sendSms(String phoneNumber, String message) {
        String messageId = sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
        if(messageId == null || "".equalsIgnoreCase(messageId)) {
            return false;
        } else {
            return true;
        }
    }

    private String sendSMSMessage(AmazonSNSClient snsClient, String message,
                                              String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        if(result != null) {
            return result.getMessageId(); // Prints the message ID.
        } else {
            return null;
        }
    }
}
