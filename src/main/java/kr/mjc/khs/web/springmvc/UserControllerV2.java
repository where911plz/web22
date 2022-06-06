package kr.mjc.khs.web.springmvc;

import kr.mjc.khs.web.dao.User;
import kr.mjc.khs.web.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

@Controller
@Slf4j
public class UserControllerV2 {

    private final UserDao userDao;
    private final MessageSource messages;

    public UserControllerV2(UserDao userDao, MessageSource messages) {
        this.userDao = userDao;
        this.messages = messages;
    }

    /**
     * 회원 목록
     */
    @GetMapping("/user/userList")
    public void userList(int count, int page, Model model) {
        List<User> userList = userDao.listUsers(count, page);
        int totalCount = userDao.countUsers();

        model.addAttribute("userList", userList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPage",
                (int) Math.ceil((double) totalCount / count));
    }

    /**
     * 회원 정보
     */
    @GetMapping("/user/userInfo")
    public void userInfo(int userId, Model model) {
        User user = userDao.getUser(userId);
        model.addAttribute("user", user);
    }

    /**
     * 회원가입
     */
    @PostMapping("/user/signup")
    public String signup(User user, HttpSession session, RedirectAttributes attributes) {
        try {
            userDao.addUser(user);
            // 회원가입 성공하면 로그인
            return signin(user.getEmail(), user.getPassword(), null, session,
                    attributes);
        } catch (DataAccessException e) {
            // 회원가입 실패하면 다시 회원가입 화면으로
            log.error(e.toString());
            attributes.addFlashAttribute("message", messages.getMessage("signup.failure", null ,
            Locale.getDefault()));
            return "redirect:/app/user/signupForm";
        }
    }

    /**
     * 로그인
     */
    @PostMapping("/user/signin")
    public String signin(String email, String password, String redirectUrl,
                         HttpSession session, RedirectAttributes attributes) {
        redirectUrl = StringUtils.defaultIfEmpty(redirectUrl,
                "/app/user/userList?count=25&page=1");
        log.debug("signin redirectUrl={}", redirectUrl);

        try {
            User user = userDao.login(email, password);
            session.setAttribute("ME", user);
            // 로그인 성공하면 redirectUrl로
            return "redirect:" + redirectUrl;
        } catch (DataAccessException e) {
            // 로그인 실패하면 다시 로그인 화면으로
            attributes.addFlashAttribute("message", messages.getMessage("signin.failure", null ,
                    Locale.getDefault()));
            return "redirect:/app/user/signinForm?redirectUrl=" +
                    URLEncoder.encode(redirectUrl, Charset.defaultCharset());
        }
    }

    /**
     * 비밀번호 변경
     */
    @PostMapping("/user/updatePassword")
    public String updatePassword(String currentPassword, String newPassword,
                                 @SessionAttribute("ME") User me, RedirectAttributes attributes) {
        int updatedRows =
                userDao.updatePassword(me.getUserId(), currentPassword, newPassword);
        if (updatedRows >= 1)
            // 업데이트 성공하면 내정보 화면으로
            return "redirect:/app/user/myInfo";
        else
        // 업데이트 실패하면 다시 비밀번호변경 화면으로
            attributes.addFlashAttribute("message", messages.getMessage("password.failure", null ,
                    Locale.getDefault()));
        return "redirect=/app/user/passwordEdit";
    }

    /**
     * 로그아웃
     */
    @GetMapping("/user/signout")
    public String signout(HttpSession session) {
        session.invalidate();
        return "redirect:/app/user/userList?count=25&page=1";
    }

    /**
     * 그냥 forward
     */
    @GetMapping({"/user/signinForm", "/user/signupForm", "/user/myInfo",
            "/user/passwordEdit"})
    public void justForward() {
        // Do nothing and forward
    }
}