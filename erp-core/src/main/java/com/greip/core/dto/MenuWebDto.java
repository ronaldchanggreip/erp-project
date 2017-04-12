package com.greip.core.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by esvr on 07/03/17.
 */
public class MenuWebDto implements Serializable {

    private String label;
    private String icon;
    private List<String> routerLink;
    private List<MenuWebDto> items;


    public MenuWebDto(){

    }

    public MenuWebDto(String label, String icon, List<String> routerLink){
        this.label = label;
        this.icon = icon;
        this.routerLink = routerLink;
    }

    public MenuWebDto(String label, String icon){
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getRouterLink() {
        return routerLink;
    }

    public void setRouterLink(List<String> routerLink) {
        this.routerLink = routerLink;
    }

    public List<MenuWebDto> getItems() {
        return items;
    }

    public void setItems(List<MenuWebDto> items) {
        this.items = items;
    }
}
