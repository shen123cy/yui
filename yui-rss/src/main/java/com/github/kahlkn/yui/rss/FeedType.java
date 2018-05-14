package com.github.kahlkn.yui.rss;

/**
 * RSS or ATOM type.
 * @author Kahle
 */
public enum FeedType {

    /**
     * RSS 0.90 Version
     */
    RSS_0_90("rss_0.90"),

    /**
     * RSS 0.91 Version
     */
    RSS_0_91("rss_0.91"),

    /**
     * RSS 0.92 Version
     */
    RSS_0_92("rss_0.92"),

    /**
     * RSS 0.93 Version
     */
    RSS_0_93("rss_0.93"),

    /**
     * RSS 0.94 Version
     */
    RSS_0_94("rss_0.94"),

    /**
     * RSS 1.0 Version
     */
    RSS_1_0("rss_1.0"),

    /**
     * RSS 2.0 Version
     */
    RSS_2_0("rss_2.0"),

    /**
     * ATOM 0.3 Version
     */
    ATOM_0_3("atom_0.3"),

    ;

    private String type;

    FeedType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

}
