package lk.ijse.project.layered.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;
import javax.mail.PasswordAuthentication;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sound.midi.MidiMessage;

import java.util.Properties;

public class SendMailController {

    public TextArea txtDescription;
    @Setter
    private String customerEmail;

    public TextField txtSubject;


    public void btnSendOnAction(ActionEvent actionEvent) {
        if (customerEmail == null || txtSubject == null || txtDescription == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All filled fields are empty");
            return;
        }
        String from = "malmidevindi26@gmail.com";
        String password = "hncc lvra ixud zsev";
        String to = customerEmail;

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(txtSubject.getText());
            message.setText(txtDescription.getText());

            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION, "Mail sent successfully").show();

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Mail sent Failed").show();
        }

    }
}