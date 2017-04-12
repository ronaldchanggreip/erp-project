package com.greip.core.dto;

import java.util.List;

/**
 * Created by Administrador on 17/02/2017.
 */
public class TreeWebDto {

    private String label;
    private String data;
    private String icon;
    private String expandedIcon;
    private String collapsedIcon;
    private List<TreeWebDto> children;

    public TreeWebDto(){

    }

    public TreeWebDto(String label, String data, String expandedIcon, String collapsedIcon){
        this.label = label;
        this.data = data;
        this.expandedIcon = expandedIcon;
        this.collapsedIcon =  collapsedIcon;
    }

    public TreeWebDto(String label, String icon, String data){
        this.label = label;
        this.icon = icon;
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExpandedIcon() {
        return expandedIcon;
    }

    public void setExpandedIcon(String expandedIcon) {
        this.expandedIcon = expandedIcon;
    }

    public String getCollapsedIcon() {
        return collapsedIcon;
    }

    public void setCollapsedIcon(String collapsedIcon) {
        this.collapsedIcon = collapsedIcon;
    }

    public List<TreeWebDto> getChildren() {
        return children;
    }

    public void setChildren(List<TreeWebDto> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
