package com.da.blog.task;

import com.da.blog.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 百度推送的工具类
 * 将百度推送硬编码改为读配置文件
 *
 * 防止勿推送到我的域名,所以注释了
 * 自己在application.yml文件中配置
 * FILE: com.da.blog.task.BaiduTask.java
 * MOTTO:  不积跬步无以至千里,不积小流无以至千里
 */
@Component
public class BaiduTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${baidu.task.postUrl}")
    public String postUrl;

    @Value("${baidu.task.baseUrl}")
    public String baseUrl;
    @Autowired
    private ArticleService articleService;

    /**
     * 初始化HttpURLConnection
     * @return
     * @throws IOException
     */
    private HttpURLConnection initConnect() throws IOException {
        //建立URL之间的连接
        HttpURLConnection conn = (HttpURLConnection) new URL(postUrl).openConnection();
        //设置通用的请求属性
        conn.setRequestProperty("Host","data.zz.baidu.com");
        conn.setRequestProperty("User-Agent", "curl/7.12.1");
        conn.setRequestProperty("Content-Length", "83");
        conn.setRequestProperty("Content-Type", "text/plain");
        //发送POST请求必须设置如下两行
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }

    /**
     * 自动推送任务
     * @throws IOException
     */
    //@Scheduled(fixedDelay = 200000)
    public void postArticleUrl() throws IOException {
       String [] ids = articleService.getArticleId();
       writerUrl(initConnect(),ids);

    }

    /**
     * 重构推送文章的write方法
     * @param conn
     * @param ids
     * @throws IOException
     */
    private void writerUrl(HttpURLConnection conn,String... ids) throws IOException {
        PrintWriter out;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.length; i++){
            sb.append(baseUrl+"/article/details/"+ids[i]+"\n");
        }
        logger.info("百度推送的url为:{}",sb.toString());
        conn.connect();
        //获取conn对应的输出流
        out=new PrintWriter(conn.getOutputStream());
        //发送请求参数
        out.print(sb.toString().trim());
        //进行输出流的缓冲
        out.flush();
        int code = conn.getResponseCode();
        if (code == 200){
            logger.info("the article url push success");
        }

    }

    /**
     * 新增添加文章推送功能
     * @param articleId 文章id
     */
    public void pushOneArticle(String articleId) throws IOException {
        writerUrl(initConnect(),articleId);

    }
}
