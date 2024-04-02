package ecommerce.repository;

import ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {
//    List<Order> findByProduct(int productId);

//    @Query("SELECT * FROM order WHERE product_product_id = :productId")
//    List<Order> findByProductId(@Param("productId") int productId);
@Query("SELECT o FROM Order o WHERE o.product.productId = :productId")
List<Order> findByProductId(@Param("productId") int productId);

}
