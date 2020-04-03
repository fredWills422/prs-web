package com.web.prs.vendor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	Vendor findByIdAndCode(Integer id, String code);
	
}
