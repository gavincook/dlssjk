package com.greejoy.sample.hessian.server.impl;

import com.greejoy.sample.hessian.server.SampleServer;
import com.greejoy.support.spring.hessian.annotation.Hessian;

@Hessian(path="/remote/sampleHessian")
public class SampleServerImpl implements SampleServer {

	
	@Override
	public String doSomething(String topic) {
		System.out.println(topic);
		return "i'm doing =========="+topic;
	}

}
