package com.poyee.agora.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Acknowledgement {
    private boolean ok;
    private int statusCode;
    private Object rtnObj;
    private String errorMsg;
}
