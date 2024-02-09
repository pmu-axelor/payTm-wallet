package org.example.templates;

import org.example.dto.MailComponents;
import org.springframework.stereotype.Service;

@Service
public class MailTemplate {

    public static MailComponents mailTemplate(){
        return MailComponents.builder().
                 mailSubject("Paytm Wallet Account").
                 mailBody("Hi %s, \n " +
                         "Your Paytm Wallet account is Created. Now you have Unlimited access to Features offered by Paytm Wallet. \n" +
                         "You daily limit is Rs. 10000 and daily Transactions limit is 10. You can update this anytime you want.\n" +
                         "Thanks & Regards,\n" +
                         "Paytm Wallet Team").
                 build();

    }

}
