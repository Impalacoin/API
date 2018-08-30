package com.impalapay.airtel.util.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.impalapay.airtel.beans.transaction.Transaction;
import com.impalapay.airtel.persistence.geolocation.CountryDAO;
import com.impalapay.airtel.persistence.transaction.TransactionStatusDAO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Utility class dealing with the export of all Registered Users activity for
 * mkifafa account.
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Nov 13, 2013
 *
 * @author <a href="mailto:michael@shujaa.co.ke">Michael Wakahe</a>
 * @author <a href="mailto:erickm@shujaa.co.ke">Erick Murimi</a>
 * @version %I%, %G%
 *
 */
public class ExcelUtil {
    // The headings to be contained in the first row of the Excel sheet

	final static String[] TRANSACTION_TITLES = {"Transaction Id","sourceCountrycode","senderName", "recipientMobile", 
		"senderToken", "currencyCode", "recipientcountryUuid", "referenceNumber","transactionStatusUuid","clientTime"};
  
    private static Logger logger = Logger.getLogger(ExcelUtil.class);

    /**
     * Creates a Microsoft Excel Workbook containing Registered Users activity
     * provided in a CSV text file. The format of the created file will be
     * Office Open XML (OOXML).
     * <p>
     * It expects the CSV to have the following columns from left to right:<br/>
     * count, mobile, language, location, Date of Registration
     * <p>
     * This method has been created to allow for large Excel files to be created
     * without overwhelming memory.
     *
     *
     * @param RegisteredUsersCSVFile a valid CSV text file. It should contain
     * the full path and name of the file e.g. "/tmp/export/registeredusers.csv"
     * @param delimiter the delimiter used in the CSV file
     * @param excelFile the Microsoft Excel file to be created. It should
     * contain the full path and name of the file e.g.
     * "/tmp/export/registeredusers.xlsx"
     * @return whether the creation of the Excel file was successful or not
     */
    public static boolean createExcelExport(final String transactionCSVFile, final String delimiter, final String excelFile) {
        boolean success = true;

        int rowCount = 0;	// To keep track of the row that we are on

        Row row;
        Map<String, CellStyle> styles;

        SXSSFWorkbook wb = new SXSSFWorkbook(5000); // keep 5000 rows in memory, exceeding rows will be flushed to disk
        // Each line of the file is approximated to be 200 bytes in size, 
        // therefore 5000 lines are approximately 1 MB in memory
        // wb.setCompressTempFiles(true); // temporary files will be gzipped on disk

        Sheet sheet = wb.createSheet("Registered Users");
        styles = createStyles(wb);

        PrintSetup printSetupTopup = sheet.getPrintSetup();
        printSetupTopup.setLandscape(true);
        sheet.setFitToPage(true);

        // Set up the heading to be seen in the Excel sheet
        row = sheet.createRow(rowCount);

        Cell titleCell;

        row.setHeightInPoints(45);
        titleCell = row.createCell(0);
        titleCell.setCellValue("Registered Users");
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$L$1"));
        titleCell.setCellStyle(styles.get("title"));

        rowCount++;
        row = sheet.createRow(rowCount);
        row.setHeightInPoints(12.75f);

        for (int i = 0; i < TRANSACTION_TITLES.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(TRANSACTION_TITLES[i]);
        }

        rowCount++;

        FileUtils.deleteQuietly(new File(excelFile));
        FileOutputStream out;

        try {
            FileUtils.touch(new File(excelFile));

            // Read the CSV file and populate the Excel sheet with it
            LineIterator lineIter = FileUtils.lineIterator(new File(transactionCSVFile));
            String line;
            String[] lineTokens;
            int size;

            while (lineIter.hasNext()) {
                row = sheet.createRow(rowCount);
                line = lineIter.next();
                lineTokens = StringUtils.split(line, delimiter);
                size = lineTokens.length;

                for (int cellnum = 0; cellnum < size; cellnum++) {
                    Cell cell = row.createCell(cellnum);
                    cell.setCellValue(lineTokens[cellnum]);
                }

                rowCount++;
            }

            out = new FileOutputStream(excelFile);
            wb.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException while trying to create Excel file '" + excelFile
                    + "' from CSV file '" + transactionCSVFile + "'.");
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;

        } catch (IOException e) {
            logger.error("IOException while trying to create Excel file '" + excelFile
                    + "' from CSV file '" + transactionCSVFile + "'.");
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        }

