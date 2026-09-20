
package com.vangasuthalam.controller;

import com.vangasuthalam.service.CustomerLoginService;

public class CustomerLoginController {

    private CustomerLoginService customerLoginService;

    public CustomerLoginController() {

        customerLoginService = new CustomerLoginService();
    }

    public int loginCustomer(String email, String password) {

        return customerLoginService.loginCustomer(email, password);
    }
}


