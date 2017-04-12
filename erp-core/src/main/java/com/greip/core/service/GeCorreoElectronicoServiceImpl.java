package com.greip.core.service;

import com.greip.core.dto.GeCorreoDto;
import com.greip.core.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.BodyPart;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;

/**
 * Created by esvr on 29/03/17.
 */
@Service("geCorreoElectronicoService")
public class GeCorreoElectronicoServiceImpl implements GeCorreoElectronicoService{

    public static Logger log = Logger.getLogger(GeCorreoElectronicoServiceImpl.class);
    @Autowired
    private Environment env;

    @Override
    public int enviar(GeCorreoDto correoDto) throws ServiceException, UnsupportedEncodingException, MessagingException {
        String mailFrom = env.getProperty("mail.from");
        String mailFromName = env.getProperty("mail.from.name");
        Session session = null;

        boolean continuar = true;
        StringBuilder mensaje = new StringBuilder();

        Properties propiedades = obtenerConfiguracion();
        session = obtenerSession(propiedades);

        final Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailFrom, mailFromName));

        InternetAddress[] iasTo = null;
        if (correoDto.getTo() != null && correoDto.getTo().length > 0) {
            continuar = true;
            iasTo = new InternetAddress[correoDto.getTo().length];
            int i = 0;
            for (String em : correoDto.getTo()) {
                iasTo[i++] = new InternetAddress(em);
                //iasTo[i++].validate();
            }
            message.setRecipients(Message.RecipientType.TO, iasTo);
            message.setSubject(correoDto.getAsunto());
            message.setContent(correoDto.getCuerpo(), "text/html");
            message.removeHeader("Content-Type");
            message.addHeader("Content-Type", "text/html");

        } else {
            continuar = false;
            mensaje.append("No se encontraron destinatarios");
            return 0;
        }

        if (continuar) {
            InternetAddress[] iasCC = null;
            if (correoDto.getCc() != null && correoDto.getCc().length > 0) {
                continuar = true;
                iasCC = new InternetAddress[correoDto.getTo().length];
                int i = 0;
                for (String em : correoDto.getTo()) {
                    iasCC[i++] = new InternetAddress(em);
                }
                message.setRecipients(Message.RecipientType.CC, iasCC);
            }
        }

        if (continuar) {
            if (correoDto.getAdjuntos() != null && correoDto.getAdjuntos().length > 0) {
                MimeMultipart multiParte = new MimeMultipart();
                BodyPart body = null;
                FileDataSource fds = null;
                for (File f : correoDto.getAdjuntos()) {
                    body = new MimeBodyPart();
                    fds = new FileDataSource(f);
                    body.setDataHandler(new DataHandler(fds));
                    body.setFileName(f.getName());
                    multiParte.addBodyPart(body);
                }
                message.setContent(multiParte);
            }
        }


        if (continuar) {
            Thread principal = new Thread(new Runnable() {
                @Override
                public void run() {
    					try {
                            System.out.println("enviando email....");
							Transport.send(message);
                            System.out.println("email enviado!!");
						} catch (MessagingException e) {
                            //System.out.println("ERROR!! " + e.getMessage());
							// TODO Auto-generated catch block
							e.printStackTrace();
							log.error(e);
						}
                }
            });
            principal.start();

            return 1;
        } else {
            return 0;
        }
    }

    private Session obtenerSession(Properties props) {
        String mailUser = env.getProperty("mail.username");
        String mailFromPwd = env.getProperty("mail.password");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailUser,
                                mailFromPwd);
                    }
                });
        return session;
    }

    private Properties obtenerConfiguracion() {
        String mailServidor = env.getProperty("mail.host");
        String mailPuerto = env.getProperty("mail.port");
        Properties props = new Properties();
        props.put("mail.smtp.host", mailServidor);
        props.put("mail.smtp.port", mailPuerto);
        //props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");
        return props;
    }
}
