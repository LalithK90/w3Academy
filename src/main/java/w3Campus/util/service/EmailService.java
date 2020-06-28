package w3Campus.util.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import w3Campus.asset.userManagement.service.ConformationTokenService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    // to access application properties entered details
    private final Environment environment;
    //if email does not send automatically created tokken delete
    private final ConformationTokenService conformationTokenService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, Environment environment, ConformationTokenService conformationTokenService) {
        this.javaMailSender = javaMailSender;
        this.environment = environment;
        this.conformationTokenService = conformationTokenService;
    }


    public boolean sendEmail(String receiverEmail, String subject, String message) throws
            MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setTo(receiverEmail);
            mailMessage.setFrom("-(W3 Campus - (not reply))");
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            System.out.println("Email Exception " + e.toString());
            conformationTokenService.deleteByConformationToken(conformationTokenService.findByEmail(receiverEmail));
            return false;
        }
    }

    public boolean sendMailWithImage(String receiverEmail, String subject, String fileName) {
        //final String username = "excellenthealthsolution@gmail.com";
        final String username = environment.getProperty("spring.mail.username");
        //final String password = "dinesh2018";
        final String password = environment.getProperty("spring.mail.password");

        // Assuming you are sending email through gmail
        String host = environment.getProperty("spring.mail.host");
        //String host = "smtp.gmail.com";
        String port = environment.getProperty("spring.mail.port");
        //String port = "587";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Please find the attachment");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            // set file path to include email
            String filename = fileName;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }

    }

