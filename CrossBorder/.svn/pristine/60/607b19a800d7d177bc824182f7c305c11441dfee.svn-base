package com.impalapay.airtel.accountmgmt.pagination;

import com.impalapay.airtel.beans.transaction.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




/**
 * A page with <code>{@link Transaction}</code>
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class TransactionPage implements Serializable {
	
	private int pageNum;
	private int totalPage;
	private int pagesize;
	private List<Transaction> contents;

	/**
	 * 
	 */
	public TransactionPage() {
		pageNum = 1;
		totalPage = 1;
		pagesize = 1;
		contents = new ArrayList<>();
	}
	
	
	/**
	 * 
	 * @param pageNum
	 * @param totalPage
	 * @param pagesize
	 * @param contents
	 */
	public TransactionPage(final int pageNum, final int totalPage, final int pagesize,
			final List<Transaction> contents) {
		this.pageNum = pageNum;
		this.totalPage = totalPage;
		this.pagesize = pagesize;
		this.contents = contents;
	}

	
	/**
	 * 
	 * @return int
	 */
	public int getPageNum() {
		return pageNum;
	}

	
	/**
	 * 
	 * @return int
	 */
	public int getTotalPage() {
		return totalPage;
	}

	
	/**
	 * 
	 * @return int
	 */
	public int getPagesize() {
		return pagesize;
	}

	
	/** 
	 * 
	 * @return List<Transaction> 
	 */
	public List<Transaction> getContents() {
		return new ArrayList<Transaction>(contents);		
	}

	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isFirstPage () {
		return pageNum == 1;
	}

	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isLastPage () {
		return pageNum == totalPage;
	}

	/**
	 * 
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TransactionPage)) return false;
	
		final TransactionPage page = (TransactionPage) o;
	
		if (pageNum != page.getPageNum()) return false;
		if (pagesize != page.getPagesize()) return false;
		if (totalPage != page.getTotalPage()) return false;
		if (contents != null ? 	!isListEqual (contents, page.getContents()) : page.getContents() != null)
			return false;
	
		return true;
	}
	
	/**
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		int result;
		result = pageNum;
		result = 29 * result + totalPage;
		result = 29 * result + pagesize;
		result = 29 * result + (contents != null ? 
		listHashCode (contents) : 0);
		return result;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return boolean
	 */
	private boolean isListEqual(final List<Transaction> a, final List<Transaction> b) {
		if (a == b || a.equals(b)) return true;
	
		final Iterator<Transaction> ia = a.iterator ();
		final Iterator<Transaction> ib = b.iterator ();
		while (ia.hasNext() && ib.hasNext()) {
			final Object oa = ia.next();
			final Object ob = ib.next();
			if (!oa.equals(ob)) {
				return false;
			}
		}
		if (ia.hasNext() || ib.hasNext()) {
			return false;
		}
		return true;
	}

	
	/**
	 * 
	 * @param a
	 * @return int
	 */
	private int listHashCode(final List<Transaction> a) {
		int result = 0;
		for (Iterator<Transaction> iterator = a.iterator(); iterator.hasNext();) {
			final Object o = iterator.next();
			result = 29 * result + o.hashCode();
		}
		return result;
	}

	
	/**
	 * @return String
	 */
	@Override
	public String toString () {
		final StringBuffer sb = new StringBuffer ();
		sb.append ("Page ").append (pageNum)
		.append (" of ").append (totalPage);
		sb.append ("\n");
	
		for (Iterator<Transaction> it = contents.iterator(); it.hasNext();) {
			final Object o = it.next();
			sb.append (o).append ("\n");
		}
		
		return sb.toString ();
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
