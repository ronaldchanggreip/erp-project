/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.greip.core.util;

/**
 * @author mdp_changr
 */
public class UtlDataBase {

    public static String MOTOR_DB = "MYSQL";
    public static String MOTOR_DB_NAME = "";
    public static String MOTOR_SERVER = "";
    public static String MOTOR_PORT = "";
    public static String MOTOR_USER_NAME = "";
    public static String MOTOR_PASSWORD = "";


    public static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static String MYSQL_URL_INI = "jdbc:mysql://";//localhost:3306/usersdb";


    public static String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static String ORACLE_URL_INI = "jdbc:oracle:thin:@";//127.0.0.1:1521:XE";


    public static String POSTGRES_DRIVER = "org.postgresql.Driver";
    public static String POSTGRES_URL_INI = "jdbc:postgresql:";//localhost:5432/dbGreipCore";


}
