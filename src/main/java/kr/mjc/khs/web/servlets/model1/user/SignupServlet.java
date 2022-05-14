package kr.mjc.khs.web.servlets.model1.user;

import kr.mjc.khs.web.dao.User;
import kr.mjc.khs.web.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 회원 등록
 */
@WebServlet("/model1/user/signup")
@Slf4j
public class SignupServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() {
        WebApplicationContext wac =
                (WebApplicationContext) getServletContext().getAttribute(
                        WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        userDao = wac.getBean(UserDao.class);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));

        try {
            userDao.addUser(user);
            // 등록 성공
            response.sendRedirect(request.getContextPath() + "/model1/user/userList");
        } catch (DataAccessException e) { // 등록 실패
            log.error(e.toString());
            response.sendRedirect(
                    request.getContextPath() + "/model1/user/signupForm");
        }
    }
}