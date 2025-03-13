package com.simulacion.banco.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class ExportUtil {

    private ExportUtil() {
    }

    public static void createExcel(List<List<String>> columns, List<String> titleColums, OutputStream out) {

        Workbook wb = new HSSFWorkbook();

        // Crear hoja
        Sheet hoja = wb.createSheet("TITULO DE LA HOJA");

        // Crear fila 1
        Row filaTitulo = hoja.createRow(0);
        Cell celda = filaTitulo.createCell(0);

        // Agrega estilos
        CellStyle estilo = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        estilo.setFont(font);

        filaTitulo.getCell(0).setCellStyle(estilo);

        celda.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
        celda.getCellStyle().setVerticalAlignment(VerticalAlignment.CENTER);

        estilo.setAlignment(HorizontalAlignment.LEFT);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);

        // Crear 2 fila
        Row filaData = hoja.createRow(0);

        // Llenar columnas cabeceras en fila 2
        for(int i=0; i<titleColums.size() ;i++)
        {
            celda = filaData.createCell(i);
            celda.setCellValue(titleColums.get(i));
            celda.setCellStyle(estilo);
        }

        // Continúa en la fila 1, para registrar los datos de la lista
        int numFila = 1;
        int posCelda = 0;

        // Llenar filas cuerpo
        for (List<String> titleColum : columns)
        {
            filaData = hoja.createRow(numFila);
            for(var valor : titleColum)
            {
                filaData.createCell(posCelda).setCellValue(String.valueOf(valor));
                posCelda++;
            }
            posCelda = 0;
            numFila++;
        }

        //AutoSize Columnas
        for(int i=0; i< titleColums.size() ;i++)
        {
            hoja.setColumnWidth(i, 5000);
        }

        try
        {
            wb.write(out);
            wb.close();
            out.close();
        }
        catch (IOException e)
        {
            log.error("Error generando Exel");
        }
    }
}
