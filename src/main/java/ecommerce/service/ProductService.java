package ecommerce.service;

import ecommerce.dto.ProductDto;

import javax.persistence.EntityExistsException;
import java.util.List;

public interface ProductService {
    Object createProduct(ProductDto productDto) throws EntityExistsException;
    ProductDto updateProduct(String productName, ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getByProductName(String productName);

    void deleteProduct(String productName);

}
