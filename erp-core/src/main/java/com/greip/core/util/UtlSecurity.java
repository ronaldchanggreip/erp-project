/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greip.core.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Administrador
 */
public class UtlSecurity {

    public static String passwordEncoder(String password){
        String encode = "";
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
            encode = passwordEncoder.encode(password);
        }catch (Exception ex){

        }
        return encode;
    }

    public static void encriptarArchivo(String archivo) {
        try {
            File desFile = new File(archivo);
            FileInputStream fis;
            FileOutputStream fos;
            CipherInputStream cis;
            // Creaci�n de llave sercreta
            byte key[] = "abcdEFGH".getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
            // Creacion de objetos de cifrado
            Cipher encrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
            encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
            // abriendo el archivo Plaintext
            fis = new FileInputStream(desFile);
            cis = new CipherInputStream(fis, encrypt);

            // Escribiendo el archivo encriptado
            fos = new FileOutputStream(desFile.getParent() + desFile.getName()
                    + ".sion");
            byte[] b = new byte[8];
            int i = cis.read(b);
            while (i != -1) {
                fos.write(b, 0, i);
                i = cis.read(b);
            }
            fos.flush();
            fos.close();
            cis.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();

            javax.swing.JOptionPane.showMessageDialog(null,
                    "Imposible abrir el archivo!!!, o nombre incorrecto");
        }
    }

    public static void desencriptarArchivo(String archivoEncriptado,
                                           String archivoDesencriptado) {
        try {
            File desFile = new File(archivoEncriptado);
            File desFileBis = new File(archivoDesencriptado);
            FileInputStream fis;
            FileOutputStream fos;
            CipherInputStream cis;

            byte key[] = "abcdEFGH".getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
            Cipher decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
            decrypt.init(Cipher.DECRYPT_MODE, secretKey);
            fis = new FileInputStream(desFile);
            cis = new CipherInputStream(fis, decrypt);
            fos = new FileOutputStream(desFileBis);
            byte[] b = new byte[8];
            int i = cis.read(b);
            while (i != -1) {
                fos.write(b, 0, i);
                i = cis.read(b);
            }
            fos.flush();
            fos.close();
            cis.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final byte[] SALT_BYTES = {(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03};
    private static final int ITERATION_COUNT = 19;
    private static String sKeyPhrase = "abcdEFGH";

    public static String encriptarTexto(String textoDesencriptado) {
        Cipher oECipher = null;
        Cipher oDCipher = null;
        String sStringValue = textoDesencriptado;
        try {
            // Crear la key
            KeySpec oKeySpec = new PBEKeySpec(sKeyPhrase.toCharArray(), SALT_BYTES, ITERATION_COUNT);
            SecretKey oKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(oKeySpec);
            oECipher = Cipher.getInstance(oKey.getAlgorithm());
            oDCipher = Cipher.getInstance(oKey.getAlgorithm());

            // Preparar los parametros para los ciphers
            AlgorithmParameterSpec oaramSpec = new PBEParameterSpec(SALT_BYTES, ITERATION_COUNT);

            // Crear los ciphers
            oECipher.init(Cipher.ENCRYPT_MODE, oKey, oaramSpec);
            oDCipher.init(Cipher.DECRYPT_MODE, oKey, oaramSpec);

            // Encodear la cadena a bytes usando UTF-8
            byte[] oUTF8 = sStringValue.getBytes("UTF8");

            // Encriptar
            byte[] oEnc = oECipher.doFinal(oUTF8);

            // Encodear bytes a base64 para obtener cadena
            return new sun.misc.BASE64Encoder().encode(oEnc);
        } catch (Exception oE1) {
            oE1.printStackTrace();
        }
        return null;
    }

    public static String desencriptarTexto(String textoEncriptado) {
        Cipher oECipher = null;
        Cipher oDCipher = null;
        String sStringValue = textoEncriptado;
        try {
            // Crear la key
            KeySpec oKeySpec = new PBEKeySpec(sKeyPhrase.toCharArray(), SALT_BYTES, ITERATION_COUNT);
            SecretKey oKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(oKeySpec);
            oECipher = Cipher.getInstance(oKey.getAlgorithm());
            oDCipher = Cipher.getInstance(oKey.getAlgorithm());

            // Preparar los parametros para los ciphers
            AlgorithmParameterSpec oParamSpec = new PBEParameterSpec(SALT_BYTES, ITERATION_COUNT);

            // Crear los ciphers
            oECipher.init(Cipher.ENCRYPT_MODE, oKey, oParamSpec);
            oDCipher.init(Cipher.DECRYPT_MODE, oKey, oParamSpec);

            // Decodear base64 y obtener bytes
            byte[] oDec = new sun.misc.BASE64Decoder().decodeBuffer(sStringValue);

            // Desencriptar
            byte[] oUTF8 = oDCipher.doFinal(oDec);

            // Decodear usando UTF-8
            return new String(oUTF8, "UTF8");
        } catch (Exception oE1) {
            oE1.printStackTrace();
        }
        return null;
    }

    public static String getCadenaAlfanumAleatoria(int longitud) {
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < longitud) {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                cadenaAleatoria += c;
                i++;
            }
        }
        return cadenaAleatoria;
    }

    public static String obtenerMACADDRESSServer() throws Exception {
        String macAddress = null;
        String command = "ifconfig";

        String osName = System.getProperty("os.name");
        //System.out.println("Operating System is " + osName);

        if (osName.startsWith("Windows")) {
            command = "ipconfig /all";
        } else if (osName.startsWith("Linux") || osName.startsWith("Mac") || osName.startsWith("HP-UX")
                || osName.startsWith("NeXTStep") || osName.startsWith("Solaris") || osName.startsWith("SunOS")
                || osName.startsWith("FreeBSD") || osName.startsWith("NetBSD")) {
            command = "ifconfig -a";
        } else if (osName.startsWith("OpenBSD")) {
            command = "netstat -in";
        } else if (osName.startsWith("IRIX") || osName.startsWith("AIX") || osName.startsWith("Tru64")) {
            command = "netstat -ia";
        } else if (osName.startsWith("Caldera") || osName.startsWith("UnixWare") || osName.startsWith("OpenUNIX")) {
            command = "ndstat";
        } else {// Note: Unsupported system.
            throw new Exception("The current operating system '" + osName + "' is not supported.");
        }

        Process pid = Runtime.getRuntime().exec(command);
        BufferedReader in = new BufferedReader(new InputStreamReader(pid.getInputStream()));
        Pattern p = Pattern.compile("([\\w]{1,2}(-|:)){5}[\\w]{1,2}");
        while (true) {
            String line = in.readLine();
            //System.out.println("line " + line);
            if (line == null) {
                break;
            }

            Matcher m = p.matcher(line);
            if (m.find()) {
                macAddress = m.group();
                break;
            }
        }
        in.close();
        return macAddress;
    }
}
