package com.fym.electrichousekeeper;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketTest {

    @Test
    public void test1() throws IOException {
        Socket socket = new Socket("116.196.101.91",80);
        try {
            InputStream inputStream = socket.getInputStream();
            int read = inputStream.read();
            System.out.println(read);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
