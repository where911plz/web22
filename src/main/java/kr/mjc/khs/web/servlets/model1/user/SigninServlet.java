package kr.mjc.khs.web.servlets.model1.user;

import kr.mjc.khs.web.dao.User;
import kr.mjc.khs.web.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인
 */
@WebServlet("/model1/user/signin")
public class SigninServlet extends HttpServlet {

    @Autowired
    private UserDao userDao;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = userDao.login(request.getParameter("email"),
                    request.getParameter("password"));
            // 로그인 성공
            request.getSession().setAttribute("ME", user);
            response.sendRedirect(
                    request.getContextPath() + "/model1/user/userList");
        } catch (DataAccessException e) {
            // 로그인 실패
            response.sendRedirect(
                    request.getContextPath() + "/model1/user/signinForm");
        }
    }
}