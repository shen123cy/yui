package com.github.kahlkn.yui.email;

import com.github.kahlkn.artoria.util.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.util.List;
import java.util.Properties;

/**
 * @author Kahle
 */
public class EmailUtils {

    public static String toString(Address address) {
        try {
            if (address instanceof InternetAddress) {
                InternetAddress addr = (InternetAddress) address;
                String nickname = addr.getPersonal();
                nickname = StringUtils.isNotBlank(nickname) ? MimeUtility.decodeText(nickname) : "";
                return nickname + "<" + addr.getAddress() + ">";
            } else {
                return address.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return address.toString();
        }
    }

    public static String toString(Address[] addresss) {
        StringBuilder builder = new StringBuilder();
        for (Address address : addresss) {
            builder.append(toString(address)).append(", ");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static String toString(List<Address> addresss) {
        StringBuilder builder = new StringBuilder();
        for (Address address : addresss) {
            builder.append(toString(address)).append(", ");
        }
        builder.deleteCharAt(builder.length() - 2);
        return builder.toString();
    }

    public static void send(Message msg) throws MessagingException {
        Transport.send(msg);
    }

    public static void send(Message msg, Address[] addresses) throws MessagingException {
        Transport.send(msg, addresses);
    }

    public static void send(Message msg, String user, String password) throws MessagingException {
        Transport.send(msg, user, password);
    }

    public static void send(Message msg, Address[] addresses, String user, String password) throws MessagingException {
        Transport.send(msg, addresses, user, password);
    }

    public static Session getSession(Properties props) {
        return Session.getDefaultInstance(props, null);
    }

    public static Session getSession(Properties props, Authenticator authenticator) {
        return Session.getDefaultInstance(props, authenticator);
    }

    public static Session createSession(Properties props) {
        return Session.getInstance(props, null);
    }

    public static Session createSession(Properties props, Authenticator authenticator) {
        return Session.getInstance(props, authenticator);
    }

    public static Store getStore(Session session) throws MessagingException {
        Store store = session.getStore();
        store.connect();
        return store;
    }

    public static Store getStore(Session session, String protocol) throws MessagingException {
        Store store = session.getStore(protocol);
        store.connect();
        return store;
    }

    public static Store getStore(Session session, String user, String password) throws MessagingException {
        Store store = session.getStore();
        store.connect(user, password);
        return store;
    }

    public static Store getStore(Session session, String protocol, String user, String password) throws MessagingException {
        Store store = session.getStore(protocol);
        store.connect(user, password);
        return store;
    }

    public static Folder[] getAllFolders(Store store) throws MessagingException {
        return store.getDefaultFolder().list();
    }

    public static Folder getFolder(Store store, String folderName) throws MessagingException {
        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_ONLY);
        return folder;
    }

    public static Folder getFolder(Store store, String folderName, boolean isReadOnly) throws MessagingException {
        Folder folder = store.getFolder(folderName);
        folder.open(isReadOnly ? Folder.READ_ONLY : Folder.READ_WRITE);
        return folder;
    }

    public static Folder getInbox(Store store) throws MessagingException {
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        return folder;
    }

    public static Folder getInbox(Store store, boolean isReadOnly) throws MessagingException {
        Folder folder = store.getFolder("INBOX");
        folder.open(isReadOnly ? Folder.READ_ONLY : Folder.READ_WRITE);
        return folder;
    }

}
