package com.HMS.Service;


import com.HMS.Entity.Property;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFService {
    public void generateBookingPdf(String filePath, Property property){
       try {
           Document document = new Document();
           PdfWriter.getInstance(document, new FileOutputStream(filePath));

           document.open();
           PdfPTable table = new PdfPTable(2);
           table.addCell("id");
           table.addCell(property.getId().toString());
           table.addCell("name");
           table.addCell(property.getName().toString());
           table.addCell("no of guests");
           table.addCell(property.getNo_of_guest().toString());
           document.add(table);
           document.close();
       }catch (Exception e){
           e.printStackTrace();
       }
    }

}
