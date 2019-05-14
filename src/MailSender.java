// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java
import com.sendgrid.*;
import java.io.IOException;

public class MailSender {
  public static void main(String[] args) throws IOException {
    Email from = new Email("osen@mondragon.edu");
    String subject = "Sending with SendGrid is Fun";
    Email to = new Email("imanol.rubio@alumni.mondragon.edu");
    Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid("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");
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException ex) {
      throw ex;
    }
  }
}