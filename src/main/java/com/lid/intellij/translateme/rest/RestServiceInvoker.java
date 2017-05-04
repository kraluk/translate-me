package com.lid.intellij.translateme.rest;

import com.intellij.openapi.diagnostic.Logger;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Simple REST service invoker
 *
 * @author lukasz
 */
public class RestServiceInvoker {
    private static final Logger log = Logger.getInstance(RestServiceInvoker.class);

    private static final String DEFAULT_DELIMITER_PATTERN = "\\A";
    private static final String EMPTY_RESPONSE = "";

    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Invokes given {@code GET} request and retrieves results
     *
     * @param request a raw {@code GET} request
     * @return raw retrieved result if available, otherwise {@link #EMPTY_RESPONSE}
     */
    public String get(String request) {
        log.trace(String.format("Attempting to get request '%s'...", request));

        String result;

        try {
            URLConnection connection = new URL(request).openConnection();
            connection.setRequestProperty("Accept-Charset", DEFAULT_ENCODING);

            InputStream response = connection.getInputStream();

            Scanner
                scanner =
                new Scanner(response, DEFAULT_ENCODING).useDelimiter(DEFAULT_DELIMITER_PATTERN);

            result = scanner.hasNext() ? scanner.next() : EMPTY_RESPONSE;
        } catch (Exception e) {
            log.warn(String.format("Unable to invoke request '%s'", request), e);
            result = EMPTY_RESPONSE;
        }

        log.trace(String.format("Got '%s'", result));
        return result;
    }
}
