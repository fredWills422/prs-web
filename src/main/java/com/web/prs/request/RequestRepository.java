package com.web.prs.request;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer>{

	Iterable<Request> findRequestByStatusAndUserIdNot(String status, Integer userId);
	
}
