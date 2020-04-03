package com.web.prs.lineItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem, Integer>{

	Iterable<LineItem> findLineItemByRequestId(Integer requestId);
	
}
