package kr.mjc.khs.web.servlets.mvc;

import kr.mjc.khs.web.dao.Article;
import kr.mjc.khs.web.dao.ArticleDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
@Controller
@Slf4j
public class ArticleController {

    private final ArticleDao articleDao;

    public ArticleController(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /*
    게시글 목록
     */
    public void articleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int count = NumberUtils.toInt(request.getParameter("count"), 20);
        int page = NumberUtils.toInt(request.getParameter("page"), 1);

        List<Article> articleList = articleDao.listArticles(count, page);
        request.setAttribute("articleList", articleList);
        request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/articleList.jsp")
                .forward(request, response);
    }
    /*
    글쓰기 화면
     */
    public void articleForm(HttpServletRequest request, HttpServletResponse response)
            throws   ServletException, IOException {
        Article article = new Article();
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("context"));


        try {
            articleDao.addArticle(article);
            articleAdd(request, response);
        } catch (DataAccessException e) {
            log.error(e.toString());
        }
    }

    public void articleAdd(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Article article = new Article();
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("context"));
        article.setName(request.getParameter("name"));
        article.setUserId(request.getIntHeader("userId"));

        try {
            articleDao.addArticle(article);
            articleAdd(request, response);
        } catch (DataAccessException e) {
            log.error(e.toString());
            response.sendRedirect(request.getContextPath() + "/mvc/article/articleForm");
        }

    }

    /*
    게시글 조회
     */
    public void articleView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int articleId = Integer.parseInt(request.getParameter("articleId"));

        Article article = articleDao.getArticle(articleId);
        request.setAttribute("article", article);
        request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/articleView.jsp")
                .forward(request, response);

    }
}
