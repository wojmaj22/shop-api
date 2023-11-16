package pl.majchrzw.shopapi.bdd.tooling;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import pl.majchrzw.shopapi.ShopApiApplication;
import pl.majchrzw.shopapi.dao.OrderDetailRepository;
import pl.majchrzw.shopapi.dao.OrderRepository;
import pl.majchrzw.shopapi.dao.ProductRepository;

@AutoConfigureMockMvc
@CucumberContextConfiguration
@SpringBootTest(classes = ShopApiApplication.class)
@MockBeans( {@MockBean(ProductRepository.class), @MockBean(OrderRepository.class), @MockBean(OrderDetailRepository.class)})
public class SpringGlue{
}
