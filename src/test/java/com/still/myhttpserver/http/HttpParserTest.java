package com.still.myhttpserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass() {
        this.httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateValidGETTestCase()
            );
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertEquals(request.getMethod(), HttpMethod.GET);
    }
    @Test
    void parseHttpRequestBadMethodName() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodName()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED   );
        }
    }
    @Test
    void parseHttpRequestBadMethodNameMaxLengthExceeded() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodNameMaxLengthExceeded()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED   );
        }
    }

    @Test
    void parseHttpRequestBadMethodNamegenerateBadTestCaseMethodNameInvalidNumberOfItemsInRequestLine() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodNameInvalidNumberOfItemsInRequestLine()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST );
        }
    }

    @Test
    void parseHttpRequestBadMethodNamegenerateBadTestCaseMethodNameEmptyRequestLineItemsAmount() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodNameEmptyRequestLine()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST );
        }
    }

    @Test
    void parseHttpRequestBadMethodNamegenerateBadTestCaseMethodNameOnlyCRnoLF() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodOnlyCRnoLF()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST );
        }
    }



    private InputStream generateValidGETTestCase() {
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8041\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: ru-RU,ru;q=0.9\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseMethodName() {
        String rawData = "GeT / HTTP/1.1\r\n" +
                "Host: localhost:8041\r\n" +
                "Accept-Language: ru-RU,ru;q=0.9\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseMethodNameMaxLengthExceeded(){
        String rawData = "GET13SDA / HTTP/1.1\r\n" +
                "Host: localhost:8041\r\n" +
                "Accept-Language: ru-RU,ru;q=0.9\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseMethodNameInvalidNumberOfItemsInRequestLine(){
        String rawData = "GET / FFFF / HTTP/1.1\r\n" +
                "Host: localhost:8041\r\n" +
                "Accept-Language: ru-RU,ru;q=0.9\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseMethodNameEmptyRequestLine(){
        String rawData = "\r\n" +
                "Host: localhost:8041\r\n" +
                "Accept-Language: ru-RU,ru;q=0.9\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseMethodOnlyCRnoLF(){
        String rawData = "GET / HTTP/1.1\r" + // <-- no line feed
                "Host: localhost:8041\r\n" +
                "Accept-Language: ru-RU,ru;q=0.9\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }
}