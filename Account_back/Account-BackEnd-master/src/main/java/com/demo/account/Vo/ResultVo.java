package com.demo.account.Vo;

import lombok.Data;

import java.util.List;

@Data
public class ResultVo {
    private String kindDescription;
    private Integer rotatedImageWidth;
    private String kind;
    private List<ItemList> itemLists;
    private Integer imageAngle;
    private Integer rotatedImageHeight;
    private String type;
    private String classes;
    private String typeDescription;

    public String getKindDescription() {
        return kindDescription;
    }

    public void setKindDescription(String kindDescription) {
        this.kindDescription = kindDescription;
    }

    public Integer getRotatedImageWidth() {
        return rotatedImageWidth;
    }

    public void setRotatedImageWidth(Integer rotatedImageWidth) {
        this.rotatedImageWidth = rotatedImageWidth;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<ItemList> getItemLists() {
        return itemLists;
    }

    public void setItemLists(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    public Integer getImageAngle() {
        return imageAngle;
    }

    public void setImageAngle(Integer imageAngle) {
        this.imageAngle = imageAngle;
    }

    public Integer getRotatedImageHeight() {
        return rotatedImageHeight;
    }

    public void setRotatedImageHeight(Integer rotatedImageHeight) {
        this.rotatedImageHeight = rotatedImageHeight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}
