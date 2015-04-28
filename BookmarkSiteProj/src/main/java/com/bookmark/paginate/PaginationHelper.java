package com.bookmark.paginate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PaginationHelper<E> {
	
	public Page<E> fetchPage (
			final JdbcTemplate jt,
			final String sqlCountRows,
			final String sqlFetchRows,
			final Object args[],
			final int pageNo,
			final int pageSize,
			final ParameterizedRowMapper<E> rowMapper) {
		
		final int rowCount = jt.queryForInt(sqlCountRows, args);
		
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		
		final Page<E> page = new Page<E>();
		page.setPageNumber(pageNo);
		page.setPageAvaliable(pageCount);
		page.setPageSize(pageSize);
		
		final int startRow = (pageNo - 1) * pageSize;
		jt.query(
				sqlFetchRows, 
				args, 
				new ResultSetExtractor(){

					@Override
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						final List pageItems = page.getPageItems();
						int currentRow = 0;
						while (rs.next() && currentRow < startRow + pageSize) {
							if (currentRow >= startRow) {
								pageItems.add(rowMapper.mapRow(rs, currentRow));
							}
							currentRow++;
						}
						return page;
					}
					
				});
		
		return page;
	}

}
