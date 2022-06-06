package kr.mjc.khs.web.springmvc;

import kr.mjc.khs.web.HttpUtils;
import kr.mjc.khs.web.dao.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@Slf4j
public class SigninInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User me = (User) session.getAttribute("ME");
        if (me != null) // 로그인 한 경우 그대로 진행
            return true;

        // 로그인 안한 경우 로그인 화면으로
        String redirectUrl = HttpUtils.getRequestURLWithQueryString(request);
        log.debug("redirectUrl={}", redirectUrl);

        response.sendRedirect(
                request.getContextPath() + "/app/user/signinForm?redirectUrl=" +
                        URLEncoder.encode(redirectUrl, Charset.defaultCharset()));
        return false;
    }
}