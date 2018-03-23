package com.github.kahlkn.yui.rss;

import com.github.kahlkn.artoria.time.DateUtils;
import com.github.kahlkn.artoria.util.StringUtils;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.impl.RSS20Generator;
import org.jdom.CDATA;
import org.jdom.Element;

import java.util.Date;

/**
 * @author Kahle
 */
public class RomeRssGenerator extends RSS20Generator {
    private boolean doCData;
    private String pattern;

    public RomeRssGenerator() {
        this("rss_2.0","2.0", false, null);
    }

    public RomeRssGenerator(RomeRss.FeedType feedType) {
        this(feedType.getType(), feedType.getVersion(), false, null);
    }

    public RomeRssGenerator(String feedType, String version) {
        this(feedType, version, false, null);
    }

    public RomeRssGenerator(boolean doCData, String pattern) {
        this("rss_2.0","2.0", doCData, pattern);
    }

    public RomeRssGenerator(RomeRss.FeedType feedType, boolean doCData, String pattern) {
        this(feedType.getType(), feedType.getVersion(), doCData, pattern);
    }

    public RomeRssGenerator(String feedType, String version, boolean doCData, String pattern) {
        super(feedType, version);
        this.doCData = doCData;
        this.pattern = pattern;
    }

    @Override
    protected void addItem(Item item, Element parent, int index) throws FeedException {
        Element eItem = new Element("item", getFeedNamespace());
        populateItem(item,eItem, index);
        checkItemConstraints(eItem);
        generateItemModules(item.getModules(),eItem);

        if (doCData) {
            Element desc = eItem.getChild("description");
            String text = desc.getValue();
            desc.setContent(new CDATA(text));
        }

        parent.addContent(eItem);
    }

    @Override
    public void populateItem(Item item, Element eItem, int index) {
        super.populateItem(item, eItem, index);

        Date pubDate = item.getPubDate();
        if (pubDate != null && StringUtils.isNotBlank(pattern)) {
            eItem.removeChild("pubDate");
            eItem.addContent(generateSimpleElement("pubDate", DateUtils.format(pubDate, pattern)));
        }

    }

}
