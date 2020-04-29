/**
 *
 */
package com.swsk.data.util.generate.db;

import java.io.Serializable;
import java.util.List;

/**
 * @author y.z
 */
public class Page<T> implements Serializable{

	private static final long serialVersionUID = -5395997221963176643L;
	private List<T> list;
	private int pageNumber;
	private int pageSize;
	private int totalPage;
	private int totalRow;

	/**
	 * Constructor.
	 * @param list the list of paginate result
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param totalPage the total page of paginate
	 * @param totalRow the total row of paginate
	 */
	public Page(List<T> list, int pageNumber, int pageSize, int totalPage, int totalRow) {
		this.list = list;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalRow = totalRow;
	}

	/**
	 * Return list of this page.
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * Return page number.
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * Return page size.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Return total page.
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * Return total row.
	 */
	public int getTotalRow() {
		return totalRow;
	}

	public boolean isFirstPage() {
		return pageNumber == 1;
	}

	public boolean isLastPage() {
		return pageNumber == totalPage;
	}
}
