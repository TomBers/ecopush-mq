package com.ecopush.mq;

import org.junit.jupiter.api.Test;
import com.ecopush.mq.Consumer;
import com.ecopush.mq.MessageWrapper;
import java.io.IOException;
import java.lang.Exception;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDecryption {
  @Test
  public void testDeserialisation() throws IOException {
    MessageWrapper messageWrapper = Consumer.deserialise("{\"data\":\"67i+gKIgs3DRhIgDfoGajxND7d0MQewliWXB2aXl50CnC0U2pZDWSxp2FE0/VY3E3IoWG1s8/2q02wNWhULYi58BZiIRQ4GahALIR5iVle/LGMpG2WqzB38RGT4XxSC3yC5TRfed0ranKlK+VyHEfzF/M7NDeeeqRbtgck8fZJ3oqikvKmaCKtNMeyRNJ5SBcGAXOvoqoQhDqJKYDbVSag==\",\"protocol\":4,\"pv\":\"2.0\",\"sign\":\"baba9cda034c3425159f6422a347405a\",\"t\":1542226993210}");
    assertEquals("1542226993210", messageWrapper.getT());
  }

  @Test
  public void testDecryption() throws IOException, Exception {
    MessageWrapper messageWrapper = Consumer.deserialise("{\"data\":\"67i+gKIgs3DRhIgDfoGajxND7d0MQewliWXB2aXl50CnC0U2pZDWSxp2FE0/VY3E3IoWG1s8/2q02wNWhULYi58BZiIRQ4GahALIR5iVle/LGMpG2WqzB38RGT4XxSC3yC5TRfed0ranKlK+VyHEfzF/M7NDeeeqRbtgck8fZJ3oqikvKmaCKtNMeyRNJ5SBcGAXOvoqoQhDqJKYDbVSag==\",\"protocol\":4,\"pv\":\"2.0\",\"sign\":\"baba9cda034c3425159f6422a347405a\",\"t\":1542226993210}");

    String decryptionKey = "skhtj9wnq7mq8dpf";
    String innerMessage = messageWrapper.getMessage(decryptionKey);
    assertEquals("{\"dataId\":\"00057AA5B3FB9FF196A802001D486995\",\"devId\":\"20146435807d3a4a3110\",\"dps\":[{\"3\":1,\"t\":1542226990000,\"add_ele\":1}],\"productKey\":\"UhLkQlpfvO0fdrf3\"}", innerMessage);
  }

  @Test
  public void testDecryptionStatus() throws IOException, Exception {
    MessageWrapper messageWrapper = Consumer.deserialise("{\"data\":\"1ThXpPlKQTg8NUCd6HXaWjO/2ukpUgzpw0CvvbpR/Hkh4tD7ntslHFuj8ErdejBwjXf+xa9x+ynQ5x+qXegCiPQw+3aQbK5+EywXBYTmaPxtIC7obG6+SnVFQlooEXgf1U9TYwXwO3MLhpMacCFIS2WnKxSuZz/NV+5rVQE/fSLUr7eKrA9/NolFI9Y7g0jx\",\"protocol\":20,\"pv\":\"2.0\",\"sign\":\"b4123ea1a798c95717940b28306522bb\",\"t\":1542402149348}");

    String decryptionKey = "skhtj9wnq7mq8dpf";
    String innerMessage = messageWrapper.getMessage(decryptionKey);
    assertEquals("{\"bizCode\":\"online\",\"bizData\":{\"time\":1542402149298},\"devId\":\"0682373584f3eb214711\",\"productKey\":\"UhLkQlpfvO0fdrf3\",\"ts\":1542402149348}", innerMessage);
  }

}
