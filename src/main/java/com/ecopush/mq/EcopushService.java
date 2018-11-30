package com.ecopush.mq;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public interface EcopushService {
   void post(String data) throws MalformedURLException, ProtocolException, IOException;
}

