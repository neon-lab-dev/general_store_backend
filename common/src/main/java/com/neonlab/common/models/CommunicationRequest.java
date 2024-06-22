package com.neonlab.common.models;

import com.neonlab.common.constants.GlobalConstants;
import com.neonlab.common.entities.Otp;
import com.neonlab.common.enums.CommunicationMode;
import lombok.Data;

@Data
public class CommunicationRequest {

    private CommunicationMode mode;
    private String to;
    private String from;
    private String body;

    public CommunicationRequest(Otp otp){
        this.to = otp.getCommunicatedTo();
        this.from = GlobalConstants.SYSTEM;
        this.body = otp.getOtp();
    }

}
