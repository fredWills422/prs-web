package com.web.prs.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.prs.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping(path="/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepo;
	
	private JsonResponse save(Product product) {
		try {
			return JsonResponse.getInstance(productRepo.save(product));
		}catch(IllegalArgumentException ex) {
			return JsonResponse.getInstance("Parameter product can not be null");
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@GetMapping()
	public JsonResponse list() {
		try {
			return JsonResponse.getInstance(productRepo.findAll());
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public JsonResponse getByPk(@PathVariable Integer id) {
		try {
			if(id==null) return JsonResponse.getInstance("Parameter id can not be null");
			Optional<Product> p = productRepo.findById(id);
			if(!p.isPresent()) {
				return JsonResponse.getInstance("Product not found");
			}
			return JsonResponse.getInstance(p.get());
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PostMapping()
	public JsonResponse add(@RequestBody Product product) {
		try {
			return save(product);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody Product product) {
		try {
			return save(product);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse remove(@PathVariable Integer id) {
		try {
			if(id==null) return JsonResponse.getInstance("Parameter id can't be null"); 
			Optional<Product> p = productRepo.findById(id);
			if(!p.isPresent()) {
				return JsonResponse.getInstance("Product not found");
			}
			productRepo.deleteById(p.get().getId());
			return JsonResponse.getInstance(p.get());
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
		
	}
	
}
