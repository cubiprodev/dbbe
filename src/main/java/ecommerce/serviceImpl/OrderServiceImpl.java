package ecommerce.serviceImpl;

import ecommerce.dto.OrderDto;
import ecommerce.entity.Order;
import ecommerce.entity.Product;
import ecommerce.exception.ResourceNotFoundException;
import ecommerce.repository.OrderRepo;
import ecommerce.repository.ProductRepo;
import ecommerce.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;
    @Override
    public OrderDto createOrder(OrderDto orderDto)
    {
   Product oneByIgnoreCaseProductName = productRepo.findByProductId(orderDto.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product", "product", orderDto.getProductId()));;
//   if(orderDto.getName().equalsIgnoreCase())
    String address = orderDto.getAddress();
    Order map = modelMapper.map(orderDto, Order.class);
    map.setProduct(oneByIgnoreCaseProductName);
    map.setStatus("Pending");
    Order save = orderRepo.save(map);
    return modelMapper.map(save, OrderDto.class);


}

    @Override
    public List<OrderDto> getAllOrder() {
        List<Order> all = orderRepo.findAll();
        List<OrderDto> collect = all.stream().map((order) -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<OrderDto> getOrdersByProductId(int productId) throws IllegalArgumentException {
        List<Order> orders = orderRepo.findByProductId(productId);
        if(!orders.isEmpty()){
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());}
        else
        {
            throw new EntityNotFoundException();
        }

               }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, int id) {
        Order order1 = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "order", +id));
        if(orderDto.getStatus()!=null)
        {
            order1.setStatus(orderDto.getStatus());
        }
        Order save = orderRepo.save(order1);
        OrderDto map = modelMapper.map(save, OrderDto.class);
//        S save = orderRepo.save(map);
        return map;
    }

    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setName(order.getName());
        dto.setQuantitiy(order.getQuantitiy());
        dto.setAddress(order.getAddress());
        dto.setProductId(order.getProduct().getProductId());
        return dto;
    }

    }


