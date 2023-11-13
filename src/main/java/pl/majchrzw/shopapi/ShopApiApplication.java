package pl.majchrzw.shopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.majchrzw.shopapi.components.UserDataInitializer;

@SpringBootApplication
public class ShopApiApplication  {

	public static void main(String[] args) {
		UserDataInitializer userDataInitializer = SpringApplication
				.run(ShopApiApplication.class, args)
				.getBean(UserDataInitializer.class);
		
		userDataInitializer.initializeData();
	}

}
