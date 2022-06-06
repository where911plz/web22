package kr.mjc.khs.web.springmvc;

import kr.mjc.khs.web.dao.Article;
import kr.mjc.khs.web.dao.ArticleDao;
import kr.mjc.khs.web.dao.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
//@Controller
@Slf4j
public class ArticleControllerV1 {
    public static final String CURRENT_ARTICLE_LIST = "CURRENT_ARTICLE_LIST";

    private final ArticleDao articleDao;

    public ArticleControllerV1(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * 게시글 목록화면
     */
    @GetMapping("/article/articleList")
    public void articleList(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        int count = NumberUtils.toInt(request.getParameter("count"), 25);
        int page = NumberUtils.toInt(request.getParameter("page"), 1);

        List<Article> articleList = articleDao.listArticles(count, page);
        int totalCount = articleDao.countArticles();

        // 현재 페이지를 세션에 넣음
        String uri = request.getRequestURI() + "?" + request.getQueryString();
        request.getSession().setAttribute(CURRENT_ARTICLE_LIST, uri);

        request.setAttribute("articleList", articleList);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage",
                (int) Math.ceil((double) totalCount / count));
        request.getRequestDispatcher("/WEB-INF/jsp/article/articleList.jsp")
                .forward(request, response);
    }

    /**
     * 게시글 조회화면
     */
    @GetMapping("/article/articleView")
    public void articleView(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        int articleId = Integer.parseInt(request.getParameter("articleId"));

        Article article = articleDao.getArticle(articleId);
        request.setAttribute("article", article);
        request.getRequestDispatcher("/WEB-INF/jsp/article/articleView.jsp")
                .forward(request, response);
    }

    /**
     * 글쓰기 화면
     */
    @GetMapping("/article/articleFrom")
    public void articleForm(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("ME") == null) {
            // 로그인 안한 경우 로그인 화면으로. redirectUrl=글쓰기화면
            String redirectUrl =
                    request.getContextPath() + "/mvc/article/articleForm";
            response.sendRedirect(
                    request.getContextPath() + "/mvc/user/signinForm?redirectUrl=" +
                            URLEncoder.encode(redirectUrl, Charset.defaultCharset()));
        } else {
            // 로그인 한 경우 글쓰기 화면으로
            request.getRequestDispatcher("/WEB-INF/jsp/article/articleForm.jsp")
                    .forward(request, response);
        }
    }

    /**
     * 게시글 수정화면
     */
    @GetMapping("/article/articleEdit")
    public void articleEdit(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        int articleId = Integer.parseInt(request.getParameter("articleId"));

        Article article = articleDao.getArticle(articleId);
        request.setAttribute("article", article);
        request.getRequestDispatcher("/WEB-INF/jsp/article/articleEdit.jsp")
                .forward(request, response);
    }

    /**
     * 게시글 추가. 추가 후 목록 1page로 redirect
     */
    @PostMapping("/article/addArticle")
    public void addArticle(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        User me = (User) request.getSession().getAttribute("ME");

        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setUserId(me.getUserId());
        article.setName(me.getName());

        articleDao.addArticle(article);
        response.sendRedirect(
                request.getContextPath() + "/app/article/articleList?count=25&page=1");
    }

    /**
     * 게시글 수정. 수정 후 조회화면으로 redirect
     */
    @PostMapping("/article/updateArticle")
    public void updateArticle(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        User me = (User) request.getSession().getAttribute("ME");

        Article article = new Article();
        article.setArticleId(articleId);
        article.setTitle(title);
        article.setContent(content);
        article.setUserId(me.getUserId());

        articleDao.updateArticle(article);
        response.sendRedirect(
                request.getContextPath() + "/app/article/articleView?articleId=" +
                        articleId);
    }

    /**
     * 게시글 삭제. 삭제 후 현재 목록으로 redirect
     */
    @GetMapping("/article/deleteArticle")
    public void deleteArticle(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        User me = (User) request.getSession().getAttribute("ME");

        articleDao.deleteArticle(articleId, me.getUserId());
        response.sendRedirect(
                (String) request.getSession().getAttribute(CURRENT_ARTICLE_LIST));
    }
}
