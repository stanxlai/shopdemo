package com.example.shopdemo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.shopdemo.service.MemberService;
import com.example.shopdemo.vo.Member;
import com.example.shopdemo.vo.Paging;

@RestController
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private MemberService service;
	
    @GetMapping("/")
    public List<Member> read() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> read(@PathVariable("id") Integer id) {
        Member foundMember = service.findById(id);
        if (foundMember == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundMember);
        }
    }

    @GetMapping("/paging/page/{current}/pagecount/{pagecount}")
    public ResponseEntity<Paging<Member>> paging(@PathVariable("current") Integer current, @PathVariable("pagecount") Integer pagecount) {
        Paging<Member> foundMember = service.findAllByPaging(current, pagecount);
        if (foundMember == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundMember);
        }
    }


    @PostMapping("/")
    public ResponseEntity<Member> create(@RequestBody Member member) throws URISyntaxException {
        Member createdMember = service.create(member);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdMember.getId())
            .toUri();

        return ResponseEntity.created(uri)
            .body(createdMember);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@RequestBody Member member, @PathVariable Integer id) {
        Member updatedMember = service.update(id, member);
        if (updatedMember == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedMember);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMember(@PathVariable Integer id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
