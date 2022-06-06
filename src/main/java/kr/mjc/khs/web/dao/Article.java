package kr.mjc.khs.web.dao;

import lombok.Data;

@Data
public class Article {
    int articleId;
    String title;
    String content;
    int userId;
    String name;
    String cdate;
    String udate;

    /***
     * new line에 <br/>을 붙이는 html 출력용 메서드
     */
    public String getContentHtml() {
        return this.content.replace("\n", "<br/>\n");
    }
}