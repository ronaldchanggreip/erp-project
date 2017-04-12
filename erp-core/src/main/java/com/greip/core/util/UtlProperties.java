/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.greip.core.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author mdp_changr
 */
public class UtlProperties {
    Logger LOG = Logger.getLogger(UtlProperties.class);

    public UtlProperties(String file) {
        InputStream in = null;
        try {
            File f = new File("C:/log4j.properties");
            in = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            LOG.fatal(ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                LOG.fatal(ex);
            }
        }
    }
}
