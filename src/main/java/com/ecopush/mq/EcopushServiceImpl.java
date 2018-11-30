package com.ecopush.mq;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class EcopushServiceImpl implements EcopushService {

  private String urlString;

  public EcopushServiceImpl(String urlString) {
    this.urlString = urlString;
  }

   public void post(String data) throws
    MalformedURLException, ProtocolException, IOException
  {
    byte[] postData = data.getBytes(StandardCharsets.UTF_8);
    HttpURLConnection con = null;
    try {
      URL url = new URL(urlString);
      con = (HttpURLConnection) url.openConnection();
      con.setDoOutput(true);
      con.setRequestMethod("POST");
      con.setRequestProperty("User-Agent", "Ecopush MQ Endpoint");
      con.setRequestProperty("Content-Type", "application/json");
      try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
        wr.write(postData);
      }

      StringBuilder content;
      try (BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()))) {
        String line;
        content = new StringBuilder();
        while ((line = in.readLine()) != null) {
          content.append(line);
          content.append(System.lineSeparator());
        }
      }

      System.out.println(content.toString());
    } finally {
      if (con != null) {
        con.disconnect();
      }
    }
  }
}
