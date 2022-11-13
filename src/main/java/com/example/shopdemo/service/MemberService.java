package com.example.shopdemo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.shopdemo.vo.Member;
import com.example.shopdemo.vo.Paging;

@Service
public class MemberService {

	
	private List<Member> members = new ArrayList<>();
	
    {
    	members.add(new Member(1, "Book", "Lee", "20000909"));
    	members.add(new Member(2, "Pen", "Johnson", "19990101"));
    	members.add(new Member(3, "丁噹", "丁", "199970909"));
    	members.add(new Member(4, "悟空", "孫", "19990101"));
    	members.add(new Member(5, "天津", "范", "20000909"));
    	members.add(new Member(6, "白白", "桃", "19990101"));
    }

	public MemberService() {
	}

	
	public Member findById(int id) {
        return members.stream()
                .filter(obj -> obj.getId() == id)
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));
	}
	
	public Member create(Member member) {
        if (StringUtils.isEmpty(member.getFirstName()) || StringUtils.isEmpty(member.getLastName())) {
            throw new IllegalArgumentException();
        }
        int newId = members.stream()
            .mapToInt(Member::getId)
            .max()
            .getAsInt() + 1;
        member.setId(newId);
        members.add(member);
        return member;
		
	}
	
	public List<Member> findAll() {
		return this.members;
		
	}

	public Paging<Member> findAllByPaging(int current, int pageCount) {
		Paging<Member> paging = new Paging<>();
		
		if (current <= 0) {
			current = 1;
		}
		
		if (pageCount < 1) {
			pageCount = 10;
		}
		
		paging.setCurrent(current);
		paging.setPageCount(pageCount);
		paging.setTotal(this.members.size() / pageCount + (this.members.size() % pageCount > 0 ? 1 : 0));
		for (int i = (current - 1) * pageCount; i < current * pageCount && i < this.members.size();  i++ ) {
			paging.getList().add(this.members.get(i));
		}
		
		return paging;
		
	}
	

	public Member update(int id, Member member) {
		Member vo = null;
		
		vo = members.stream()
				.filter(obj -> obj.getId() == id)
				.findFirst()
				.orElseThrow(() -> new MemberNotFoundException("Member not found"));
		synchronized (vo) {
			vo.setFirstName(member.getFirstName());
			vo.setLastName(member.getLastName());
			vo.setBirthday(member.getBirthday());
			vo.setPhoneNumber(member.getPhoneNumber());
		}
		
		return vo;
		
	}
	
	public synchronized void delete(int id) {
		Iterator<Member> iter = this.members.iterator();
		while(iter.hasNext()) {
			Member vo = iter.next();
			if (vo.getId() == (id)) {
				iter.remove();
				break;
			}
		}
	}

    public static class MemberNotFoundException extends RuntimeException {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public MemberNotFoundException(String msg) {
            super(msg);
        }
    }

}
