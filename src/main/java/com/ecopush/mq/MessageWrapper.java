package com.ecopush.mq;

import java.lang.Exception;
import com.tuya.open.sdk.mq.AESBase64Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageWrapper {
  private String data;
  private String protocol;
  private String pv;
  private String sign;
  private String t;

  public String getData() {
    return this.data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getProtocol() {
    return this.protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getPv() {
    return this.pv;
  }

  public void setPv(String pv) {
    this.pv = pv;
  }

  public String getSign() {
    return this.sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getT() {
    return this.t;
  }

  public void setT(String t) {
    this.t = t;
  }

  public String getMessage(String encryptionKey) throws Exception {
    return AESBase64Utils.decrypt(this.data, encryptionKey);
  }
}
