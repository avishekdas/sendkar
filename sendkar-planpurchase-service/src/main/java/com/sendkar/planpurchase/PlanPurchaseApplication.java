package com.sendkar.planpurchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		PlanPurchaseApplication.class,
		Jsr310JpaConverters.class
})
public class PlanPurchaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanPurchaseApplication.class, args);
	}
}
