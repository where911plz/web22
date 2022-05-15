package kr.mjc.khs.web.servlets.mvc;

import kr.mjc.khs.web.dao.Article;
import kr.mjc.khs.web.dao.ArticleDao;
import kr.mjc.khs.web.dao.User;
import kr.mjc.khs.web.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@Controller
@Slf4j
public class ArticleController {

    private final ArticleDao articleDao;
    private final UserDao userDao;
    public ArticleController(ArticleDao articleDao, UserDao userDao) {
        this.articleDao = articleDao;
        this.userDao = userDao;
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
    /*
    게시글 추가
     */
    public void articleAdd(HttpServletRequest request, HttpServletResponse response)
        throws  IOException {
        HttpSession session = request.getSession();
        User me = (User) session.getAttribute("ME");
        Article article = new Article();
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("content"));
        article.setName(me.getName());
        article.setUserId(me.getUserId());

        try {
            articleDao.addArticle(article);
            response.sendRedirect(request.getContextPath() + "/mvc/article/articleList");
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
    /*
    게시글 수정
     */
    public void articleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User me = (User) session.getAttribute("ME");
        Article article = new Article();

        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("content"));
        article.setArticleId(article.getArticleId());
        article.setUserId(me.getUserId());

        try {
            articleDao.updateArticle(article);
            response.sendRedirect(request.getContextPath() + "/mvc/article/articleList");
        } catch (DataAccessException e) {
            log.error(e.toString());
            response.sendRedirect(request.getContextPath() + "/mvc/article/articleForm");
        }


    }
    }
