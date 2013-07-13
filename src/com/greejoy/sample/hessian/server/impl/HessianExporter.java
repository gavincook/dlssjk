package com.greejoy.sample.hessian.server.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.greejoy.support.spring.hessian.HessianServiceHelper;

public class HessianExporter implements BeanFactoryAware,ApplicationListener<ContextRefreshedEvent >{

	DefaultListableBeanFactory  bf;
	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		System.out.println("执行了.....");
		bf = (DefaultListableBeanFactory) arg0;
		/*BeanDefinitionBuilder ss = BeanDefinitionBuilder  
                .genericBeanDefinition(SampleServerImpl.class);  
		 bf.registerBeanDefinition("/ss",  
	                ss.getRawBeanDefinition()); 
		BeanDefinitionBuilder userBeanDefinitionBuilder = BeanDefinitionBuilder  
                .genericBeanDefinition(HessianServiceExporter.class);  
        userBeanDefinitionBuilder.addPropertyValue("service", bf.getBean(SampleServer.class));  
        userBeanDefinitionBuilder.addPropertyValue("serviceInterface",SampleServer.class);  
        System.out.println("注册bean...........");
        bf.registerBeanDefinition("/sampleHessian",  
                userBeanDefinitionBuilder.getRawBeanDefinition());  */
		
		for(String beanName:HessianServiceHelper.getHessianServiceMap().keySet()){
			System.out.println("准备注册..."+beanName);
			bf.registerBeanDefinition(beanName, HessianServiceHelper.getHessianServiceMap().get(beanName));
		}
	}
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
        
	}
	

	
}
