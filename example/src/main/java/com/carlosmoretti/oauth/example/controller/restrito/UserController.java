package com.carlosmoretti.oauth.example.controller.restrito;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carlosmoretti.oauth.example.domain.User;
import com.carlosmoretti.oauth.example.repository.RoleRepository;
import com.carlosmoretti.oauth.example.repository.UserRepository;
import com.carlosmoretti.oauth.example.shared.constant.ProfileConst;

@RestController
@RequestMapping("/restrito/user")
public class UserController {
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
	
	@Secured({ProfileConst.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public User save(@RequestBody User user){
        user = this.userRepository.save(user);
        return user;
    }

    @Secured({ProfileConst.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public User edit(@RequestBody User user){
        user = this.userRepository.save(user);
        return user;
    }

    @Secured({ProfileConst.ROLE_ADMIN, ProfileConst.ROLE_CLIENTE})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<User>> list(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
    	Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return new ResponseEntity<Page<User>>(userRepository.findAll(pageable), HttpStatus.OK);
    }
}
