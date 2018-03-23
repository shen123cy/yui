package com.github.kahlkn.yui.email;

import com.github.kahlkn.artoria.io.IOUtils;
import com.github.kahlkn.artoria.time.DateTime;
import com.github.kahlkn.artoria.util.ArrayUtils;
import com.github.kahlkn.artoria.util.CollectionUtils;
import com.github.kahlkn.artoria.util.MapUtils;
import com.github.kahlkn.artoria.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import static com.github.kahlkn.artoria.util.Const.ENDL;

/**
 * @author Kahle
 */
public class Email {
    private String charset = Charset.defaultCharset().name();

    private Props props;
    private Session session;
    private Boolean debug;
    private String user;
    private String password;

    private List<Address> from = new ArrayList<Address>();
    private List<Address> to = new ArrayList<Address>();
    private List<Address> cc = new ArrayList<Address>();
    private List<Address> bcc = new ArrayList<Address>();
    private Date sentDate;
    private String subject;
    private String textContent;
    private String htmlContent;
    private Map<String, File> files = new HashMap<String, File>();

    private Message message;
    private String messageID;
    private String priority;
    private Integer size;
    private Boolean isRead;
    private boolean isTextEmail = false;
    private boolean isHaveAttach = false;

    public static Email create() {
        return new Email();
    }

    public static Email create(Session session) {
        Email email = new Email();
        email.session = session;
        return email;
    }

    public static Email create(Props props) {
        Email email = new Email();
        email.props = props;
        return email;
    }

    private Email() {}

    public String getCharset() {
        return charset;
    }

    public Email setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public Props getProps() {
        return props;
    }

    public Email setProps(Props props) {
        this.props = props;
        return this;
    }

    public Session getSession() {
        return session;
    }

    public Email setSession(Session session) {
        this.session = session;
        return this;
    }

    public Boolean isDebug() {
        return debug;
    }

