package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Account;
import com.example.entity.dto.AccountDetails;
import com.example.entity.vo.request.ConfirmResetVO;
import com.example.entity.vo.request.DetailsSaveVO;
import com.example.entity.vo.request.EmailRegisterVO;
import com.example.entity.vo.request.EmailResetVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountDetailsService extends IService<AccountDetails> {

    AccountDetails findAccountDetailsById(int id);

   boolean saveAccountDetails(int id , DetailsSaveVO vo);

}
