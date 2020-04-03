package com.web.prs.lineItem;

import java.util.ArrayList;
import java.util.List;
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

import com.web.prs.request.Request;
import com.web.prs.request.RequestController;
import com.web.prs.request.RequestRepository;
import com.web.prs.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping(path="/line-items")
public class LineItemController {
	
	@Autowired
	private RequestRepository requestRepo;
	
	@Autowired
	private LineItemRepository lineItemRepo;
	
	
	private void recalculateTotal(int requestId) throws Exception{
		Optional<Request> r = requestRepo.findById(requestId);
		if(!r.isPresent()) {
			throw new Exception ("The requestId" +requestId+ "was not found");
		}
		Request request = r.get();
		double total = 0;
		Iterable<LineItem> lines = lineItemRepo.findLineItemByRequestId(requestId);
		for(LineItem line: lines) {
			total += line.getQuantity() * line.getProduct().getPrice();
			request.setTotal(total);
			request.setStatus(RequestController.REQUEST_STATUS_EDIT);
			requestRepo.save(request);
		}
	}
	
	public JsonResponse save(@RequestBody LineItem lineItem) {
		try {
			LineItem l = lineItemRepo.save(lineItem);
			return JsonResponse.getInstance(l);
		}catch(IllegalArgumentException ex) {
			return JsonResponse.getInstance("Parameter lineItem can not be null");
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	
	@GetMapping()
	public JsonResponse list() {
		try {
			return JsonResponse.getInstance(lineItemRepo.findAll());
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public JsonResponse getByPK(@PathVariable Integer id) {
		try {
			Optional<LineItem> l = lineItemRepo.findById(id);
			if(!l.isPresent()) {
				return JsonResponse.getInstance("LineItem not found");
			}
			return JsonResponse.getInstance(l.get());
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}

	@PostMapping()
	public JsonResponse add(@RequestBody LineItem lineItem) {
		try {
			JsonResponse jr = save(lineItem);
			recalculateTotal(lineItem.getRequest().getId());
			return jr;
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody LineItem lineItem) {
		try {
			JsonResponse jr = save(lineItem);
			recalculateTotal(lineItem.getRequest().getId());
			return jr;
		}catch(Exception ex){
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse remove(@PathVariable Integer id) {
		try {
			Optional<LineItem> l = lineItemRepo.findById(id);
			if(!l.isPresent()) {
				return JsonResponse.getInstance("LineItem not found");
			}
			lineItemRepo.deleteById(id);
			lineItemRepo.flush();
			recalculateTotal(l.get().getRequest().getId());
			return JsonResponse.getInstance(l.get());
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	
	@GetMapping("/lines-for-pr/{requestId}")
	public JsonResponse linesForRequest(@PathVariable Integer requestId) {
		try {
			List<LineItem> listOfLineItemsWithRequestId = new ArrayList<>();
			Iterable<LineItem> lines = lineItemRepo.findLineItemByRequestId(requestId);
			for(LineItem l: lines) {
				listOfLineItemsWithRequestId.add(l);
			}
			return JsonResponse.getInstance(listOfLineItemsWithRequestId);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
}
