package com.be.utility;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import com.be.model.Post_;

public class ModelSorting {
	/**
	 * Sort by combination of selected attribute <br>
	 * <i>Example: sortBy = (1+2+4) = 7 => sort by price, view and averageRating
	 * attribute
	 * 
	 * @param sortBy         null for unsorted and possible value is 1, 2, 4
	 * @param sortDescending true if sort in descending order, otherwise ascending
	 * @return Sort object <br>
	 *         sortBy possible value <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; 1: by createDate - newest post <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; 2: by salary <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; 4: by number of recruit <br>
	 */
	public static Sort getPostSort(Integer sortBy, Boolean sortDescending) {
		Sort sort = Sort.unsorted();

		if (sortBy != null) {
			if (sortDescending == null || !sortDescending.booleanValue()) { // ASC
					if (sortBy >= 4) {
					sort = sort.and(JpaSort.of(Post_.recruit).ascending());
					sortBy -= 4;
				}
				if (sortBy >= 2) {
					sort = sort.and(JpaSort.of(Post_.salary).ascending());
					sortBy -= 2;
				}
				if (sortBy >= 1) {
					sort = sort.and(JpaSort.of(Post_.createDate).ascending());
					sortBy -= 1;
				}
			} else { // DESC
				
				if (sortBy >= 4) {
					sort = sort.and(JpaSort.of(Post_.recruit).descending());
					sortBy -= 4;
				}
				if (sortBy >= 2) {
					sort = sort.and(JpaSort.of(Post_.salary).descending());
					sortBy -= 2;
				}
				if (sortBy >= 1) {
					sort = sort.and(JpaSort.of(Post_.createDate).descending());
					sortBy -= 1;
				}
			}
		}
		return sort;
	}
}
