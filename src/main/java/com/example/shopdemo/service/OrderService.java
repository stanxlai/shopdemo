package com.example.shopdemo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.shopdemo.vo.Member;
import com.example.shopdemo.vo.Order;
import com.example.shopdemo.vo.Paging;


@Service
public class OrderService {

	@Autowired
	private MemberService memberService;
	
    private final Set<Order> orders = new HashSet<>();

    public Order findById(int id) {
        return orders.stream()
            .filter(obj -> obj.getId() == id)
            .findFirst()
            .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    public Set<Order> findAll() {
        return orders;
    }

    public Paging<Order> findByProductName(String productName, int current, int pageCount) {
    	List<Order> list = (orders.stream()
    		.filter(
    				obj -> obj.getOrderItems().stream()
    					.filter(obj2 -> obj2.getProduct().getName().equals(productName))
    					.findFirst().isPresent()
    				)
    		.collect(Collectors.toList()));
    	
    	return this.findAllByPaging(list, current, pageCount);
    }

    public Paging<Order> findByDatetime(String datetime, int current, int pageCount) {
    	List<Order> list = (orders.stream()
    		.filter(
    				obj -> obj.getDate().startsWith(datetime)
    				)
    		.collect(Collectors.toList()));
    	
    	return this.findAllByPaging(list, current, pageCount);
    }
    
    public Set<Member> findByCount(long count) {
    	
    	Map<Integer, Long> result = orders.stream().map(obj -> obj.getMemberId()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    	
    	List<Member> members = new ArrayList<>();
    	for (Map.Entry<Integer, Long> entry : result.entrySet()) {
			Integer key = entry.getKey();
			Long val = entry.getValue();
			if (val >= count) {
				members.add(memberService.findById(key));
			}
			
		}	
    	
    	return new HashSet<>(members);
    	
    }

	public Paging<Order> findAllByPaging(List<Order> orders, int current, int pageCount) {
		Paging<Order> paging = new Paging<>();
		
		if (current <= 0) {
			current = 1;
		}
		
		if (pageCount < 1) {
			pageCount = 10;
		}
		
		paging.setCurrent(current);
		paging.setPageCount(pageCount);
		paging.setTotal(orders.size() / pageCount + (orders.size() % pageCount > 0 ? 1 : 0));
		for (int i = (current - 1) * pageCount; i < current * pageCount && i < orders.size();  i++ ) {
			paging.getList().add(orders.get(i));
		}
		
		return paging;
		
	}
	

    public Order save(Order order) {
        if (StringUtils.isEmpty(order.getDate())) {
            throw new IllegalArgumentException();
        }
        synchronized (orders) {
        	int newId = 1;
        	OptionalInt oldId = orders.stream()
        			.mapToInt(Order::getId)
        			.max();
        	if (oldId.isPresent()) {
        		newId = oldId.getAsInt() + 1;
        		
        	}
        	order.setId(newId);
        	orders.add(order);
			
		}
        return order;
    }

    public static class OrderNotFoundException extends RuntimeException {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public OrderNotFoundException(String msg) {
            super(msg);
        }
    }
}