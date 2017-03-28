package com.lid.intellij.translateme.yandex;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

public class YandexClient {
    private static final Logger log = Logger.getInstance(YandexClient.class);

    private static final String HOST = "translate.yandex.net";
    private static final String PATH = "/api/v1.5/tr.json/";
    private static final String APIKEY = "trnsl.1.1.20150828T222514Z.96c635fa0967005b.781eebb21e0a7b0e9b3b4f2fb62a21a74400189f";

    //https://translate.yandex.net/api/v1.5/tr.json/translate?key=APIkey&lang=en-ru&text=To+be,+or+not+to+be%3F&text=That+is+the+question.
    public String translate(String text, String langFrom, String langTo) {
        final String method = "translate";

        String uri = "https://" + HOST + PATH + method;
        uri += "?key=" + APIKEY;
        uri += "&lang=" + langFrom + "-" + langTo;
        try {
            uri += "&text=" + URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }

        return request(uri);
    }

    public String detect(String text) {
        final String method = "detect";
        String uri = "https://" + HOST + PATH + method;
        uri += "?key=" + APIKEY;
        uri += "&text=" + text;

        return request(uri);
    }

    public String getLanguages(String ui) {
        final String method = "getLangs";
        String uri = "https://" + HOST + PATH + method;
        uri += "?key=" + APIKEY;
        uri += "&ui=" + ui;

        return request(uri);
    }

    @Nullable
    private String request(String uri) {
        try {
            URLConnection connection = new URL(uri).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream response = connection.getInputStream();

            Scanner s = new Scanner(response, "UTF-8").useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }
}
