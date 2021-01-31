package com.still.myhttpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);


    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();


//            //reading request from browser
//            int oneByte;
//            while ( (oneByte = inputStream.read()) >= 0 ) {
//                System.out.print((char) oneByte);
//            }


            String html = "<html><head><title>Java Http Server</title></head><body><h1>This is Body</h1></body></html>";
            final String CRLF = "\r\n"; //carriage return and line feed //13, 10 ASCI

            String response =
                    "HTTPS/1.1 200 OK" + CRLF  +// Status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF +  //HEADERS
                            CRLF + html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());


            LOGGER.info("* Connection finished! *");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}

            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }

        }


    }
}

