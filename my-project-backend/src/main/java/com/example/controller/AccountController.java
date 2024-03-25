package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.dto.Account;
import com.example.entity.vo.response.AccountVO;
import com.example.service.AccountService;
import com.example.utils.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/info")
    public RestBean<AccountVO> info(@RequestAttribute(Const.ATTR_USER_ID) Integer id){
        Account account = accountService.findAccountById(id);
        return RestBean.success(account.asViewObject(AccountVO.class));
    }
}
