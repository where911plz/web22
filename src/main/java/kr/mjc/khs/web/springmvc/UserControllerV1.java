package kr.mjc.khs.web.springmvc;

import kr.mjc.khs.web.dao.User;
import kr.mjc.khs.web.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

//@Controller
@Slf4j
public class UserControllerV1 {

    private final UserDao userDao;

    public UserControllerV1(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 회원 목록
     */
    @GetMapping("/user/userList")
    public void userList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int count = NumberUtils.toInt(request.getParameter("count"), 25);
        int page = NumberUtils.toInt(request.getParameter("page"), 1);

        List<User> userList = userDao.listUsers(count, page);
        int totalCount = userDao.countUsers();

        request.setAttribute("userList", userList);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage",
                (int) Math.ceil((double) totalCount / count));
        request.getRequestDispatcher("/WEB-INF/jsp/user/userList.jsp")
                .forward(request, response);
    }

    /**
     * 회원 정보
     */
    @GetMapping("/user/userInfo")
    public void userInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));

        User user = userDao.getUser(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/jsp/user/userInfo.jsp")
                .forward(request, response);
    }

    /**
     * 회원가입
     */
    @PostMapping("/user/signup")
    public void signup(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));

        try {
            userDao.addUser(user);
            // 회원가입 성공하면 로그인
            signin(request, response);
        } catch (DataAccessException e) {
            // 회원가입 실패하면 다시 회원가입 화면으로
            log.error(e.toString());
            response.sendRedirect(request.getContextPath() + "/app/user/signupForm");
        }
    }

    /**
     * 로그인
     */
    @PostMapping("/user/signin")
    public void signin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String redirectUrl =
                StringUtils.defaultIfEmpty(request.getParameter("redirectUrl"),
                        request.getContextPath() + "/app/user/userList?count=25&page=1");
        log.debug("signin redirectUrl={}", redirectUrl);

        try {
            User user = userDao.login(email, password);
            // 로그인 성공하면 redirectUrl로
            request.getSession().setAttribute("ME", user);
            response.sendRedirect(redirectUrl);
        } catch (DataAccessException e) {
            // 로그인 실패하면 다시 로그인 화면으로
            response.sendRedirect(
                    request.getContextPath() + "/app/user/signinForm?redirectUrl=" +
                            URLEncoder.encode(redirectUrl, Charset.defaultCharset()));
        }
    }

    /**
     * 비밀번호 변경
     */
    @PostMapping("/user/updatePassword")
    public void updatePassword(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User me = (User) session.getAttribute("ME");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");

        int updatedRows =
                userDao.updatePassword(me.getUserId(), currentPassword, newPassword);
        if (updatedRows >= 1)
            // 업데이트 성공하면 내정보 화면으로
            response.sendRedirect(request.getContextPath() + "/app/user/myInfo");
        else
            // 업데이트 실패하면 다시 비밀번호변경 화면으로
            response.sendRedirect(
                    request.getContextPath() + "/app/user/passwordEdit");
    }

    /**
     * 로그아웃
     */
    @GetMapping("/user/signout")
    public void signout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(
                request.getContextPath() + "/app/user/userList?count=25&page=1");
    }
}