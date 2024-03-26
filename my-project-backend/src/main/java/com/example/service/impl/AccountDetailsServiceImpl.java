package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Account;
import com.example.entity.dto.AccountDetails;
import com.example.entity.vo.request.DetailsSaveVO;
import com.example.mapper.AccountDetailsMapper;
import com.example.service.AccountDetailsService;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * 账户详细信息处理相关服务
 */
@Service
@AllArgsConstructor
public class AccountDetailsServiceImpl extends ServiceImpl<AccountDetailsMapper, AccountDetails> implements AccountDetailsService {


    private AccountService accountService;


    @Override
    public AccountDetails findAccountDetailsById(int id) {
        return this.getById(id);
    }

    @Override
    public synchronized boolean saveAccountDetails(int id, DetailsSaveVO vo) {
        Account account = accountService.findAccountByNameOrEmail(vo.getUsername());
        if (ObjectUtils.isEmpty(account) || account.getId() == id){
            accountService.lambdaUpdate().eq(Account::getId,id)
                    .set(Account::getUsername,vo.getUsername())
                    .update();
            AccountDetails accountDetails = AccountDetails.builder().id(id).phone(vo.getPhone()).gender(vo.getGender())
                    .qq(vo.getQq()).wx(vo.getWx()).description(vo.getDescription()).build();
            this.saveOrUpdate(accountDetails);
            return true;
        }
        return false;
    }
}