        wb.dispose(); // dispose of temporary files backup of this workbook on disk

        return success;


    }

    /**
     *
     * @param wb
     * @return styles
     */
    public static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>();

        CellStyle style;
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 48);
        titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        return styles;
    }

    /**
     *
     * @param wb
     * @return CellStyle
     */
    public static CellStyle createBorderedStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }

    /**
     * Creates a Microsoft Excel Workbook containing transactions activity provided from a list
     *
     * @param contents a List
     * @param delimiter the delimiter used in the CSV file
     * @param excelFile the Microsoft Excel file to be created
     * @return whether the creation of the Excel file was successful or not
     */
    public static boolean createExcelExport(List<Transaction> transactions, final String delimiter, final String excelFile) {
        TransactionStatusDAO statusDAO = TransactionStatusDAO.getInstance();
        CountryDAO countryDAO = CountryDAO.getInstance();

        boolean success = true;

        int rowCount = 0;	// To keep track of the row that we are on

        Row row;
        Map<String, CellStyle> styles;

        SXSSFWorkbook wb = new SXSSFWorkbook(5000); // keep 5000 rows in memory, exceeding rows will be flushed to disk
        // Each line of the file is approximated to be 200 bytes in size, 
        // therefore 5000 lines are approximately 1 MB in memory
        // wb.setCompressTempFiles(true); // temporary files will be gzipped on disk

        Sheet sheet = wb.createSheet("Transactions");
        styles = createStyles(wb);

        PrintSetup printSetupTopup = sheet.getPrintSetup();
        printSetupTopup.setLandscape(true);
        sheet.setFitToPage(true);

        // Set up the heading to be seen in the Excel sheet
        row = sheet.createRow(rowCount);

        Cell titleCell;

        row.setHeightInPoints(45);
        titleCell = row.createCell(0);
        titleCell.setCellValue("Registered Users");
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$L$1"));
        titleCell.setCellStyle(styles.get("title"));

        rowCount++;
        row = sheet.createRow(rowCount);
        row.setHeightInPoints(12.75f);

        for (int i = 0; i < TRANSACTION_TITLES.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(TRANSACTION_TITLES[i]);
        }

        rowCount++;

        FileUtils.deleteQuietly(new File(excelFile));
        FileOutputStream out;

        try {
            FileUtils.touch(new File(excelFile));

            Cell cell;

            for (Transaction list : transactions) {
                row = sheet.createRow(rowCount);
                
                row.createCell(0).setCellValue(list.getUuid());
                row.createCell(1).setCellValue(list.getSourceCountrycode());
		        row.createCell(2).setCellValue(list.getSenderName());
		        row.createCell(3).setCellValue(list.getSenderName());
		        row.createCell(4).setCellValue(list.getSenderToken());
		        row.createCell(5).setCellValue(list.getCurrencyCode());
		        row.createCell(6).setCellValue(countryDAO.getCountry(list.getRecipientCountryUuid()).getCountrycode());
		        row.createCell(7).setCellValue(list.getReferenceNumber());
		        row.createCell(8).setCellValue( statusDAO.getTransactionStatus(list.getTransactionStatusUuid()).getDescription());
		        row.createCell(9).setCellValue(list.getClientTime().toString());

                rowCount++;
            }

            out = new FileOutputStream(excelFile);
            wb.write(out);
            out.close();

        } catch (IOException e) {
            logger.error("IOException while trying to create Excel file '" + excelFile
                    + "' from list of topups.");
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        }

        wb.dispose(); // dispose of temporary files backup of this workbook on disk

        return success;
    }

    
}

/*
** Local Variables:
**   mode: java
**   c-basic-offset: 2
**   tab-width: 2
**   indent-tabs-mode: nil
** End:
**
** ex: set softtabstop=2 tabstop=2 expandtab:
**
*/
