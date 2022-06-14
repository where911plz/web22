package kr.mjc.khs.web;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {
    /*
    queryString 을 포함한 requsetURL
     */
    public static String getRequestURLWithQueryString(
            HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        return queryString == null ? requestURL.toString() :
                requestURL.append("?").append(queryString).toString();
    }
}
