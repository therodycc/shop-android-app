package com.example.shopping.screenMain.email;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail extends AsyncTask<String, Void, String> {

    private Context mContext;
    private Session mSession;
    private String mEmail;
    private String codigo;


    public JavaMail(Context mContext, String mEmail,String codigo) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.codigo= codigo;
    }


    @Override
    protected String doInBackground(String... params) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        mSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EmailApp.EMAIL, EmailApp.PASSWORD);
                    }
                });


        try {
            MimeMessage mm = new MimeMessage(mSession);

            mm.setFrom(new InternetAddress(EmailApp.EMAIL));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            mm.setSubject("VERIFICACIÓN DE SU CUENTA STARTBUYING");
            mm.setText("¡Te damos la bienvenida a la mejor App de Negocios, Gracias por Registrarte!\n" +
                    "Tu nueva clave de verificación es:" + codigo);
            Transport.send(mm);
            Toast.makeText(mContext,"Send Email",Toast.LENGTH_SHORT).show();

        } catch (MessagingException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
