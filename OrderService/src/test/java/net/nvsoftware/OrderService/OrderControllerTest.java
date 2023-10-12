package net.nvsoftware.OrderService;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import net.nvsoftware.OrderService.repository.OrderRepository;
import net.nvsoftware.OrderService.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@SpringBootTest
@EnableConfigurationProperties
@AutoConfigureMockMvc
@ContextConfiguration(classes = OrderServiceTestConfig.class)
public class OrderControllerTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8053))
            .build();

    private ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @BeforeEach
    void setupTest() throws IOException { // put common methods every test will use here
        reduceQuantity();
        getProductResponse();
        doPayment();
        getPaymentResponse();
    }


    private void getPaymentResponse() throws IOException {
        wireMockExtension.stubFor(WireMock.get("/payments/.*") // urlMatching("/payments/.*")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(StreamUtils.copyToString(
                                OrderControllerTest.class
                                        .getClassLoader()
                                        .getResourceAsStream("mockData/paymentResponse.json"),
                                Charset.defaultCharset()
                        ))
                )
        );
    }

    private void doPayment() {
        wireMockExtension.stubFor(WireMock.post("/payments") // urleq
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }

    private void getProductResponse() throws IOException {

        wireMockExtension.stubFor(WireMock.get("/products/1")
                .willReturn(WireMock.aResponse().
                        withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(StreamUtils.copyToString(
                                OrderControllerTest.class
                                        .getClassLoader()
                                        .getResourceAsStream("mockData/productResponse.json"),
                                Charset.defaultCharset()
                        ))
                ));

    }

    private void reduceQuantity() {
        wireMockExtension.stubFor(WireMock.put("/product/reduceQuantity")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }


    @Test
    public void testPlaceOrderDoPaymentSuccess() {
        // PlaceOrder
        // Get OrderEntity from DB by orderId
        // Verify OrderResponse
    }
    @Test
    public void testGetOrderDetailByIdSuccess() {
        // Get OrderEntity from DB by orderId
        // Get ProductResponse from DB by orderId
        // Get PaymentResponse from DB by orderId
        // Verify OrderResponse
    }


}
