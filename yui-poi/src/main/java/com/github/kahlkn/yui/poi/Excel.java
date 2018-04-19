package com.github.kahlkn.yui.poi;

import com.github.kahlkn.artoria.converter.ConvertUtils;
import com.github.kahlkn.artoria.exception.ExceptionUtils;
import com.github.kahlkn.artoria.io.IOUtils;
import com.github.kahlkn.artoria.reflect.ReflectUtils;
import com.github.kahlkn.artoria.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.kahlkn.artoria.util.Const.GET_OR_SET_LENGTH;
import static com.github.kahlkn.artoria.util.Const.SET;
import static org.apache.poi.ss.usermodel.Cell.*;

/**
 * Excel tools and excel object.
 * @author Kahle
 */
public class Excel {

    public static Excel create(String excelType) {
        Assert.notBlank(excelType, "Parameter \"excelType\" must not blank. ");
        boolean isXls = Excel.XLS.equalsIgnoreCase(excelType);
        return new Excel(isXls ? new HSSFWorkbook() : new XSSFWorkbook());
    }

    public static Excel create(File file) throws IOException {
        return Excel.create(file, null);
    }

    public static Excel create(File file, String extIfBlank) throws IOException {
        Assert.notNull(file, "Parameter \"file\" must not null. ");
        Assert.state(file.exists(), "Parameter \"file\" must exist. ");
        String filePath = file.toString();
        String ext = PathUtils.getExtension(filePath);
        // If ext is blank, try get parameter extIfBlank.
        if (StringUtils.isBlank(ext)) {
            if (StringUtils.isBlank(extIfBlank)) {
                throw new PoiException("Extension name in file \""
                        + filePath + "\" is blank, and parameter \"extIfBlank\" is blank. ");
            }
            else {
                ext = extIfBlank;
            }
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            return Excel.create(in, ext);
        }
        finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static Excel create(InputStream in, String excelType) throws IOException {
        Assert.notNull(in, "Parameter \"in\" must not null. ");
        Assert.notBlank(excelType, "Parameter \"excelType\" must not blank. ");
        Excel excel;
        if (Excel.XLS.equalsIgnoreCase(excelType)) {
            excel = new Excel(new HSSFWorkbook(in));
        }
        else if (Excel.XLSX.equalsIgnoreCase(excelType)) {
            excel = new Excel(new XSSFWorkbook(in));
        }
        else {
            throw new PoiException("Unsupported excel type \"" + excelType + "\". ");
        }
        if (excel.getNumberOfSheets() > 0) {
            excel.selectSheet(0);
        }
        return excel;
    }

    public static <T> Excel create(List<T> beans, Map<String, String> ppTtlMap) {
        return Excel.create(null, Excel.XLSX, beans, ppTtlMap, 0, 0);
    }

    public static <T> Excel create(Excel template, String excelType, List<T> beans, Map<String, String> ppTtlMap) {
        return Excel.create(template, excelType, beans, ppTtlMap, 0, 0);
    }

    /**
     *
     * @param template
     * @param excelType
     * @param beans
     * @param ppTtlMap Property title mapping
     * @param rowStart Begin 0
     * @param columnStart Begin 0
     * @param <T>
     * @return
     */
    public static <T> Excel create(Excel template, String excelType
            , List<T> beans, Map<String, String> ppTtlMap, int rowStart, int columnStart) {
        try {
            Assert.notBlank(excelType, "Parameter \"excelType\" must not blank. ");
            // Check rowStart and columnStart.
            if (rowStart < 0) { rowStart = 0; }
            if (columnStart < 0) { columnStart = 0; }
            // Check ppTtlMap not empty.
            boolean hasPpTtlMap = false;
            if (MapUtils.isNotEmpty(ppTtlMap)) {
                hasPpTtlMap = true;
            }
            // Check template and if null create default.
            Excel result = template == null
                    ? Excel.create(excelType).createSheet() : template;
            // Check beans has data.
            if (CollectionUtils.isEmpty(beans)) {
                return result;
            }
            T bean = CollectionUtils.takeFirstNotNullElement(beans);
            Assert.notNull(bean, "Elements in \"beans\" all is null. ");
            // Init some.
            List<Method> usefulReadMethods = new ArrayList<Method>();
            List<Object> rowContent = new ArrayList<Object>();
            // Add blank cell.
            for (int z = 0; z < columnStart; z++) { rowContent.add(null); }
            Class<?> clazz = bean.getClass();
            // Find useful read methods.
            Map<String, Method> readMethods = ReflectUtils.findReadMethods(clazz);
            Method[] methods = ReflectUtils.findMethods(clazz);
            for (Method method : methods) {
                String name = method.getName();
                method = readMethods.get(name);
                if (method != null) {
                    name = name.substring(GET_OR_SET_LENGTH);
                    name = StringUtils.uncapitalize(name);
                    name = hasPpTtlMap ? ppTtlMap.get(name) : name;
                    if (StringUtils.isBlank(name)) { continue; }
                    usefulReadMethods.add(method);
                    rowContent.add(name);
                }
            }
            result.createRow(rowStart, rowContent);
            // Fill beans data to excel.
            for (int i = 0, size = beans.size(); i < size; i++) {
                rowContent.clear();
                // Add blank cell.
                for (int z = 0; z < columnStart; z++) { rowContent.add(null); }
                bean = beans.get(i);
                for (Method method : usefulReadMethods) {
                    Object val = method.invoke(bean);
                    rowContent.add(val);
                }
                result.createRow(i + rowStart + 1, rowContent);
            }
            // Return result.
            return result;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";
    private Workbook workbook;
    private Sheet currentSheet;

    Excel(Workbook workbook) {
        Assert.notNull(workbook, "Parameter \"workbook\" must not null. ");
        this.workbook = workbook;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public String getExtension() {
        if (workbook instanceof HSSFWorkbook) {
            return XLS;
        }
        else if (workbook instanceof XSSFWorkbook) {
            return XLSX;
        }
        else {
            throw new PoiException("Unsupported extension. ");
        }
    }

    public Sheet getCurrentSheet() {
        if (currentSheet == null) {
            throw new PoiException("Create or select sheet first.");
        }
        return currentSheet;
    }

    public Integer getLastRowNum() {
        // Get the last row number on the current select sheet.
        // And based 0, contained n.
        return this.getCurrentSheet().getLastRowNum();
    }

    public int getNumberOfSheets() {
        // Like list size, if 3, index is 0, 1, 2.
        return this.getWorkbook().getNumberOfSheets();
    }

    public Excel createSheet() {
        this.currentSheet = workbook.createSheet();
        return this;
    }

    public Excel createSheet(String sheetName) {
        this.currentSheet = workbook.createSheet(sheetName);
        return this;
    }

    public Excel selectSheet(int index) {
        this.currentSheet = workbook.getSheetAt(index);
        return this;
    }

    public Excel selectSheet(String sheetName) {
        this.currentSheet = workbook.getSheet(sheetName);
        return this;
    }

    private Object getCellValue(Cell cell) {
        Object cellValue;
        if (cell == null) {
            return null;
        }
        int cellType = cell.getCellType();
        switch (cellType) {
            case CELL_TYPE_NUMERIC:
            case CELL_TYPE_FORMULA: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue();
                }
                else {
                    cellValue = cell.getNumericCellValue();
                }
                break;
            }
            case CELL_TYPE_BOOLEAN: {
                cellValue = cell.getBooleanCellValue();
                break;
            }
            default: {
                cellValue = cell.getStringCellValue();
            }
        }
        return cellValue;
    }

    private void setCellValue(Cell cell, Object value) {
        if (cell == null || value == null) {
            return;
        }
        if (value instanceof String) {
            cell.setCellValue((String) value);
        }
        else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        }
        else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString) value);
        }
        else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
        else {
            cell.setCellValue(value.toString());
        }
    }

    public List<?> getRow(Integer rowNum) {
        // Row's firstCellNum, lastCellNum can null.
        return this.getRow(rowNum, null, null);
    }

    public List<?> getRow(Integer rowNum, Integer colNum) {
        // Get 0 to colNum cell (not contain colNum).
        return this.getRow(rowNum, 0, colNum);
    }

    public List<?> getRow(Integer rowNum, Integer firstCellNum, Integer lastCellNum) {
        // Get current select sheet a line, rowNum begin 0, contained n.
        // And firstCellNum begin 0, not contain lastCellNum.
        List<Object> rowContent = new ArrayList<Object>();
        Row row = this.getCurrentSheet().getRow(rowNum);
        if (row == null) { return new ArrayList<Object>(); }
        if (firstCellNum == null) { firstCellNum = (int) row.getFirstCellNum(); }
        if (lastCellNum == null) { lastCellNum = (int) row.getLastCellNum(); }
        for (int i = firstCellNum; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            Object val = this.getCellValue(cell);
            rowContent.add(val);
        }
        return rowContent;
    }

    public Excel createRow(Integer rowNum, List<?> rowContent) {
        int len = rowContent.size();
        int lastRowNum = this.getCurrentSheet().getLastRowNum();
        // Can get to lastRowNum.
        Row row = lastRowNum >= rowNum
                ? this.getCurrentSheet().getRow(rowNum)
                : this.getCurrentSheet().createRow(rowNum);
        for (int i = 0; i < len; ++i) {
            int lastCellNum = row.getLastCellNum();
            // Can not get to lastCellNum.
            Cell cell = lastCellNum > i
                    ? row.getCell(i) : row.createCell(i);
            Object val = rowContent.get(i);
            this.setCellValue(cell, val);
        }
        return this;
    }

    public Excel write(OutputStream out) throws IOException {
        Assert.notNull(out, "Parameter \"out\" must not null. ");
        workbook.write(out);
        out.flush();
        return this;
    }

    public void write(File file) throws IOException {
        Assert.notNull(file, "Parameter \"file\" must not null. ");
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            this.write(out);
        }
        finally {
            IOUtils.closeQuietly(out);
        }
    }

    public byte[] write() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.write(out);
        return out.toByteArray();
    }

    public <T> List<T> readToBeans(Class<T> clazz, int rowStart, int columnStart) {
        return this.readToBeans(clazz, null, rowStart, columnStart);
    }

    public <T> List<T> readToBeans(Class<T> clazz, Map<String, String> ttlPpMap) {
        return this.readToBeans(clazz, ttlPpMap, 0, 0);
    }

    /**
     *
     * @param clazz
     * @param ttlPpMap Title property mapping
     * @param rowStart Begin 0
     * @param columnStart Begin 0
     * @param <T>
     * @return
     */
    public <T> List<T> readToBeans(Class<T> clazz, Map<String, String> ttlPpMap, int rowStart, int columnStart) {
        try {
            Assert.notNull(clazz, "Parameter \"clazz\" must not null. ");
            // Check rowStart and columnStart.
            if (rowStart < 0) { rowStart = 0; }
            if (columnStart < 0) { columnStart = 0; }
            // Check ttlPpMap not empty.
            boolean hasTtlPpMap = false;
            if (MapUtils.isNotEmpty(ttlPpMap)) { hasTtlPpMap = true; }
            // Check excel has row or has data.
            List<T> result = new ArrayList<T>();
            Integer lastRowNum = this.getLastRowNum();
            Assert.state(lastRowNum > rowStart, "Excel not has row or not has data. ");
            // Init some.
            Map<String, Method> writeMethods = ReflectUtils.findWriteMethods(clazz);
            List<Method> usefulWriteMethods = new ArrayList<Method>();
            List<?> rowContent;
            // Find bean write methods.
            rowContent = this.getRow(rowStart, columnStart, null);
            for (Object data : rowContent) {
                if (data == null || StringUtils.isBlank(data.toString())) {
                    throw new PoiException("Maybe parameter \"columnStart = " +
                            columnStart + "\" is error, because get table title include blank element. ");
                }
                String name = data.toString();
                if (hasTtlPpMap) {
                    String tmp = ttlPpMap.get(name);
                    name = StringUtils.isNotBlank(tmp) ? tmp : name;
                }
                name = SET + StringUtils.capitalize(name);
                Method method = writeMethods.get(name);
                if (method == null) {
                    throw new PoiException("Method \"" + name + "\" not found in class \"" + clazz.getName() + "\". ");
                }
                usefulWriteMethods.add(method);
            }
            // Fill data to bean.
            for (int i = rowStart + 1; i <= lastRowNum; i++) {
                rowContent.clear();
                rowContent = this.getRow(i, columnStart, null);
                if (CollectionUtils.isEmpty(rowContent)) {
                    continue;
                }
                T bean = clazz.newInstance();
                for (int j = 0, size = rowContent.size(); j < size; j++) {
                    Object val = rowContent.get(j);
                    if (val == null) { continue; }
                    Method method = usefulWriteMethods.get(j);
                    Class<?> valType = method.getParameterTypes()[0];
                    if (!val.getClass().equals(valType)) {
                        val = ConvertUtils.convert(val, valType);
                    }
                    method.invoke(bean, val);
                }
                result.add(bean);
            }
            // Return result.
            return result;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

}
