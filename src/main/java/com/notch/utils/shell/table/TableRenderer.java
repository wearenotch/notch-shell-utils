package com.notch.utils.shell.table;
import java.util.List;

public interface TableRenderer {
    
    <E> String render(List<String> headers, List<E> elements,  TableRowMapper<E> rowMapper);
    
}
