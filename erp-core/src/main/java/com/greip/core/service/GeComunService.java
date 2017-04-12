package com.greip.core.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by esvr on 22/03/17.
 */
public interface GeComunService {

    public String viewReportePdf(HashMap hmParametros, String reporte, String nombreArchivo) throws FileNotFoundException, JRException, IOException, SQLException;
}
