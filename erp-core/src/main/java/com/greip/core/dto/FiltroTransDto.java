package com.greip.core.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HvivesO on 05/01/2017.
 */
public class FiltroTransDto implements Serializable{

    private String condicion;
    private List<Object> params;
    private String order;

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
