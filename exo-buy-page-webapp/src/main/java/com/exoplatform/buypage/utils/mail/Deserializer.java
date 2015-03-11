/*
 * Copyright (C) 2003-2015 eXo Platform SAS.
 *
 * This file is part of UXPaaS :: MGT :: Services.
 *
 * UXPaaS :: MGT :: Services is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * UXPaaS :: MGT :: Services software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UXPaaS :: MGT :: Services; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
package com.exoplatform.buypage.utils.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A deserializer used in exo-buy-admin that resolve mail template.
 *
 * @author eXo Platform SAS
 * @since 1.0.0
 */
public class Deserializer {
  /**
   * Constant {@code LOG}
   */
  private static final Logger LOG = LoggerFactory.getLogger(Deserializer.class);

  /**
   * <p>
   * readFromInputStream.
   * </p>
   *
   * @param io - input stream
   * @return String from io
   */
  public static String readFromInputStream(InputStream io) {
    StringBuilder sb = new StringBuilder();

    try (Scanner scanner = new Scanner(io)) {
      while (scanner.hasNextLine()) {
        sb.append(scanner.nextLine());
        sb.append(System.getProperty("line.separator"));
      }
    }

    return sb.toString();
  }

  /**
   * <p>
   * getResourceContent.
   * </p>
   *
   * @param resource a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   * @throws java.io.IOException if any.
   */
  public static String getResourceContent(String resource) throws IOException {
    File file = new File(resource);
    if (file.exists() && file.isFile()) {
      try (FileInputStream stream = new FileInputStream(file)) {
        return readFromInputStream(stream);
      }
    }

    URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
    if (url != null) {
      LOG.debug("loading {}", url.toString());
      InputStream inputStream = null;
      try {
        inputStream = url.openConnection().getInputStream();
        if (inputStream != null) {
          return readFromInputStream(inputStream);
        }
      } finally {
        if (inputStream != null) {
          inputStream.close();
        }
      }
    }

    return null;
  }

  /**
   * Resolve mail template
   *
   * @param templateContent - content of template
   * @param properties - properties for template resolving
   * @return a {@link java.lang.String} object.
   */
  public static String resolveTemplate(String templateContent, Map<String, String> properties) {
    for (Entry<String, String> property : properties.entrySet()) {
      templateContent = templateContent.replace("${" + property.getKey() + "}", property.getValue());
    }
    return templateContent;
  }

  /**
   * <p>
   * resolveVariables.
   * </p>
   *
   * @param input a {@link java.lang.String} object.
   * @param props a {@link java.util.Properties} object.
   * @return a {@link java.lang.String} object.
   */
  public static String resolveVariables(String input, Properties props) {
    final int NORMAL = 0;
    final int SEEN_DOLLAR = 1;
    final int IN_BRACKET = 2;
    if (input == null) {
      return input;
    }
    char[] chars = input.toCharArray();
    StringBuilder buffer = new StringBuilder();
    boolean properties = false;
    int state = NORMAL;
    int start = 0;
    for (int i = 0; i < chars.length; ++i) {
      char c = chars[i];
      if (c == '$' && state != IN_BRACKET) {
        state = SEEN_DOLLAR;
      } else if (c == '{' && state == SEEN_DOLLAR) {
        buffer.append(input.substring(start, i - 1));
        state = IN_BRACKET;
        start = i - 1;
      } else if (state == SEEN_DOLLAR) {
        state = NORMAL;
      } else if (c == '}' && state == IN_BRACKET) {
        if (start + 2 == i) {
          buffer.append("${}");
        } else {
          String value = null;
          String key = input.substring(start + 2, i);

          if (props != null) {
            // Some parameters have been given thus we need to check
            // inside first
            String sValue = props.getProperty(key);
            value = sValue == null || sValue.length() == 0 ? null : sValue;
          }
          if (value == null) {
            // try to get it from the system properties
            value = System.getProperty(key);
          }

          if (value != null) {
            properties = true;
            buffer.append(value);
          }
        }
        start = i + 1;
        state = NORMAL;
      }
    }
    if (properties == false) {
      return input;
    }
    if (start != chars.length) {
      buffer.append(input.substring(start, chars.length));
    }
    return buffer.toString();

  }
}
