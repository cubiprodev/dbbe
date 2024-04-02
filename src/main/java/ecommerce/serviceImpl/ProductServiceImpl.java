package ecommerce.serviceImpl;

import ecommerce.dto.ProductDto;
import ecommerce.entity.Product;
import ecommerce.repository.ProductRepo;
import ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepo productRepo;
    @Override
    public Object createProduct(ProductDto productDto) throws EntityExistsException {
        String productName = productDto.getProductName();
        Optional<Product> byProductName = productRepo.findOneByIgnoreCaseProductName(productName);
        if(byProductName.isPresent())
        {
            throw new EntityExistsException("User not found for Phone Number: " + productName);
        }
//        if(productName==null)
//        {
//            throw new NullPointerException("Please provide a product name" + productName);
//        }

        Product map = modelMapper.map(productDto, Product.class);

        map.setCreatedAt(LocalDateTime.now());
        Product save = productRepo.save(map);
        return modelMapper.map(save, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(String productName, ProductDto productDto) {
        Optional<Product> byProductName = productRepo.findOneByIgnoreCaseProductName(productName);

//        if(productName==null)
//        {
//            throw new NullPointerException("Please provide a Product Name");
//        }
        if(byProductName.isEmpty())
        {
            throw new EntityNotFoundException("User not found for Phone Number: " + productName);
        }
        Product product = byProductName.get();
        if(productDto.getProductName()!=null)
        {
            product.setProductName(productDto.getProductName());
        }
        if(productDto.getPrice()!=0)
        {
            product.setPrice(productDto.getPrice());
        }
        Product save = productRepo.save(product);
        return modelMapper.map(save, ProductDto.class);
    }

    public ProductDto getByProductName(String productName)
    {
        Optional<Product> byProductName = productRepo.findOneByIgnoreCaseProductName(productName);
        if(byProductName.isPresent())
        {
            return modelMapper.map(byProductName.get(), ProductDto.class);
        }
        if (productName.isEmpty() || productName.trim().isEmpty()) {
            throw new EntityNotFoundException("Please provide a valid product name");
        }
        else{
            throw new IllegalArgumentException();
        }

    }
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> userst = productRepo.findAll();
        List<ProductDto> collect = userst.stream().map((product)->modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return collect;

    }

    @Override
    public void deleteProduct(String productName) {
        Optional<Product> byProductName = productRepo.findOneByIgnoreCaseProductName(productName);
//        System.out.println(productName);
//        if(productName==null)
//        {
//            throw new NullPointerException("Please provide a Product Name");
//        }
        if(byProductName.isEmpty())
        {
            throw new EntityNotFoundException("User not found for Phone Number: " + productName);
        }
        Product product = byProductName.get();
        productRepo.delete(product);
    }
}
