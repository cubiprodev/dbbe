package ecommerce.repository;

import ecommerce.entity.Order;
import ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    Optional<Product> findOneByIgnoreCaseProductName(String productName);

    Optional<Product> findByProductId(int productId);
}
