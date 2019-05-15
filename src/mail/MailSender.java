package mail;

import com.sendgrid.*;
import java.io.IOException;

public class MailSender {
	static SendGrid sg;
	public MailSender(String key) {
		sg=new SendGrid(key);
	}
	public void sendMail() throws IOException {
    Email from = new Email("noreply@osen.edu");
    String subject = "Sending with SendGrid is Fun";
    Email to = new Email("imanol.rubio@alumni.mondragon.edu");
    Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
    Mail mail = new Mail(from, subject, to, content);
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