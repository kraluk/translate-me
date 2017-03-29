package com.lid.intellij.translateme.yandex;

import com.intellij.openapi.diagnostic.Logger;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Simple Yandex REST service invoker
 *
 * @author lukasz
 */
class YandexServiceInvoker {
    private static final Logger log = Logger.getInstance(YandexServiceInvoker.class);

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String DEFAULT_DELIMITER_PATTERN = "\\A";

    private static final String EMPTY_RESPONSE = "";

    /**
     * Invokes gives request/URL and retrieves results
     *
     * @param request an GET request
     * @return retrieved result if available, otherwise {@link #EMPTY_RESPONSE}
     */
    String invoke(String request) {
        log.trace(String.format("Attempting to invoke request '%s'...", request));

        String result;

        try {
            URLConnection connection = new URL(request).openConnection();
            connection.setRequestProperty("Accept-Charset", DEFAULT_ENCODING);

            InputStream response = connection.getInputStream();

            Scanner scanner = new Scanner(response, DEFAULT_ENCODING).useDelimiter(DEFAULT_DELIMITER_PATTERN);
            result = scanner.hasNext() ? scanner.next() : EMPTY_RESPONSE;
        } catch (Exception e) {
            log.warn(e);
            result = EMPTY_RESPONSE;
        }

        log.trace(String.format("Got '%s'", result));
        return result;
    }
}