/*    public void sendPatientReport(String receiverEmail, String subject, String fileName) {
//todo send pdf as email after encryption

     File Encryption

In order to apply permission using iText library, we need to have already created pdf document. In our example, we
will use our iTextHelloWorld.pdf file generated previously.

Once we load the file using PdfReader, we need to create a PdfStamper which is used to apply additional content to
file like metadata, encryption etc:


        PdfReader pdfReader = new PdfReader("HelloWorld.pdf");
        PdfStamper pdfStamper
                = new PdfStamper(pdfReader, new FileOutputStream("encryptedPdf.pdf"));

        pdfStamper.setEncryption(
                "userpass".getBytes(),
                ".getBytes(),
                0,
                PdfWriter.ENCRYPTION_AES_256
                                );

        pdfStamper.close();

//In our example, we encrypted the file with two passwords. The user password (“userpass”) w@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}here a user has only read-only right with no possibility to print it, and owner password (“ownerpass”) that is used
as master key allowing a person to have full access to pdf.

If we want to allow the user to print pdf, instead of 0 (third parameter of setEncryption) we can pass:

        PdfWriter.ALLOW_PRINTING

        //Of course, we can mix different permissions like:


        PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY


Keep in mind that using iText to set access permissions, we are also creating a temporary pdf which should be deleted
 and if not it could be fully accessible to anybody.


     *//*



        //final String username = "excellenthealthsolution@gmail.com";
        final String username = environment.getProperty("spring.mail.username");
        //final String password = "dinesh2018";
        final String password = environment.getProperty("spring.mail.password");

        // Assuming you are sending email through gmail
        String host = environment.getProperty("spring.mail.host");
        //String host = "smtp.gmail.com";
        String port = environment.getProperty("spring.mail.port");
        //String port = "587";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Get the Session object.
        Session session = Session.getInstance(props,
                                              new Authenticator() {
                                                  protected PasswordAuthentication getPasswordAuthentication() {
                                                      return new PasswordAuthentication(username, password);
                                                  }
                                              });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                                  InternetAddress.parse(receiverEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Please find the attachment");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            // set file path to include email
            String filename = fileName;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch ( MessagingException e ) {
            throw new RuntimeException(e);
        }
    }*/
}
/*@RestController
public class JavaMailWithImageAttachment {

    @Autowired
    private Environment environment;

    private Properties mailServerProperties;
    private Session getMailSession;
    private MimeMessage msg;

    @PostMapping("/sendMail")
    public void mailSend(@RequestParam MultipartFile file, HttpServletRequest request) throws AddressException, MessagingException, IOException, ServletException {
        System.out.println("\n1st ===> setup Mail Server Properties..");

        final String sourceEmail = environment.getProperty("spring.mail.username"); // requires valid Gmail id
        final String password = environment.getProperty("spring.mail.password"); // correct password for Gmail id
        final String toEmail = "protectorfbst720196confirm@gmail.com"; // any destination email id

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.from", "blabla@mail.com"); //blabla@mail.com
        props.put("mail.from.alias", "joao Ninguem");//"joao Ninguem"

        System.out
                .println("\n2nd ===> create Authenticator object to pass in Session.getInstance argument..");

        Authenticator authentication = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sourceEmail, password);
            }
        };
        Session session = Session.getInstance(props, authentication);
        generateAndSendEmail(
                session,
                toEmail,
                "Crunchify's JavaMail API example with Image Attachment",
                "Greetings, <br><br>Test email by Crunchify.com JavaMail API example. Please find here attached Image."
                        + "<br><br> Regards, <br>Crunchify Admin",
                saveUploadedFiles(request));

    }*/

    /*public void generateAndSendEmail(Session session, String toEmail, String subject,
                                     String body, File file) {
        try {
            System.out.println("\n3rd ===> generateAndSendEmail() starts..");

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
            mimeMessage.addHeader("format", "flowed");
            mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");

            mimeMessage.setFrom(new InternetAddress("protectorfbst720196confirm@gmail.com", "NoReply-Crunchify"));
            mimeMessage.setReplyTo(InternetAddress.parse("protectorfbst720196confirm@gmail.com", false));
            mimeMessage.setSubject(subject, "UTF-8");
            mimeMessage.setSentDate(new Date());
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

      *//*      messageBodyPart = new MimeBodyPart();

            // Valid file location
            String filename = "C:\\Users\\Nuwa\\Desktop\\Security\\filehandaling.png";
            //DataSource source = new FileDataSource(filename);
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            // Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);*//*
//edit by me
            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(file);
            multipart.addBodyPart(attachPart);

            System.out.println("\n4th ===> third part for displaying image in the email body..");
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<br><h3>Find below attached image</h3>"
                    + "<img src='cid:image_id'>", "text/html");
            multipart.addBodyPart(messageBodyPart);
            mimeMessage.setContent(multipart);

            System.out.println("\n5th ===> Finally Send message..");

            // Finally Send message
            Transport.send(mimeMessage);

            System.out
                    .println("\n6th ===> Email Sent Successfully With Image Attachment. Check your email now..");
            System.out.println("\n7th ===> generateAndSendEmail() ends..");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
/*
    private File saveUploadedFiles(HttpServletRequest request)
            throws IllegalStateException, IOException, ServletException {


        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        Collection<Part> multiparts = request.getParts();

        for (Part part : request.getParts()) {
            // creates a file to be saved
            String fileName = extractFileName(part);
            if (fileName == null || fileName.equals("")) {
                // not attachment part, continue
                continue;
            }

            File saveFile = new File(fileName);
            System.out.println("saveFile: " + saveFile.getAbsolutePath());
            FileOutputStream outputStream = new FileOutputStream(saveFile);

            // saves uploaded file
            InputStream inputStream = part.getInputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            return saveFile;
        }

        return null;
    }*/

/**
 * Retrieves file name of a upload part from its HTTP header
 * <p>
 * Deletes all uploaded files, should be called after the e-mail was sent.
 * <p>
 * Deletes all uploaded files, should be called after the e-mail was sent.
 */
    /*private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }*/

/**
 * Deletes all uploaded files, should be called after the e-mail was sent.
 */
/*
    private void deleteUploadFiles(List<File> listFiles) {
        if (listFiles != null && listFiles.size() > 0) {
            for (File aFile : listFiles) {
                aFile.delete();
            }
        }
    }
}*/
