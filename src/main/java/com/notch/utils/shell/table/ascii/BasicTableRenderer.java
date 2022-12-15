package com.notch.utils.shell.table.ascii;

import com.notch.utils.shell.table.TableRenderer;
import com.notch.utils.shell.table.TableRowMapper;

import de.vandermeer.asciitable.AT_ColumnWidthCalculator;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

import java.util.List;

/**
 * 
 * @author dmadunic on 12.12.22.
 * @param <E>
 */
public class BasicTableRenderer implements TableRenderer {
    private static final AT_ColumnWidthCalculator DEFAULT_CWC = new CWC_LongestLine();
    public static final int DEFAULT_TABLE_WIDTH=100;

    private AT_ColumnWidthCalculator cwc;
    private int width;

    public BasicTableRenderer() {
        this(DEFAULT_CWC, DEFAULT_TABLE_WIDTH);
    }

    public BasicTableRenderer(int tableWidth) {
        this(DEFAULT_CWC, tableWidth);
    }

    public BasicTableRenderer(AT_ColumnWidthCalculator cwc, int tableWidth) {
        this.cwc = (cwc != null ? cwc : DEFAULT_CWC);
        this.width = tableWidth;
    }
   
    @Override
    public <E> String render(List<String> headers, List<E> elements, TableRowMapper<E> rowMapper) {
        AsciiTable at = new AsciiTable();
        at.getRenderer().setCWC(cwc);

        at.addRule();
        at.addRow(headers);
        at.addRule();
        for(E el : elements) {
            at.addRow(rowMapper.mapToArray(el));
            at.addRule();
        }
        return at.render(width);
    }

   

    // ----- get / set methods -------------------------------------------------------------

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setCwc(AT_ColumnWidthCalculator cwc) {
        this.cwc = cwc;
    }

    public AT_ColumnWidthCalculator getCwc() {
        return cwc;
    }

}
