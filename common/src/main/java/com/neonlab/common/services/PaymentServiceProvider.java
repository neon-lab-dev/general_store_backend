package com.neonlab.common.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentServiceProvider {

    @Autowired private ApplicationContextProvider applicationContextProvider;

    public PaymentService getService(boolean isOnline){
        return isOnline ?
                applicationContextProvider.getApplicationContext().getBean(OnlinePaymentService.class):
                applicationContextProvider.getApplicationContext().getBean(ManualPaymentServiceProvider.class);
    }

}
