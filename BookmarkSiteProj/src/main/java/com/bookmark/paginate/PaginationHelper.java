package com.bookmark.paginate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PaginationHelper<E> {
	
	private final int defaultPageSize = 10;
	
	public Page<E> fetchPage (
			final JdbcTemplate jt,
			final String sqlCountRows,
			final String sqlFetchRows,
			final Object args[],
			final int targetPageNo,
			final int targetPageSize,
			final ParameterizedRowMapper<E> rowMapper) {
		
		final int rowCount = jt.queryForInt(sqlCountRows, args);
		
		final int pageSize;
		final int pageNo;
		if (rowCount < targetPageSize) {
			pageSize = rowCount;
			pageNo = 1;
		} else {
			if (targetPageSize < 1) {
				pageSize = defaultPageSize;
			} else {
				pageSize = targetPageSize;
			}
			if (targetPageNo < 1) {
				pageNo = 1;
			} else {
				pageNo = targetPageNo;
			}
		}
		
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
