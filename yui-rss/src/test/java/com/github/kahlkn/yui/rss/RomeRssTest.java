package com.github.kahlkn.yui.rss;

import org.junit.Test;

import java.util.Date;

public class RomeRssTest {

    public static RomeRss createRss(RomeRss.FeedType feedType) {
        RomeRss rss = RomeRss.on(feedType.toString(), "hello", "http://uux.me", "Hello, World! ");
        rss.addItem("Post1", "http://uux.me/1", new Date(), "Post1's XXXX ddddd ssss");
        rss.addItem("Post2", "http://uux.me/2", new Date(), "Post2's XXXX ddddd ssss");
        rss.addItem("Post3", "http://uux.me/3", new Date(), "Post3's XXXX ddddd ssss");
        rss.addItem("Post4", "http://uux.me/4", new Date(), "Post4's XXXX ddddd ssss");
        rss.addItem("Post5", "http://uux.me/5", new Date(), "Post5's XXXX ddddd ssss");
        rss.setImage("image", "http://image.com");
        return rss;
    }

    @Test
    public void test1() throws Exception {
        RomeRss.FeedType feedType = RomeRss.FeedType.RSS_2_0;
        RomeRss rss = createRss(feedType);

        String outputString = rss.outputString();
        // System.out.println(outputString);

        RomeRss on = RomeRss.on(outputString, "utf-8");
        System.out.println(on.outputString());
    }

    @Test
    public void test2() throws Exception {
        RomeRss.FeedType feedType = RomeRss.FeedType.RSS_2_0;
        RomeRssGenerator generator = new RomeRssGenerator(true, "yyyy-MM-dd HH:mm:ss");
        RomeRss rss = createRss(feedType).setFeedGenerator(generator);

        System.out.println(rss.outputString());
    }

}
