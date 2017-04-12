package com.greip.core.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HvivesO on 05/01/2017.
 */
public class FiltroDto implements Serializable{

    private boolean order = false;
    private boolean fplantilla = false;
    private String plantilla;
    private List<FiltroOrderDto> orders;
    private List<FiltroDetaDto> filtros;
    private boolean limit = false;
    private int first = 0;
    private int max = 0;

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public List<FiltroOrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<FiltroOrderDto> orders) {
        this.orders = orders;
    }

    public List<FiltroDetaDto> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<FiltroDetaDto> filtros) {
        this.filtros = filtros;
    }

    public boolean isFplantilla() {
        return fplantilla;
    }

    public void setFplantilla(boolean fplantilla) {
        this.fplantilla = fplantilla;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public boolean isLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
