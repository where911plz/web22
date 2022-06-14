package kr.mjc.khs.web.springmvc;

import kr.mjc.khs.web.HttpUtils;
import kr.mjc.khs.web.dao.Article;
import kr.mjc.khs.web.dao.ArticleDao;
import kr.mjc.khs.web.dao.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
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
    public void articleList(int count, int page, HttpServletRequest request,
                            Model model) {
        List<Article> articleList = articleDao.listArticles(count, page);
        int totalCount = articleDao.countArticles();

        // 현재 페이지를 세션에 넣음
        String currentUrl = HttpUtils.getRequestURLWithQueryString(request);
        request.getSession().setAttribute(CURRENT_ARTICLE_LIST, currentUrl);

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
    @GetMapping("/article/articleForm")
    public void articleForm() {
    }

    /**
     * 게시글 수정화면
     */
    @GetMapping("/article/articleEdit")
    public void articleEdit(int articleId, @SessionAttribute("ME") User me,
                            Model model) {
        Article article = articleDao.getArticle(articleId);
        // 본인 글이 아닌 경우
        if (article.getUserId() != me.getUserId())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        model.addAttribute("article", article);
    }

    /**
     * 게시글 추가. 추가 후 목록 1page로 redirect
     */
    @PostMapping("/article/addArticle")
    public String addArticle(Article article, @SessionAttribute("ME") User me) {
        article.setUserId(me.getUserId());
        article.setName(me.getName());
        articleDao.addArticle(article);
        return "redirect:/app/article/articleList?count=25&page=1";
    }

    /**
     * 게시글 수정. 수정 후 조회화면으로 redirect
     */
    @PostMapping("/article/updateArticle")
    public String updateArticle(Article article,
                                @SessionAttribute("ME") User me) {
        article.setUserId(me.getUserId());
        if (articleDao.updateArticle(article) < 1)
            // 본인 글이 아니라 수정하지 못한 경우
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return "redirect:/app/article/articleView?articleId=" +
                article.getArticleId();
    }

    /**
     * 게시글 삭제. 삭제 후 현재 목록으로 redirect
     */
    @GetMapping("/article/deleteArticle")
    public String deleteArticle(int articleId, @SessionAttribute("ME") User me,
                                @SessionAttribute(CURRENT_ARTICLE_LIST) String currentArticleList) {
        if (articleDao.deleteArticle(articleId, me.getUserId()) < 1)
            // 본인 글이 아니라 삭제하지 못한 경우
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return "redirect:" + currentArticleList;
    }
}