    public Email setDebug(Boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Email setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Email setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<Address> getFrom() {
        return from;
    }

    public Email setFrom(Address... from) {
        this.from.clear();
        this.from.addAll(Arrays.asList(from));
        return this;
    }

    public Email addFrom(Address... from) {
        this.from.addAll(Arrays.asList(from));
        return this;
    }

    public Email setFrom(String from) {
        try {
            return setFrom(InternetAddress.parse(from));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Email addFrom(String from) {
        try {
            return addFrom(InternetAddress.parse(from));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Email setFrom(String from, String displayName) {
        try {
            String encodeName = MimeUtility.encodeText(displayName);
            return setFrom(new InternetAddress(encodeName + " <" + from + ">"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Email addFrom(String from, String displayName) {
        try {
            String encodeName = MimeUtility.encodeText(displayName);
            return addFrom(new InternetAddress(encodeName + " <" + from + ">"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Address> getTo() {
        return to;
    }

    public Email setTo(Address... to) {
        this.to.clear();
        this.to.addAll(Arrays.asList(to));
        return this;
    }

    public Email addTo(Address... to) {
        this.to.addAll(Arrays.asList(to));
        return this;
    }

    public Email setTo(String to) {
        try {
            return setTo(InternetAddress.parse(to));
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    public Email addTo(String to) {
        try {
            return addTo(InternetAddress.parse(to));
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Address> getCc() {
        return cc;
    }

    public Email setCc(Address... cc) {
        this.cc.clear();
        this.cc.addAll(Arrays.asList(cc));
        return this;
    }

    public Email addCc(Address... cc) {
        this.cc.addAll(Arrays.asList(cc));
        return this;
    }

    public Email setCc(String cc) {
        try {
            return setCc(InternetAddress.parse(cc));
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    public Email addCc(String cc) {
        try {
            return addCc(InternetAddress.parse(cc));
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Address> getBcc() {
        return bcc;
    }

    public Email setBcc(Address... bcc) {
        this.bcc.clear();
        this.bcc.addAll(Arrays.asList(bcc));
        return this;
    }

    public Email addBcc(Address... bcc) {
        this.bcc.addAll(Arrays.asList(bcc));
        return this;
    }

    public Email setBcc(String bcc) {
        try {
            return setBcc(InternetAddress.parse(bcc));
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    public Email addBcc(String bcc) {
        try {
            return addBcc(InternetAddress.parse(bcc));
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    public Date getSentDate() {
        return sentDate;
    }

    public Email setSentDate(Date sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Email setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public Email setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public Email setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
        return this;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public Email setFiles(List<File> files) {
        this.files.clear();
        for (File file : files) {
            this.files.put(file.getName(), file);
        }
        return this;
    }

    public Email addFiles(List<File> files) {
        for (File file : files) {
            this.files.put(file.getName(), file);
        }
        return this;
    }

    public Email setFiles(File... files) {
        this.files.clear();
        for (File file : files) {
            this.files.put(file.getName(), file);
        }
        return this;
    }

    public Email addFiles(File... files) {
        for (File file : files) {
            this.files.put(file.getName(), file);
        }
        return this;
    }

    public Email addFile(String fName, File file) {
        fName = StringUtils.isBlank(fName) ? file.getName() : fName;
        this.files.put(fName, file);
        return this;
    }

    public Message getMessage() {
        return message;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getPriority() {
        return priority;
    }

    public Integer getSize() {
        return size;
    }

    public Boolean isRead() {
        return isRead;
    }

    public boolean isTextEmail() {
        return isTextEmail;
    }

    public boolean isHaveAttach() {
        return isHaveAttach;
    }

    public Email saveAttach(File dir)
            throws IOException, MessagingException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Mkdirs failure. ");
        }
        if (isHaveAttach && MapUtils.isEmpty(this.files)) {
            List<File> files = saveAttachment(message, dir);
            if (CollectionUtils.isNotEmpty(files)) {
                this.addFiles(files);
            }
        }
        return this;
    }

    public MimeMessage build()
            throws MessagingException, IOException {
        if (session == null) {
            if (props != null) {
                session = Session.getDefaultInstance(props.build());
            } else {
                throw new NullPointerException("Session and Email Props both are null. ");
            }
        }
        if (debug != null) {
            session.setDebug(debug);
        }
        MimeMessage message = new MimeMessage(session);
        if (CollectionUtils.isNotEmpty(from)) {
            Address[] tmp = new Address[from.size()];
            from.toArray(tmp);
            message.addFrom(tmp);
        } else {
            throw new NullPointerException("From is null. ");
        }
        if (CollectionUtils.isNotEmpty(to)) {
            Address[] tmp = new Address[to.size()];
            to.toArray(tmp);
            message.addRecipients(Message.RecipientType.TO, tmp);
        } else {
            throw new NullPointerException("To is null. ");
        }
        if (CollectionUtils.isNotEmpty(cc)) {
            Address[] tmp = new Address[cc.size()];
            cc.toArray(tmp);
            message.addRecipients(Message.RecipientType.CC, tmp);
        }
        if (CollectionUtils.isNotEmpty(bcc)) {
            Address[] tmp = new Address[bcc.size()];
            bcc.toArray(tmp);
            message.addRecipients(Message.RecipientType.BCC, tmp);
        }
        if (sentDate != null) {
            message.setSentDate(sentDate);
        }
        if (StringUtils.isNotBlank(subject)) {
            message.setSubject(subject, charset);
        }
        if (StringUtils.isNotBlank(htmlContent) || MapUtils.isNotEmpty(files)) {
            MimeMultipart mimeMultipart = new MimeMultipart();
            if (StringUtils.isNotBlank(htmlContent)) {
                String contentType = String.format("text/html; charset=%s", charset.toLowerCase());
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(htmlContent, contentType);
                mimeMultipart.addBodyPart(mimeBodyPart);
            }
            if (MapUtils.isNotEmpty(files)) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    MimeBodyPart filePart = new MimeBodyPart();
                    filePart.setDataHandler(new DataHandler(new FileDataSource(entry.getValue())));
                    filePart.setFileName(MimeUtility.encodeWord(entry.getKey(), charset, "B"));
                    mimeMultipart.addBodyPart(filePart);
                }
            }
            message.setContent(mimeMultipart);
        } else if (StringUtils.isNotBlank(textContent)) {
            message.setText(textContent, charset);
        }
        return message;
    }

    public Email send()
            throws MessagingException, IOException {
        MimeMessage message = this.build();
        if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(password)) {
            Transport.send(message, user, password);
        } else {
            String user = null, password = null;
            if (props != null) {
                user = props.getUser();
                password = props.getPassword();
            }
            if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(password)) {
                Transport.send(message, user, password);
            } else {
                Transport.send(message);
            }
        }
        messageID = message.getMessageID();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageID   : ").append(messageID).append(ENDL);
        String fromStr = from.size() > 2 ? EmailUtils.toString(from.subList(0, 2)) + " ..." : EmailUtils.toString(from);
        builder.append("From        : ").append(fromStr).append(ENDL);
        String toStr = to.size() > 2 ? EmailUtils.toString(to.subList(0, 2)) + " ..." : EmailUtils.toString(to);
        builder.append("To          : ").append(toStr).append(ENDL);
        if (CollectionUtils.isNotEmpty(cc)) {
            String ccStr = cc.size() > 2 ? EmailUtils.toString(cc.subList(0, 2)) + " ..." : EmailUtils.toString(cc);
            builder.append("Cc          : ").append(ccStr).append(ENDL);
        }
        if (CollectionUtils.isNotEmpty(bcc)) {
            String bccStr = bcc.size() > 2 ? EmailUtils.toString(bcc.subList(0, 2)) + " ..." : EmailUtils.toString(bcc);
            builder.append("Bcc         : ").append(bccStr).append(ENDL);
        }
        builder.append("SentDate    : ").append(DateTime.create(sentDate).toString()).append(ENDL);
        builder.append("Priority    : ").append(priority).append(ENDL);
        builder.append("Size        : ").append(size).append(" Byte").append(ENDL);
        builder.append("HasAttach   : ").append(isHaveAttach).append(ENDL);
        builder.append("IsRead      : ").append(isRead).append(ENDL);
        builder.append("IsTextEmail : ").append(isTextEmail).append(ENDL);
        String subjt = subject.trim();
        subjt = subjt.length() > 36 ? subjt.substring(0, 36) : subjt;
        builder.append("Subject     : ").append(subjt).append(ENDL);
        String content = isTextEmail ? textContent : htmlContent;
        content = content.trim().replaceAll("\\s", " ");
        content = content.length() > 36 ? content.substring(0, 36) : content;
        builder.append("Summary     : ").append(content).append(ENDL);
        return builder.toString();
    }

    private static String getContent(Part part)
            throws MessagingException, IOException {
        StringBuilder builder = new StringBuilder();
        boolean containAttach = part.getContentType().contains("name");
        if (part.isMimeType("text/*") && !containAttach) {
            builder.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            builder.append(getContent((Part) part.getContent()));
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                builder.append(getContent(bodyPart));
            }
        }
        return builder.toString();
    }

    private static boolean containAttachment(Part part)
            throws MessagingException, IOException {
        boolean hasAttach = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bPart = multipart.getBodyPart(i);
                String disp = bPart.getDisposition();
                boolean isAttach = disp != null, isInline = isAttach;
                isAttach = isAttach && disp.equalsIgnoreCase(Part.ATTACHMENT);
                isInline = isInline && disp.equalsIgnoreCase(Part.INLINE);
                if (isAttach || isInline) {
                    hasAttach = true;
                } else if (bPart.isMimeType("multipart/*")) {
                    hasAttach = containAttachment(bPart);
                } else {
                    if (bPart.isMimeType("application/*")) {
                        hasAttach = true;
                    }
                    if (bPart.getContentType().contains("name")) {
                        hasAttach = true;
                    }
                }
                if (hasAttach) {
                    break;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            hasAttach = containAttachment((Part) part.getContent());
        }
        return hasAttach;
    }

    private static List<File> saveAttachment(Part part, File dir)
            throws MessagingException, IOException {
        List<File> files = new ArrayList<File>();
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bPart = multipart.getBodyPart(i);
                String disp = bPart.getDisposition();
                boolean isAttach = disp != null, isInline = isAttach;
                boolean hasName = isAttach, isApplication = isAttach;
                isAttach = isAttach && disp.equalsIgnoreCase(Part.ATTACHMENT);
                isInline = isInline && disp.equalsIgnoreCase(Part.INLINE);
                hasName = hasName && bPart.getContentType().contains("name");
                isApplication = isApplication && bPart.isMimeType("application/*");
                if (isAttach || isInline || hasName || isApplication) {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = bPart.getInputStream();
                        String fName = bPart.getFileName();
                        fName = MimeUtility.decodeText(fName);
                        File dest = new File(dir, fName);
                        if (!dest.createNewFile()) {
                            String msg = "Create file failure. File : ";
                            msg += dest.toString();
                            throw new IOException(msg);
                        }
                        files.add(dest);
                        out = new FileOutputStream(dest);
                        IOUtils.copyLarge(in, out);
                        out.flush();
                    } finally {
                        IOUtils.closeQuietly(in);
                        IOUtils.closeQuietly(out);
                    }
                } else if (bPart.isMimeType("multipart/*")) {
                    files.addAll(saveAttachment(bPart, dir));
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            files.addAll(saveAttachment((Part) part.getContent(), dir));
        }
        return files;
    }

    public static Email parse(Message message)
            throws MessagingException, IOException {
        Email email = new Email();
        email.message = message;
        if (message instanceof MimeMessage) {
            email.messageID = ((MimeMessage) message).getMessageID();
        }
        String[] xPrioHeaders = message.getHeader("X-Priority");
        email.priority = ArrayUtils.isNotEmpty(xPrioHeaders) ? xPrioHeaders[0] : null;
        email.size = message.getSize();
        email.isRead = message.getFlags().contains(Flags.Flag.SEEN);
        email.sentDate = message.getSentDate();
        email.from.addAll(Arrays.asList(message.getFrom()));
        email.to.addAll(Arrays.asList(message.getRecipients(Message.RecipientType.TO)));
        Address[] tmp = message.getRecipients(Message.RecipientType.CC);
        if (tmp != null) {
            email.cc.addAll(Arrays.asList(tmp));
        }
        tmp = message.getRecipients(Message.RecipientType.BCC);
        if (tmp != null) {
            email.bcc.addAll(Arrays.asList(tmp));
        }
        email.subject = MimeUtility.decodeText(message.getSubject());
        if (message.isMimeType("text/*") && !message.getContentType().contains("name")) {
            email.isTextEmail = true;
            email.textContent = message.getContent().toString();
        } else {
            email.isTextEmail = false;
            email.htmlContent = getContent(message);
        }
        email.isHaveAttach = containAttachment(message);
        return email;
    }

}
