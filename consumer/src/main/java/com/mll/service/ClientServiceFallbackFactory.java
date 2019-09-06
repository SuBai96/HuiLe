package com.mll.service;

import feign.hystrix.FallbackFactory;

//@Component
public class ClientServiceFallbackFactory implements FallbackFactory<MLLService>{
    @Override
    public MLLService create(Throwable cause) {
        //return new
        return null;
    }
}
