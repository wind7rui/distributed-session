package com.wind.web.session;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class CookieUtil {
    private static final String SESSION_ID_IN_REQUEST = "JSESIONID_IN_REQUEST";

    public static String getCurrentSessionId(HttpContext httpContext) {
        //浏览器禁止cookie的情况下，将sessionid写入请求中，服务端从请求中获取
        String sessionId = (String) httpContext.getRequest().getAttribute(SESSION_ID_IN_REQUEST);
        if (StringUtils.isNotBlank(sessionId)) {
            return sessionId;
        }

        Cookie cookies[] = httpContext.getRequest().getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie sessionCookie : cookies) {
                if (httpContext.getCookieConfig().getSessionIdName().equalsIgnoreCase(sessionCookie.getName())) {
                    return sessionCookie.getValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }

    public static String generateNewSessionId() {
        return StringUtils.replace(UUID.randomUUID().toString().toUpperCase(), "-", "");
    }

    public static void writeCookie(String sessioId, HttpContext httpContext) {
        httpContext.getResponse().addHeader("Set-Cookie", buildCookie(sessioId, httpContext.getCookieConfig(), httpContext.getCookieConfig().getMaxAge()));
    }

    private static String buildCookie(String sessioId, CookieConfig cookieConfig, int maxAge) {
        StringBuilder cookieString = new StringBuilder(cookieConfig.getSessionIdName());
        cookieString.append("=").append(sessioId).append(";");
        if (StringUtils.isNotEmpty(cookieConfig.getPath())) {
            cookieString.append("Path=").append(cookieConfig.getPath()).append(";");
        }

        if (StringUtils.isNotEmpty(cookieConfig.getDomain())) {
            cookieString.append("Domain=").append(cookieConfig.getDomain()).append(";");
        }

        if (cookieConfig.isSecure()) {
            cookieString.append("Secure;");
        }
        cookieString.append("HttpOnly;");
        cookieString.append("Max-Age=").append(maxAge).append(";");
        return cookieString.toString();
    }

    public static void deleteCookie(HttpContext httpContext) {
        httpContext.getResponse().addHeader("Set-Cookie", buildCookie("", httpContext.getCookieConfig(), 0));
    }
}
