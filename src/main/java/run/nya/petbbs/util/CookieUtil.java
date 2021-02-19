package run.nya.petbbs.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Cookie工具类
 *
 * 2021/02/19
 */
@Component
public class CookieUtil {

    public static final Integer MAX_AGE = 60 * 60 * 24;

    /**
     * 设置Cookie
     * @param key
     * @param value
     */
    public void setCookie(String key, String value) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getResponse();
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(MAX_AGE);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        assert response != null;
        response.addCookie(cookie);
    }

    /**
     * 获取Cookie
     * @param  key
     * @return
     */
    public String getCookie(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 删除Cookie
     * @param key
     */
    public void delCookie(String key) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getResponse();
        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(MAX_AGE);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        assert response != null;
        response.addCookie(cookie);
    }

}
