package com.HMS.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsService {

    public String sendSms(String to, String from, String messageBody) {
            Message message = Message.creator(
                    new PhoneNumber(to), // Receiver's phone number
                    new PhoneNumber(from), // Twilio phone number
                    messageBody
            ).create();

            return "SMS sent successfully! Message SID: " + message.getSid();
    }
}

