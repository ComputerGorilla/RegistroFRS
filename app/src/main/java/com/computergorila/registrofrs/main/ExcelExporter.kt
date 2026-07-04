package com.computergorila.registrofrs.main

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.computergorila.registrofrs.data.local.IncidenciaEntity
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

object ExcelExporter {

    // MAPA DE FILAS (Mantiene la posición física de la cuadrícula de tu imagen)
    private val filaParadas = mapOf(
        "P20" to 3, "P21" to 4, "P22" to 5, "P02" to 6, "P31" to 7,
        "P32" to 8, "P33" to 9, "P34" to 10, "P04" to 11, "P05" to 12,
        "P06" to 13, "P07" to 14, "P08" to 15, "P09" to 16, "P10" to 17,
        "P29" to 18, "P30" to 19, "P11" to 20, "P23" to 21, "P24" to 22,
        "P25" to 23, "P26" to 24, "P27" to 25, "P28" to 26, "P13" to 27,
        "P14" to 28, "P16" to 29, "P17" to 30, "P19" to 31
    )

    fun exportarMes(context: Context, mesNombre: String, incidencias: List<IncidenciaEntity>) {
        try {
            // 1. Leer la plantilla (CORREGIDO: Nombre exacto de tu archivo en assets)
            val inputStream = context.assets.open("Modelo_registro.xlsx")
            val workbook = XSSFWorkbook(inputStream)
            val sheet = workbook.getSheetAt(0)

            // 2. Escribir el nombre del Mes en B1 (Fila 0, Celda 1)
            val filaMes = sheet.getRow(0) ?: sheet.createRow(0)
            val celdaMes = filaMes.getCell(1) ?: filaMes.createCell(1)
            celdaMes.setCellValue(mesNombre.uppercase())

            // 3. Inyectar las incidencias
            incidencias.forEach { item ->
                val filaIndex = filaParadas[item.paradaId]
                if (filaIndex != null) {
                    val row = sheet.getRow(filaIndex) ?: sheet.createRow(filaIndex)

                    // El día 1 se escribe en la Columna C (índice 2). 1 + 1 = 2. Es correcto.
                    val colIndex = item.dia + 1

                    val cell = row.getCell(colIndex) ?: row.createCell(colIndex)
                    cell.setCellValue(item.numero.toDouble())
                }
            }

            val nombreArchivo = "Registro_Trambesos_${mesNombre.uppercase()}.xlsx"
            val rutaDescargas = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(rutaDescargas, nombreArchivo)

            val outputStream = FileOutputStream(file)
            workbook.write(outputStream)

            outputStream.close()
            workbook.close()

            Toast.makeText(context, "¡Excel guardado en Descargas!", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al exportar: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}