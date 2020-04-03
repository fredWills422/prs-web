package com.web.prs.request;

import java.sql.Date;
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
@RequestMapping(path="/requests")
public class RequestController {
	
	
	public static final String REQUEST_STATUS_NEW = "NEW";
	public static final String REQUEST_STATUS_EDIT = "EDIT";
	public static final String REQUEST_STATUS_REVIEW = "REVIEW";
	public static final String REQUEST_STATUS_APPROVED = "APPROVED";
	public static final String REQUEST_STATUS_REJECTED = "REJECTED";
	
	
	@Autowired
	private RequestRepository requestRepo;
	
	private JsonResponse save(Request request) {
		try {
			Request r = requestRepo.save(request);
			return JsonResponse.getInstance(r);
		}catch(IllegalArgumentException ex) {
			return JsonResponse.getInstance("Parameter request can not be null");
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@GetMapping()
	public JsonResponse list() {
		try {
			return JsonResponse.getInstance(requestRepo.findAll());
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public JsonResponse getByPK(@PathVariable Integer id) {
		try {
			Optional<Request> r = requestRepo.findById(id);
			if(!r.isPresent()) {
				return JsonResponse.getInstance("Request not found");
			}
			return JsonResponse.getInstance(r.get());
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PostMapping()
	public JsonResponse add(@RequestBody Request request) {
		try {
			request.setStatus(REQUEST_STATUS_NEW);
			request.setSubmittedDate(new Date(System.currentTimeMillis()));
			return save(request);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody Request request) {
		try {
			return save(request);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse remove(@PathVariable Integer id){
		try {
			Optional<Request> r = requestRepo.findById(id);
			if(!r.isPresent()) {
				return JsonResponse.getInstance("Request not found");
			}
			requestRepo.deleteById(id);
			return JsonResponse.getInstance(r.get());
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	
	@GetMapping("/list-review/{userId}")
	public JsonResponse getRequestWithStatusOfReview(@PathVariable Integer userId){
		try {
			if(userId==null) {
				return JsonResponse.getInstance("userId parameter can not be null");
			}
			Iterable<Request> requests = 
					requestRepo.findRequestByStatusAndUserIdNot(REQUEST_STATUS_REVIEW, userId);
			return JsonResponse.getInstance(requests);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	
	private JsonResponse setRequestStatus(Request request, String status) {
		try {
			request.setStatus(status);
			return save(request);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PutMapping("/submit-review/")
	public JsonResponse reviewStatus(@RequestBody Request request) {
		try {
			request.setSubmittedDate(new Date(System.currentTimeMillis()));
			if(request.getTotal() <= 50) {
				return setRequestStatus(request, REQUEST_STATUS_APPROVED);
			}
			return setRequestStatus(request, REQUEST_STATUS_REVIEW);
		}catch(Exception ex){
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PutMapping("/approve/{id}")
	public JsonResponse approvedStatus(@RequestBody Request request, @PathVariable Integer id) {
		try {
			if(id != request.getId()) {
				return JsonResponse.getInstance("Parameter id does not match request.id");
			}
			return setRequestStatus(request, REQUEST_STATUS_APPROVED);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PutMapping("/reject/{id}")
	public JsonResponse rejectStatus(@RequestBody Request request, @PathVariable Integer id) {
		try {
			if(id != request.getId()) {
				return JsonResponse.getInstance("Parameter id does not match request.id");
			}
			return setRequestStatus(request, REQUEST_STATUS_REJECTED);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PutMapping("/new/{id}")
	public JsonResponse newStatus(@RequestBody Request request, @PathVariable Integer id) {
		try {
			if(id != request.getId()) {
				return JsonResponse.getInstance("Parameter id does not match request.id");
			}
			return setRequestStatus(request, REQUEST_STATUS_NEW);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	@PutMapping("/edit/{id}")
	public JsonResponse editStatus(@RequestBody Request request, @PathVariable Integer id) {
		try {
			if(id != request.getId()) {
				return JsonResponse.getInstance("Parameter id does not match request.id");
			}
			return setRequestStatus(request, REQUEST_STATUS_EDIT);
		}catch(Exception ex) {
			return JsonResponse.getInstance(ex.getMessage());
		}
	}
	
	
}
