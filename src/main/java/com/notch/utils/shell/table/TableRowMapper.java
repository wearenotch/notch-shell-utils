package com.notch.utils.shell.table;

public interface TableRowMapper<E> {
    Object[] mapToArray(E element);
}
