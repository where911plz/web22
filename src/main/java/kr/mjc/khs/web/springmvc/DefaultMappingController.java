package kr.mjc.khs.web.springmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
public class DefaultMappingController {
    /**
     * 기본 매핑
     */
    @GetMapping("/**")
    private void mapDefault(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        log.debug(pathInfo);
        request.getRequestDispatcher("/WEB-INF/jsp" + pathInfo + ".jsp")
                .forward(request, response);
    }
}