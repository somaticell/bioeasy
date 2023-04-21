package com.baidu.mapapi.cloud;

public class CloudEvent {

    public class ErrorNo {
        public static final int STATUS_CODE_NETWORK_ERROR = 2;
        public static final int STATUS_CODE_NETWORK_TIME_OUT = 8;
        public static final int STATUS_CODE_PERMISSION_UNFINISHED = 107;
        public static final int STATUS_CODE_RESULT_NOTFOUND = -1;
        public static final int STATUS_CODE_SERVER_ERROR_INTERVAL = 10000;

        public ErrorNo() {
        }
    }
}
