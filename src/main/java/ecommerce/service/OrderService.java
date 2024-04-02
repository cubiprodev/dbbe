package ecommerce.service;

import ecommerce.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);

    List<OrderDto> getAllOrder();
     List<OrderDto> getOrdersByProductId(int productId) throws IllegalArgumentException;

     OrderDto updateOrder(OrderDto orderDto, int id);
}
