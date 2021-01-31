package com.still.myhttpserver.http;

public enum HttpMethod {
    GET, HEAD;
    public static final int MAX_LENGTH;

    static {
        int tempMaxLength = -1;
        for (HttpMethod method : HttpMethod.values()) {
            tempMaxLength = (method.name().length() > tempMaxLength) ? method.name().length() : tempMaxLength;
        }
        MAX_LENGTH = tempMaxLength;
    }
}
