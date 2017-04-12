/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greip.core.util;

import java.io.*;
import java.security.MessageDigest;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Administrador
 */
public class UtlFile {

    public static String obtenerExtensionArchivo(File file) {
        String nombreConExtension = file.getName();
        int tamanoCompleto = nombreConExtension.length();
        return "";
    }

    public static void renombrarArchivo(File archivoInicial, String nombreFinal) {
        File newFile = new File(archivoInicial.getParent() + nombreFinal);
        archivoInicial.renameTo(newFile);
    }

    public static void renombrarDirectorio(File directorioInicial, String nombreFinal) {

        File newDirectorio = new File(directorioInicial.getParent() + nombreFinal);
        directorioInicial.renameTo(newDirectorio);
    }

    public static File convertirStringToFile(String cadena) {
        File f = null;

        return f;
    }

    public static String convertirFileToString(File archivo) {
        String f = null;
        return f;
    }

    public static void comprimirArchivo(String archivoDescomprimido) {
        try {
            File f = new File(archivoDescomprimido);
            ZipOutputStream os = new ZipOutputStream(new FileOutputStream(f.getParent() + f.getName()
                    + ".zip"));
            os.setLevel(Deflater.DEFAULT_COMPRESSION);
            os.setMethod(Deflater.DEFLATED);

            ZipEntry entrada = new ZipEntry(new File(archivoDescomprimido).getName());
            os.putNextEntry(entrada);

            FileInputStream fis = new FileInputStream(archivoDescomprimido);
            byte[] buffer = new byte[1024];
            int leido = 0;
            while (0 < (leido = fis.read(buffer))) {
                os.write(buffer, 0, leido);
            }

            fis.close();
            os.closeEntry();

            os.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String obtenerUltimoNivel(String rutaInicial) {
        File f = new File(rutaInicial);
        File fN = new File(rutaInicial + "\\" + f.list()[0]);
        String salida = "";

        if (fN.isDirectory()) {
            salida = obtenerUltimoNivel(fN.getAbsolutePath());
        } else {
            return fN.getParent();
        }
        return salida;

    }

    public static String[] archivosAComprimir() {
        File f = new File("D:\\test\\");
        String[] sL = new String[f.list().length];
        for (int i = 0; i < f.list().length; i++) {
            sL[i] = "D:/test/" + f.list()[i];

        }
        comprimirArchivo("D:\\test\\tres.zip", sL);
        return f.list();

    }

    public static void comprimirArchivo(String salidaComprimido, String... archivosDescomprimidos) {
        try {
            File f = new File(salidaComprimido);
            ZipOutputStream os = new ZipOutputStream(new FileOutputStream(f.getParent() + f.getName()));
            os.setLevel(Deflater.DEFAULT_COMPRESSION);
            os.setMethod(Deflater.DEFLATED);

            for (String arc : archivosDescomprimidos) {
                ZipEntry entrada = new ZipEntry(new File(arc).getName());
                os.putNextEntry(entrada);

                FileInputStream fis = new FileInputStream(arc);
                byte[] buffer = new byte[1024];
                int leido = 0;
                while (0 < (leido = fis.read(buffer))) {
                    os.write(buffer, 0, leido);
                }

                fis.close();
                os.closeEntry();
            }
            os.close();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    public static void descomprimirArchivo(String archivoComprimido) {
        try {

            ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoComprimido));
            ZipEntry entrada;
            while (null != (entrada = zis.getNextEntry())) {
                FileOutputStream fos = new FileOutputStream(new File(archivoComprimido).getParent() + entrada.getName());
                int leido;
                byte[] buffer = new byte[1024];
                while (0 < (leido = zis.read(buffer))) {
                    fos.write(buffer, 0, leido);
                }
                fos.close();
                zis.closeEntry();
            }
            zis.close();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    /**
     * @param archivoDestino Archivo donde copiar el Archivo Origen
     * @param archivoOrigen  Archivo a copiar
     * @throws IOException
     */
    public static void copiarArchivoHD(File archivoDestino, File archivoOrigen) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(archivoDestino);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            FileInputStream isr = new FileInputStream(archivoOrigen);
            BufferedInputStream bis = new BufferedInputStream(isr);
            int i;
            while ((i = bis.read()) != -1) {
                bos.write(i);
            }
            isr.close();
            bis.close();
            bos.close();
            bos.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void copiarArchivoHD(File archivoDestino, byte[] archivo) throws FileNotFoundException, IOException {
        FileOutputStream fos;
        BufferedOutputStream bos;

        fos = new FileOutputStream(archivoDestino);
        bos = new BufferedOutputStream(fos);
        fos.write(archivo);
        bos.close();
        bos.flush();

    }

    static public void comprimirCarpeta(String carpetaComprimir, String zipSalida) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        fileWriter = new FileOutputStream(zipSalida);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip("", carpetaComprimir, zip);
        zip.flush();
        zip.close();
    }

    static private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
            throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }

    static public String generarCheckSumSha1(String filepath) {
        String checksum = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

            FileInputStream fileInput = new FileInputStream(filepath);
            byte[] dataBytes = new byte[1024];

            int bytesRead = 0;

            while ((bytesRead = fileInput.read(dataBytes)) != -1) {
                messageDigest.update(dataBytes, 0, bytesRead);
            }

            byte[] digestBytes = messageDigest.digest();

            StringBuffer sb = new StringBuffer("");

            for (int i = 0; i < digestBytes.length; i++) {
                sb.append(Integer.toString((digestBytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            //System.out.println("Checksum for the File: " + sb.toString());
            checksum = sb.toString();

            fileInput.close();
        } catch (Exception ex) {

        }
        return checksum;
    }

    /**
     *  Lee un archivo plano y carga las lineas en un Stringbuffer
      * @param file nombre del archivo con la ruta
     * @return Stringbuffer
     */
    static public StringBuffer leerArchivo(String file){
        StringBuffer st = new StringBuffer();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File (file);
            //System.out.println(archivo.isFile());
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null)
                st.append(linea);
                //System.out.println(linea);
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }

        //System.out.println(st);
        return st;
    }
}
