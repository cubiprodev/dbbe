package ecommerce.controller;

import ecommerce.dto.ApiResponset;
import ecommerce.dto.OrderDto;
import ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PostMapping("/createOrder")
    public ResponseEntity<ApiResponset<?>> createOrder(@RequestBody OrderDto orderDto)
    {
        try {
            OrderDto order = orderService.createOrder(orderDto);
            return ResponseEntity.ok(new ApiResponset<>(true, "Created Successfully", order));
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(new ApiResponset<>(false, "Please provide an value", null), HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e)
        {
            return new ResponseEntity<>(new ApiResponset<>(false, "Please provide a correct value", null), HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponset<?>> getOrdersByProductId(@PathVariable int productId) {


        try {
          List<OrderDto> order = orderService.getOrdersByProductId(productId);
            return ResponseEntity.ok(new ApiResponset<>(true, "Created Successfully", order));
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(new ApiResponset<>(false, "Please provide an value", null), HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e)
        {
            return new ResponseEntity<>(new ApiResponset<>(false, "Please provide a correct value", null), HttpStatus.BAD_REQUEST);
        }
//        return ResponseEntity.ok(orders);
    }

    @GetMapping("/getAllOrders")
    public List<OrderDto> getOrderList()
    {
        return orderService.getAllOrder();
    }

    @PutMapping("/updateOrder")
    public OrderDto updateOrder(@RequestBody OrderDto orderDto, @RequestParam int id)
    {
        return orderService.updateOrder(orderDto, id);
    }
}
