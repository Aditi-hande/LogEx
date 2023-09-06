package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@SpringBootApplication(scanBasePackages={"com.example.demo"})
public class LEASApplication {

	public static void main(String[] args) {
		SpringApplication.run(LEASApplication.class, args);
		
	}
	@Bean
	public SchedulerFactoryBean schedulerFactory(ApplicationContext applicationContext) {
		SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		factoryBean.setJobFactory(jobFactory);
		factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
		return factoryBean;
	}
}
