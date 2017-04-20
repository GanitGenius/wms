/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printInv;

/**
 *
 * @author GanitGenius
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import controller.application.sell.NewSellController;
import dataBase.DBConnection;
import dataBase.DBProperties;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class GenerateInvoice {

    private BaseFont bfBold;
    private BaseFont bf;
    private int pageNumber = 0;
    public static int n;
    public static String sellId, custName, total;
    public static String[] s1, s2, s3, s4;

    public static void invoice() throws PrinterException {

        System.out.println("indexs ====" + n);

        String pdfFilename = "Invoice.pdf";
        GenerateInvoice generateInvoice = new GenerateInvoice();

        generateInvoice.createPDF(pdfFilename);

        PrinterJob job = PrinterJob.getPrinterJob();
        try {
            PDDocument doc = PDDocument.load(new File("Invoice.pdf"));
            job.setPageable(new PDFPageable(doc));
            if (job.printDialog()) {
                job.print();
            }
        } catch (Exception ex) {
            Logger.getLogger(GenerateInvoice.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createPDF(String pdfFilename) {

        Document doc = new Document();
        PdfWriter docWriter = null;
        initializeFonts();

        try {
//   String path = "docs/" + pdfFilename;
            String path = pdfFilename;
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.addAuthor("SmartWMS");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("SmartWMS");
            doc.addTitle("Invoice");
            doc.setPageSize(PageSize.LETTER);

            doc.open();
            PdfContentByte cb = docWriter.getDirectContent();

            boolean beginPage = true;
            int y = 0;
            System.out.println("n ===========" + n);
            for (int i = 0; i < n; i++) {
                if (beginPage) {
                    beginPage = false;
                    generateLayout(doc, cb);
                    generateHeader(doc, cb);
                    y = 615;
                }
                generateDetail(doc, cb, i, y);
                y = y - 15;
                if (y < 50) {
                    printPageNumber(cb);
                    doc.newPage();
                    beginPage = true;
                }
            }
            printPageNumber(cb);
            cb.beginText();
            cb.setFontAndSize(bfBold, 10);
            cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Grand Total : " + total, 570, 35, 0);
            cb.endText();

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
    }

    public void generateLayout(Document doc, PdfContentByte cb) {

        try {

            cb.setLineWidth(1f);

            // Invoice Header box layout
            cb.rectangle(420, 700, 150, 60);
            cb.moveTo(420, 720);
            cb.lineTo(570, 720);
            cb.moveTo(420, 740);
            cb.lineTo(570, 740);
            cb.moveTo(480, 700);
            cb.lineTo(480, 760);
            cb.stroke();

            // Invoice Header box Text Headings 
            createHeadings(cb, 422, 743, "Invoice No.");
            createHeadings(cb, 422, 723, "Name");
            createHeadings(cb, 422, 703, "Invoice Date");

            // Invoice Detail box layout 
            cb.rectangle(20, 50, 550, 600);
            cb.moveTo(20, 630);
            cb.lineTo(570, 630);
            cb.moveTo(50, 50);
            cb.lineTo(50, 650);
            cb.moveTo(360, 50);
            cb.lineTo(360, 650);
            cb.moveTo(430, 50);
            cb.lineTo(430, 650);
            cb.moveTo(500, 50);
            cb.lineTo(500, 650);
            cb.stroke();

            // Invoice Detail box Text Headings 
            createHeadings(cb, 22, 633, "SNo.");
            createHeadings(cb, 52, 633, "Product ID");
            createHeadings(cb, 362, 633, "Quantity");
            createHeadings(cb, 432, 633, "Unit Price");
            createHeadings(cb, 502, 633, "Line Total");

            //add the images
            Image companyLogo = Image.getInstance("src/image/icon.png");
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(25);
            doc.add(companyLogo);
        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void generateHeader(Document doc, PdfContentByte cb) {


        DBConnection dbCon = new DBConnection();
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        DBProperties dBProperties = new DBProperties();
        String db = dBProperties.loadPropertiesFile();

        con = dbCon.geConnection();
        String orgName = "";
        String orgAdd = "";
        String orgCont = "";
        try {
            pst = con.prepareStatement("select * from " + db + ".Organize where Id=1");
            rs = pst.executeQuery();
            while (rs.next()) {
                orgName = rs.getString(2);
                orgAdd = rs.getString(8);
                orgCont = rs.getString(4);
                System.out.println(orgName + "\t" + orgAdd + "\t" + orgCont);

            }
            createHeadings(cb, 200, 750, orgName);
            int tmp = 735;
            StringTokenizer st = new StringTokenizer(orgAdd, ",");
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                System.out.println(s);
                createHeadings(cb, 200, tmp, s);
                tmp -= 15;
            }
            createHeadings(cb, 200, tmp, orgCont);
        } catch (SQLException ex) {
            Logger.getLogger(NewSellController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        System.out.println(sellId + " " + "CustNsme" + " " + dtf.format(localDate));
        createHeadings(cb, 482, 743, sellId);
        createHeadings(cb, 482, 723, custName);
        createHeadings(cb, 482, 703, dtf.format(localDate));

    }

    public void generateDetail(Document doc, PdfContentByte cb, int index, int y) {
        DecimalFormat df = new DecimalFormat("0.00");

        try {


            createContent(cb, 48, y, String.valueOf(index + 1), PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 52, y, s1[index], PdfContentByte.ALIGN_LEFT);
            createContent(cb, 428, y, s2[index], PdfContentByte.ALIGN_RIGHT);

            createContent(cb, 498, y, s3[index], PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 568, y, s4[index], PdfContentByte.ALIGN_RIGHT);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text) {

        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x, y);
//        System.out.println(text+"*****************");
        cb.showText(text.trim());
//        System.out.println("***************************");
        cb.endText();

    }

    public void printPageNumber(PdfContentByte cb) {

        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber + 1), 570, 20, 0);
        cb.endText();

        pageNumber++;

    }

    private void createContent(PdfContentByte cb, float x, float y, String text, int align) {

        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned(align, text.trim(), x, y, 0);
        cb.endText();

    }

    public void initializeFonts() {

        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setN(int x) {
        n = x;
        System.out.println("n ====" + n);
        s1 = new String[n];
        s2 = new String[n];
        s3 = new String[n];
        s4 = new String[n];
    }

    public void setSellId(String x) {
        sellId = x;
    }

    public void setCustName(String x) {
        custName = x;
    }
    
    public void setTotal(String x) {
        total = x;
    }

    public void sets1(String s, int i) {
        s1[i] = s;
    }

    public void sets2(String s, int i) {
        s2[i] = s;
    }

    public void sets3(String s, int i) {
        s3[i] = s;
    }

    public void sets4(String s, int i) {
        s4[i] = s;
    }

}
