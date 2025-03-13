package com.simulacion.banco.enums;

import java.util.ArrayList;
import java.util.List;

public enum ColumNameMiddleMarginEnum {

    FECHA("Fecha"),
    MEDIAMOBILE("Media Movíl"),
    COST_PRODUCTION("Costo de Producción"),
    MIDDLE_MARGIN("Margen Medio"),
    UPPER_LIMIT("Limite superior"),
    ALERT("Alerta del Dia");

    final String columnName;

    ColumNameMiddleMarginEnum(String columnName) {
        this.columnName = columnName;
    }

    public static List<String> getColumsName () {

        List<String> listNames = new ArrayList<>();

        for (ColumNameMiddleMarginEnum type : values()) {
            listNames.add(type.columnName);
        }
        return listNames;
    }
}
