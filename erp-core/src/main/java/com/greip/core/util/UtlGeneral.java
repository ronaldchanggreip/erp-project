/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greip.core.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Administrador
 */
public class UtlGeneral {

    public static void print(Object object) {
        System.out.println(object);
    }

    public static void toUpperCaseDto(Object object) {
        Class cls = object.getClass();

        Field[] atributos = cls.getDeclaredFields();

        for (Field f : atributos) {

            if (f.getType().toString().equals("class java.lang.String")) {
                Method metodoGet = null;
                Method metodoSet = null;

                try {
                    Class a[] = new Class[1];

                    metodoGet = cls.getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), null);
                    metodoSet = cls.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), f.getType());

                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    Object n[] = new Object[1];
                    try {
                        n[0] = metodoGet.invoke(object, null);
                        if (n[0].toString() != null) {
                            n[0] = n[0].toString().toUpperCase();
                        }
                    } catch (Exception e) {
                        n[0] = null;
                    }
                    metodoSet.invoke(object, n);

                } catch (Exception e) {
                }
            }

        }

    }

    public static void redondearNumerosDto(Object object, int precision) {
        Class cls = object.getClass();

        Field[] atributos = cls.getDeclaredFields();

        for (Field f : atributos) {
            //System.out.println(f.getType());
            if (f.getType().toString().equals("class java.math.BigDecimal")) {
                //System.out.println(f.getName() + " " + f.getType());
                Method metodoGet = null;
                Method metodoSet = null;

                try {
                    Class a[] = new Class[1];

                    metodoGet = cls.getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), null);
                    metodoSet = cls.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), f.getType());

                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    Object n[] = new Object[1];
                    try {
                        n[0] = metodoGet.invoke(object, null);
                        if (n[0] != null) {
                            BigDecimal bd = (BigDecimal) n[0];
                            n[0] = UtlNumber.dividir(bd, BigDecimal.ONE, precision);
                        }
                    } catch (Exception e) {
                        n[0] = null;
                    }
                    metodoSet.invoke(object, n);

                } catch (Exception e) {
                }
            }

        }

    }

    public static void ordena(List lista, final String propiedad) {
        Collections.sort(lista, new Comparator() {
            public int compare(Object obj1, Object obj2) {

                Class clase = obj1.getClass();
                String getter = "get" + Character.toUpperCase(propiedad.charAt(0)) + propiedad.substring(1);
                try {
                    Method getPropiedad = clase.getMethod(getter);

                    Object propiedad1 = getPropiedad.invoke(obj1).toString();
                    Object propiedad2 = getPropiedad.invoke(obj2).toString();

                    if (propiedad1 instanceof Comparable && propiedad2 instanceof Comparable) {
                        Comparable prop1 = (Comparable) propiedad1;
                        Comparable prop2 = (Comparable) propiedad2;
                        return prop1.compareTo(prop2);
                    }//CASO DE QUE NO SEA COMPARABLE  
                    else {
                        if (propiedad1.equals(propiedad2)) {
                            return 0;
                        } else {
                            return 1;
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    public static String obtenerSeparadorDirectorioOS() {
        String separador = File.separator;
        return separador;
    }

    public static String obtenerRutaJavaHome() {
        String ruta = null;
        ruta = System.getProperty("java.home");
        return ruta;
    }

    public static String obtenerSistemaOperativo() {
        String plataforma = "";
        String tmp = System.getProperty("os.name").toUpperCase();

        if (tmp.indexOf("WINDOWS") != -1) {
            plataforma = "WINDOWS";
        } else if (tmp.indexOf("LINUX") != -1) {
            plataforma = "LINUX";
        } else {
            plataforma = "OTHERS";
        }

        return plataforma;
    }

    public static boolean in(Object valor, Object... in) {
        boolean retorno = false;
        for (Object o : in) {
            if (o.equals(valor)) {
                retorno = true;
            }
        }
        return retorno;
    }

    public static String obtenerVersionJava() {
        String plataforma = "";

        plataforma = System.getProperty("java.version");

        return plataforma;
    }
}
