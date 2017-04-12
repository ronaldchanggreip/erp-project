package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import net.sf.jasperreports.engine.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by esvr on 22/03/17.
 */
@Service("geComunService")
public class GeComunServiceImpl implements GeComunService {

    public static Logger log = Logger.getLogger(GeComunServiceImpl.class);

    @Autowired
    private Environment env;

    public Connection obtenerConexionDataBase(){
        Connection cnx = null;
        try{
            Class.forName(env.getProperty("jdbc.driverClassName"));
            cnx = DriverManager.getConnection(env.getProperty("jdbc.url"), env.getProperty("jdbc.user"), env.getProperty("jdbc.pass"));
        }catch (Exception e) {
            log.error(e);
        }
        return cnx;
    }

    @Override
    @ExceptionServiceAnnotation
    public String viewReportePdf(HashMap hmParametros, String reporte, String nombreArchivo) throws FileNotFoundException, JRException, IOException, SQLException {
        String pathRetorno = "";
        InputStream ie = null;
        Connection c = null;
        try {
            File file = new File(env.getProperty("path.application"));
            String pathExport = env.getProperty("path.application") + "exports" + File.separator;
            ie = new FileInputStream(new File(file.getCanonicalPath(), "jaspers" + file.separator + reporte + ".jasper"));
            c = this.obtenerConexionDataBase();
            TimeZone timeZone = TimeZone.getTimeZone("America/Lima");
            //System.out.println("CONEXION : " + c);
            if (c != null) {
                hmParametros.put("P_DIRECTORIO_PRINCIPAL", file.getCanonicalPath() + file.separator + "generales" + file.separator);
                hmParametros.put("P_LOGO", "LogoCompleto.png");
                hmParametros.put(JRParameter.REPORT_LOCALE, new Locale("es_PE"));
                hmParametros.put(JRParameter.REPORT_TIME_ZONE, timeZone);
                JasperPrint jasperPrint = JasperFillManager.fillReport(ie, hmParametros, c);
                //System.out.println("el jasperProcesado: " + jasperPrint.toString());
                byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
                Path path = Paths.get(pathExport + nombreArchivo);
                Files.write(path, bytes);
                pathRetorno = pathExport + nombreArchivo;
            } else {
                System.out.println("NO SE PUEDO EJECUTAR LA IMPRESION LA CONEXION ES NULA");
            }
        } catch (JRException ex) {
            log.error("Error al rellenar el reporte " + ex.getMessage());
        } catch (IOException ex) {
            log.error("Error al leer el jasper " + ex.getMessage());
        } catch (Throwable ex) {
            log.error("Otros errores con el jasper " + ex.getMessage());
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
                if (ie != null) {
                    ie.close();
                }
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        return pathRetorno;
    }
}
