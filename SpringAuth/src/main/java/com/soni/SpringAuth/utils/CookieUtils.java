package com.soni.SpringAuth.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Contains methods to handle cookie related actions
 * 
 * @author @Aniket Soni
 */
@Component
public class CookieUtils {

  @Autowired
  private HttpServletResponse response;
  @Autowired
  private HttpServletRequest request;

  @Value("3600000")
  String JWT_TOKEN_EXPIRY_MS;

  /**
   * Sets key value pair to cookie for current HttpServletResponse
   * 
   * @param key   key to be stored in cookie
   * @param value value to be stored in cookie
   * @author @aadarshp31
   */
  public void setCookieValue(String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(Integer.parseInt(JWT_TOKEN_EXPIRY_MS) / 1000); // token expiration time in seconds
    response.addCookie(cookie);
  }

  /**
   * Sets key value pair to cookie for current HttpServletResponse
   * 
   * @param key    key to be stored in cookie
   * @param value  value to be stored in cookie
   * @param maxAge expiration duration
   * @author @aadarshp31
   */
  public void setCookieValue(String key, String value, Integer maxAge) {
    Cookie cookie = new Cookie(key, value);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(maxAge); // expiration time in seconds
    response.addCookie(cookie);
  }

  /**
   * Gets value for a key stored in the cookie in the current HttpServletRequest
   * 
   * @param key key for the value to be fetched from cookie
   * @return value stored in the cookie again the provided key
   * @author @aadarshp31
   */
  public String getCookieValue(String key) {
    Map<String, String> map = new HashMap<String, String>();

    String[] cookieStrings = request.getHeader("Cookie").split(";");

    for (String item : cookieStrings) {
      String itemKey = item.split("=")[0].trim();
      String value = item.split("=")[1].trim();
      map.put(itemKey, value);
    }

    return map.get(key);
  }

}
