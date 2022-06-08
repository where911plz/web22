package kr.mjc.khs.web.springmvc;

import kr.mjc.khs.web.dao.Article;
import kr.mjc.khs.web.dao.ArticleDao;
import kr.mjc.khs.web.dao.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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
public class ArticleControllerV2 {
    public static final String CURRENT_ARTICLE_LIST = "CURRENT_ARTICLE_LIST";

    private final ArticleDao articleDao;

    public ArticleControllerV2(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * 게시글 목록화면
     */
    @GetMapping("/article/articleList")
    public void articleList(int count, int page, HttpServletRequest request, Model model) {
        List<Article> articleList = articleDao.listArticles(count, page);
        int totalCount = articleDao.countArticles();
        // 현재 페이지를 세션에 넣음

        String uri = request.getRequestURI() + "?" + request.getQueryString();
        request.getSession().setAttribute(CURRENT_ARTICLE_LIST, uri);

        model.addAttribute("articleList", articleList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPage",
                (int) Math.ceil((double) totalCount / count));
    }

    /**
     * 게시글 조회화면
     */
    @GetMapping("/article/articleView")
    public void articleView(int articleId, Model model) {

        Article article = articleDao.getArticle(articleId);
        model.addAttribute("article", article);

    }

    /**
     * 글쓰기 화면
     */
    @GetMapping("/article/articleFrom")
    public void articleForm() {

    }

    /**
     * 게시글 수정화면
     */
    @GetMapping("/article/articleEdit")
    public void articleEdit(int articleId, Model model) {

        Article article = articleDao.getArticle(articleId);
        model.addAttribute("article", article);

    }

    /**
     * 게시글 추가. 추가 후 목록 1page로 redirect
     */
    @PostMapping("/article/addArticle")
    public String addArticle(Article article, @SessionAttribute("ME") User me ) {
        articleDao.addArticle(article);
        return "redirect=/app/article/articleList?count=25&page=1";
    }

    /**
     * 게시글 수정. 수정 후 조회화면으로 redirect
     */
    @PostMapping("/article/updateArticle")
    public String updateArticle(Article article, @SessionAttribute("ME") User me){
        article.setUserId(me.getUserId());

        articleDao.updateArticle(article);
        return "redirect=/app/article/articleView?articleId=";
    }


    /**
     * 게시글 삭제. 삭제 후 현재 목록으로 redirect
     */
    @GetMapping("/article/deleteArticle")
    public String deleteArticle(int articleId, @SessionAttribute("ME") User me)  {

        articleDao.deleteArticle(articleId, me.getUserId());
        return "redirect=" + CURRENT_ARTICLE_LIST;
    }
}