package com.github.kahlkn.yui.email;

import com.github.kahlkn.artoria.util.StringUtils;

import java.util.Properties;

public class Props {

    private String smtpHost;
    private Integer smtpPort;
    private String imapHost;
    private Integer imapPort;
    private String pop3Host;
    private Integer pop3Port;
    private String storeProtocol;
    private Boolean debug;
    private Boolean sslOnConnect;
    private String user;
    private String password;

    public static Props create() {
        return new Props();
    }

    public static Props create(String user, String password) {
        Props props = new Props();
        props.setUser(user);
        props.setPassword(password);
        return props;
    }

    private Props() {}

    public String getSmtpHost() {
        return smtpHost;
    }

    public Props setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
        return this;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public Props setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
        return this;
    }

    public String getImapHost() {
        return imapHost;
    }

    public Props setImapHost(String imapHost) {
        this.imapHost = imapHost;
        return this;
    }

    public Integer getImapPort() {
        return imapPort;
    }

    public Props setImapPort(Integer imapPort) {
        this.imapPort = imapPort;
        return this;
    }

    public String getPop3Host() {
        return pop3Host;
    }

    public Props setPop3Host(String pop3Host) {
        this.pop3Host = pop3Host;
        return this;
    }

    public Integer getPop3Port() {
        return pop3Port;
    }

    public Props setPop3Port(Integer pop3Port) {
        this.pop3Port = pop3Port;
        return this;
    }

    public String getStoreProtocol() {
        return storeProtocol;
    }

    public Props setStoreProtocol(String storeProtocol) {
        this.storeProtocol = storeProtocol;
        return this;
    }

    public Boolean isDebug() {
        return debug;
    }

    public Props setDebug(Boolean debug) {
        this.debug = debug;
        return this;
    }

    public Boolean isSslOnConnect() {
        return sslOnConnect;
    }

    public Props setSslOnConnect(Boolean sslOnConnect) {
        this.sslOnConnect = sslOnConnect;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Props setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Props setPassword(String password) {
        this.password = password;
        return this;
    }

    public Properties build() {
        Properties props = new Properties();
        props.putAll(System.getProperties());
        if (StringUtils.isNotBlank(smtpHost)) {
            props.setProperty("mail.smtp.host", smtpHost);
        }
        if (smtpPort != null) {
            props.setProperty("mail.smtp.port", smtpPort + "");
        }
        if (StringUtils.isNotBlank(imapHost)) {
            props.setProperty("mail.imap.host", imapHost);
        }
        if (imapPort != null) {
            props.setProperty("mail.imap.port", imapPort + "");
        }
        if (StringUtils.isNotBlank(pop3Host)) {
            props.setProperty("mail.pop3.host", pop3Host);
        }
        if (pop3Port != null) {
            props.setProperty("mail.pop3.port", pop3Port + "");
        }
        if (StringUtils.isNotBlank(storeProtocol)) {
            props.setProperty("mail.store.protocol", storeProtocol);
        }
        if (debug != null) {
            props.setProperty("mail.debug", debug + "");
        }
        if (sslOnConnect != null && sslOnConnect) {
            if (StringUtils.isNotBlank(smtpHost)) {
                props.setProperty("mail.smtp.ssl.enable", "true");
            }
            if (StringUtils.isNotBlank(imapHost)) {
                props.setProperty("mail.imap.ssl.enable", "true");
            }
        }
        return props;
    }

}
