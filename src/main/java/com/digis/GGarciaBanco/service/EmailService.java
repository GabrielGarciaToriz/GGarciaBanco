package com.digis.ggarciabanco.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoTarjeta(String destinatario, String public_id, String nombreUsuario, String numeroTarjeta) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("¡Bienvenido a GGarciaBanco! Tu cuenta y tarjeta están listas");

            String contenidoHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #f8fafc; color: #1e293b; margin: 0; padding: 0; }
                        .container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 12px rgba(0,0,0,0.05); border: 1px solid #e2e8f0; }
                        .header { background-color: #1e3a8a; padding: 30px; text-align: center; color: #ffffff; }
                        .header h1 { margin: 0; font-size: 24px; font-weight: 600; letter-spacing: 0.5px; }
                        .content { padding: 40px 30px; line-height: 1.6; }
                        .content p { margin: 0 0 20px; font-size: 16px; color: #334155; }
                        .creds-box { background-color: #f1f5f9; border-left: 4px solid #3b82f6; border-radius: 6px; padding: 20px; margin: 25px 0; }
                        .creds-row { display: flex; margin-bottom: 12px; font-size: 15px; }
                        .creds-row:last-child { margin-bottom: 0; }
                        .creds-label { font-weight: 600; color: #475569; width: 150px; flex-shrink: 0; }
                        .creds-value { color: #0f172a; font-family: 'Courier New', Courier, monospace; font-weight: bold; font-size: 16px; letter-spacing: 1px; }
                        .footer { background-color: #f8fafc; padding: 20px; text-align: center; font-size: 13px; color: #94a3b8; border-top: 1px solid #e2e8f0; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>GGarciaBanco</h1>
                        </div>
                        <div class="content">
                            <p>Hola <strong>%s</strong>,</p>
                            <p>Queremos darte la más cordial bienvenida a nuestra institución financiera. Tu cuenta ha sido dada de alta de forma exitosa y tus credenciales de acceso digital han sido generadas.</p>
                            
                            <p>A continuación, te proporcionamos los detalles de tu nueva cuenta. Te sugerimos guardar esta información en un lugar seguro:</p>
                            
                            <div class="creds-box">
                                <div class="creds-row">
                                    <span class="creds-label">ID de Usuario:</span>
                                    <span class="creds-value">%s</span>
                                </div>
                                <div class="creds-row">
                                    <span class="creds-label">Número de Tarjeta:</span>
                                    <span class="creds-value">%s</span>
                                </div>
                            </div>
                            
                            <p>A partir de este momento, puedes utilizar tu ID de Usuario o tu número de tarjeta para ingresar a nuestro portal web o aplicación móvil y comenzar a realizar operaciones.</p>
                            <p>Si tienes alguna duda o no reconoces este registro, por favor ponte en contacto de inmediato con nuestro centro de atención telefónica.</p>
                        </div>
                        <div class="footer">
                            Este es un mensaje automatizado, por favor no respondas a esta dirección de correo.<br>
                            &copy; 2026 GGarciaBanco. Todos los derechos reservados.
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(nombreUsuario, public_id, numeroTarjeta);

            helper.setText(contenidoHtml, true);
            mailSender.send(mensaje);

        } catch (Exception e) {
            throw new RuntimeException("Error al estructurar o enviar el correo HTML: " + e.getMessage(), e);
        }
    }

    public void enviarCorreoVerificacion(String destinatario, String nombreUsuario, String tokenVerificacion) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("Acción Requerida: Verifica tu cuenta en GGarciaBanco");

            // URL hacia tu frontend en Angular
            String urlActivacion = "http://192.167.0.190:4200/activar?token=" + tokenVerificacion;

            String contenidoHtml = """
                <!DOCTYPE html>
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f5; padding: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; background: #ffffff; padding: 30px; border-radius: 8px; text-align: center;">
                        <h2 style="color: #1e3a8a;">Confirma tu correo electrónico</h2>
                        <p>Hola <strong>%s</strong>,</p>
                        <p>Gracias por registrarte. Para generar tu tarjeta y activar tu cuenta, por favor haz clic en el siguiente botón:</p>
                        <a href="%s" style="display: inline-block; background-color: #2563eb; color: #ffffff; padding: 12px 24px; text-decoration: none; border-radius: 6px; font-weight: bold; margin: 20px 0;">Verificar mi cuenta</a>
                        <p style="font-size: 12px; color: #64748b;">Si no solicitaste este registro, ignora este correo.</p>
                    </div>
                </body>
                </html>
                """.formatted(nombreUsuario, urlActivacion);

            helper.setText(contenidoHtml, true);
            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo de verificación: " + e.getMessage(), e);
        }
    }

    // ============================================================
// AGREGAR este metodo a tu EmailService.java existente
// Colócalo despues de enviarCorreoVerificacion()
// ============================================================
    public void enviarCorreoResetPassword(String destinatario, String nombreUsuario, String token) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("Solicitud de cambio de contrasena - GGarciaBanco");

            String urlReset = "http://192.167.0.213:4200/reset-password?token=" + token;

            String contenidoHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #f8fafc; color: #1e293b; margin: 0; padding: 0; }
                        .container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 12px rgba(0,0,0,0.05); border: 1px solid #e2e8f0; }
                        .header { background-color: #1e3a8a; padding: 30px; text-align: center; color: #ffffff; }
                        .header h1 { margin: 0; font-size: 24px; font-weight: 600; letter-spacing: 0.5px; }
                        .content { padding: 40px 30px; line-height: 1.6; }
                        .content p { margin: 0 0 20px; font-size: 16px; color: #334155; }
                        .btn { display: inline-block; background-color: #2563eb; color: #ffffff; padding: 14px 28px; text-decoration: none; border-radius: 6px; font-weight: bold; margin: 10px 0 20px; font-size: 16px; }
                        .warning-box { background-color: #fef3c7; border-left: 4px solid #f59e0b; border-radius: 6px; padding: 14px 18px; margin: 20px 0; font-size: 14px; color: #92400e; }
                        .footer { background-color: #f8fafc; padding: 20px; text-align: center; font-size: 13px; color: #94a3b8; border-top: 1px solid #e2e8f0; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>GGarciaBanco</h1>
                        </div>
                        <div class="content">
                            <p>Hola <strong>%s</strong>,</p>
                            <p>Recibimos una solicitud para restablecer la contrasena de tu cuenta. Haz clic en el siguiente boton para continuar:</p>
                            <div style="text-align: center;">
                                <a href="%s" class="btn">Restablecer contrasena</a>
                            </div>
                            <div class="warning-box">
                                <strong>Importante:</strong> Este enlace expira en <strong>15 minutos</strong> y solo puede usarse una vez.
                            </div>
                            <p style="font-size: 14px; color: #64748b;">Si no solicitaste este cambio, puedes ignorar este correo. Tu contrasena actual no ha sido modificada.</p>
                        </div>
                        <div class="footer">
                            Este es un mensaje automatizado, por favor no respondas a esta direccion de correo.<br>
                            &copy; 2026 GGarciaBanco. Todos los derechos reservados.
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(nombreUsuario, urlReset);

            helper.setText(contenidoHtml, true);
            mailSender.send(mensaje);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo de reset: " + e.getMessage(), e);
        }
    }
}
