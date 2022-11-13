package com.example.shopdemo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.shopdemo.service.OrderService;
import com.example.shopdemo.service.OrderService.OrderNotFoundException;
import com.example.shopdemo.service.ProductService;
import com.example.shopdemo.service.ProductService.ProductNotFoundException;
import com.example.shopdemo.vo.Member;
import com.example.shopdemo.vo.Order;
import com.example.shopdemo.vo.OrderItem;
import com.example.shopdemo.vo.Paging;
import com.example.shopdemo.vo.Product;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
    @GetMapping("/orderid/{id}")
    public ResponseEntity<Order> orderid(@PathVariable("id") Integer id) {
    	try {
    		Order foundOrder = orderService.findById(id);
    		if (foundOrder == null) {
    			return ResponseEntity.notFound().build();
    		} else {
    			return ResponseEntity.ok(foundOrder);
    		}
    		
    	} catch (OrderNotFoundException ex) {
    		return ResponseEntity.notFound().build();
    		
    	}

    }

    @GetMapping("/productname/{productname}/paging/page/{current}/pagecount/{pagecount}")
    public ResponseEntity<Paging<Order>> productname(@PathVariable("productname") String productname, @PathVariable("current") Integer current, @PathVariable("pagecount") Integer pagecount) {
    	try {
    		Paging<Order> foundOrder = orderService.findByProductName(productname, current, pagecount);
    		if (foundOrder == null) {
    			return ResponseEntity.notFound().build();
    		} else {
    			return ResponseEntity.ok(foundOrder);
    		}
    		
    	} catch (OrderNotFoundException ex) {
    		return ResponseEntity.notFound().build();
    		
    	}

    }

    @GetMapping("/datetime/{datetime}/paging/page/{current}/pagecount/{pagecount}")
    public ResponseEntity<Paging<Order>> datetime(@PathVariable("datetime") String datetime, @PathVariable("current") Integer current, @PathVariable("pagecount") Integer pagecount) {
    	try {
    		Paging<Order> foundOrder = orderService.findByDatetime(datetime, current, pagecount);
    		if (foundOrder == null) {
    			return ResponseEntity.notFound().build();
    		} else {
    			return ResponseEntity.ok(foundOrder);
    		}
    		
    	} catch (OrderNotFoundException ex) {
    		return ResponseEntity.notFound().build();
    		
    	}

    }

    @GetMapping("/ordercount/{ordercount}")
    public ResponseEntity<Set<Member>> ordercount(@PathVariable("ordercount") Long ordercount) {
    	try {
    		Set<Member> foundOrder = orderService.findByCount(ordercount);
    		if (foundOrder == null) {
    			return ResponseEntity.notFound().build();
    		} else {
    			return ResponseEntity.ok(foundOrder);
    		}
    		
    	} catch (OrderNotFoundException ex) {
    		return ResponseEntity.notFound().build();
    		
    	}

    }

    @PostMapping("/buy/product/{productId}/quantity/{quantity}")
    public ResponseEntity<Order> create(@PathVariable("productId") Integer proudctId, 
    		@PathVariable("quantity") Integer quantity, 
    		@RequestBody Member member) throws URISyntaxException {
    	try {
    		Product product = productService.findById(proudctId);
    		
    		byte[] array = new byte[7]; // length is bounded by 7
    		new Random().nextBytes(array);
    		String generatedString = new String(array, Charset.forName("UTF-8"));
    		
    		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
    		String date = df.format(new Date()) + "_" + generatedString;
    		Order order = new Order(date, -1, member.getId());
    		
    		synchronized (order.getOrderItems()) {
    			int newId = 1;
    			OptionalInt oldId = order.getOrderItems().stream()
    					.mapToInt(OrderItem::getId)
    					.max();
    			if (oldId.isPresent()) {
    				newId = oldId.getAsInt() + 1;
    			}
    			
    			OrderItem item = new OrderItem(product, newId, quantity);
    			order.getOrderItems().add(item);
    			
    		}
    		
    		Order createdOrder = orderService.save(order);
    		
    		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
    				.path("/{id}")
    				.buildAndExpand(createdOrder.getId())
    				.toUri();
    		
    		return ResponseEntity.created(uri)
    				.body(createdOrder);
    		
    	} catch (ProductNotFoundException ex) {
    		return ResponseEntity.notFound().build();
    		
    	}
    	
    	
    }


}
