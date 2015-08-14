/**
 *    _____ _____ _____ _____    __    _____ _____ _____ _____
 *   |   __|  |  |     |     |  |  |  |     |   __|     |     |
 *   |__   |  |  | | | |  |  |  |  |__|  |  |  |  |-   -|   --|
 *   |_____|_____|_|_|_|_____|  |_____|_____|_____|_____|_____|
 *
 *                UNICORNS AT WARP SPEED SINCE 2010
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.sumologic.logback.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * Author: Jose Muniz (jose@sumologic.com)
 * Date: 4/4/13
 * Time: 3:58 AM
 */
public class MockHttpServer {

    private int port;
    private HttpHandler handler;
    private HttpServer server;


    public MockHttpServer(int port, HttpHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    public void start() throws IOException {
        InetSocketAddress addr = new InetSocketAddress(port);
        server = HttpServer.create(addr, 0);
        server.createContext("/", handler);
        server.setExecutor(null); // default executor

        server.start();

    }


    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }
}
/*
public class MockHttpServer {

    private final static int PORT = 1023;

    private static HttpRequest createHttpRequest(final String requestString) throws IOException, HttpException {

        final HttpParams params = new BasicHttpParams();
        SessionInputBuffer inbuf = new AbstractSessionInputBuffer() {
            {
                init(new ByteArrayInputStream(
                        requestString.getBytes()),
                        requestString.length(),
                        params);
            }

            @Override
            public boolean isDataAvailable(int i) throws IOException {
                return true;
            }
        };


        HttpRequestFactory requestFactory = new DefaultHttpRequestFactory();
        HttpRequestParser requestParser = new HttpRequestParser(inbuf, null, requestFactory, params);
        HttpRequest request = (HttpRequest) requestParser.parse();

        return request;
    }


    class ListenerThread implements Runnable {

        private String readFully(InputStream is) throws IOException {
            StringBuilder builder = new StringBuilder();
            int c;
            while ((c = is.read()) != -1) {
                builder.append(c);
            }

            return builder.toString();
        }


        @Override
        public void run() {
            ServerSocket server = null;
            Socket socket = null;

            try {
                server = new ServerSocket(PORT);
                socket = server.accept();
                createHttpRequest(readFully(socket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (HttpException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) try { socket.close(); } catch (Exception e) {}
                if (server != null) try { server.close(); } catch (Exception e) {}
            }
        }
    }

    public void start() throws IOException {

    }
}

*/