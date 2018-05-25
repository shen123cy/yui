package com.github.kahlkn.yui.generator;

import com.github.kahlkn.artoria.jdbc.DbUtils;
import com.github.kahlkn.artoria.jdbc.SimpleDataSource;
import com.github.kahlkn.yui.generator.common.ColumnMeta;
import com.github.kahlkn.yui.generator.common.TableMeta;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableInfoGetTest {
    private static DbUtils dbUtils = new DbUtils(new SimpleDataSource());

    @Test
    public void test1() throws SQLException {
        Connection conn = dbUtils.getConnection();
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        String schemaPattern = null;
        String tableNamePattern = "t_cms_topic";
        ResultSet tablesRs = databaseMetaData.getTables(null, schemaPattern, tableNamePattern, new String[]{"TABLE"});

        List<TableMeta> tableMetas = new ArrayList<TableMeta>();
        while (tablesRs.next()) {
            TableMeta tableMeta = new TableMeta();
            // TABLE_CAT : test_db_information | TABLE_SCHEM : null | TABLE_NAME : t_cms_topic | TABLE_TYPE : TABLE | REMARKS :  | TYPE_CAT : null | TYPE_SCHEM : null | TYPE_NAME : null | SELF_REFERENCING_COL_NAME : null | REF_GENERATION : null
            tableMeta.setName(tablesRs.getString("TABLE_NAME"));
            tableMeta.setRemarks(tablesRs.getString("REMARKS"));

            StringBuilder primaryKey = new StringBuilder();
            ResultSet primaryKeysRs = databaseMetaData.getPrimaryKeys(null, null, tableMeta.getName());
            while (primaryKeysRs.next()) {
                // TABLE_CAT : test_db_information | TABLE_SCHEM : null | TABLE_NAME : t_cms_topic | COLUMN_NAME : ID | KEY_SEQ : 1 | PK_NAME : PRIMARY
                primaryKey.append(primaryKeysRs.getString("COLUMN_NAME")).append(",");
            }
            primaryKeysRs.close();
            primaryKey.deleteCharAt(primaryKey.length() - 1);
            tableMeta.setPrimaryKey(primaryKey.toString());

            ResultSet columnsRs = databaseMetaData.getColumns(null, null, tableMeta.getName(), null);
            tableMeta.setColumns(new ArrayList<ColumnMeta>());

            while (columnsRs.next()) {
                // TABLE_CAT : test_db_information | TABLE_SCHEM : null | TABLE_NAME : t_cms_topic | COLUMN_NAME : ID | DATA_TYPE : -5 | TYPE_NAME : BIGINT | COLUMN_SIZE : 19 | BUFFER_LENGTH : 65535 | DECIMAL_DIGITS : 0 | NUM_PREC_RADIX : 10 | NULLABLE : 0 | REMARKS : Topic id | COLUMN_DEF : null | SQL_DATA_TYPE : 0 | SQL_DATETIME_SUB : 0 | CHAR_OCTET_LENGTH : null | ORDINAL_POSITION : 1 | IS_NULLABLE : NO | SCOPE_CATALOG : null | SCOPE_SCHEMA : null | SCOPE_TABLE : null | SOURCE_DATA_TYPE : null | IS_AUTOINCREMENT : YES | IS_GENERATEDCOLUMN :  |
                ColumnMeta columnMeta = new ColumnMeta();
                columnMeta.setName(columnsRs.getString("COLUMN_NAME"));
                columnMeta.setType(columnsRs.getString("TYPE_NAME"));
                columnMeta.setSize(columnsRs.getInt("COLUMN_SIZE"));
                columnMeta.setDecimalDigits(columnsRs.getInt("DECIMAL_DIGITS"));
                columnMeta.setRemarks(columnsRs.getString("REMARKS"));
                columnMeta.setIsNullable(columnsRs.getString("IS_NULLABLE"));
                columnMeta.setIsAutoincrement(columnsRs.getString("IS_AUTOINCREMENT"));
                if (primaryKey.indexOf(columnMeta.getName()) > 0) {
                    columnMeta.setIsPrimaryKey(true);
                }
                else {
                    columnMeta.setIsPrimaryKey(false);
                }
                tableMeta.getColumns().add(columnMeta);
            }
            columnsRs.close();
            tableMetas.add(tableMeta);


            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from " + tableMeta.getName() + " where 1 = 2");
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println(">>>> " + rsmd.getColumnName(i) + " >> " + rsmd.getColumnClassName(i));
            }

        }
        tablesRs.close();
        conn.close();
//        System.out.println(JSON.toJSONString(tableMetas, true));
    }

}
