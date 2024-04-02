package ecommerce.controller;

import ecommerce.dto.ApiResponse;
import ecommerce.dto.ApiResponset;
import ecommerce.dto.ProductDto;
import ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping(value="/createProduct")
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductDto productDto)
    {
        try{
        productService.createProduct(productDto);
            return ResponseEntity.ok(new ApiResponse("Product created successfully", true));

        }
        catch (EntityExistsException e){
           return new ResponseEntity<>(new ApiResponse("User with this Product Name already exists", false), HttpStatus.ALREADY_REPORTED);

        }
//        catch (NullPointerException e)
//        {
//            return new ResponseEntity<>(new ApiResponse("Please proviade a  Product Name", false), HttpStatus.NOT_FOUND);
//
//        }
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponset<?>> getByProductName(@RequestParam String productName) {
        try {
            ProductDto allProducts = productService.getByProductName(productName);
            return ResponseEntity.ok(new ApiResponset<>(true, "Product found", allProducts));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ApiResponset<>(false, "Please provide a name", null), HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponset<>(false, "Please provide a valid name", null), HttpStatus.BAD_REQUEST);
        }


    }
    @GetMapping("/getAll")
    public List<ProductDto> getAllProduct()
    {
        List<ProductDto> allProducts = productService.getAllProducts();
        return allProducts;
    }
    @PutMapping("/updateProduct")
    public ResponseEntity<ApiResponse> updateProduct(@Valid @RequestParam String productName, @RequestBody ProductDto productDto)
    {
        try
        {
            productService.updateProduct(productName, productDto);
            return ResponseEntity.ok(new ApiResponse("Created Successfully", true));
        }
//        catch (NullPointerException e)
//        {
//            return  new ResponseEntity<>(new ApiResponse("Please provide a product Name", false), HttpStatus.NOT_FOUND);
//        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(new ApiResponse("Please provide a valid name", false), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam(required = true) String productName)
    {

        try
        {
            productService.deleteProduct(productName);
//            return "deleted successfully";

            return ResponseEntity.ok(new ApiResponse("deleted successfully", true));
        }
//        catch (NullPointerException e)
//        {
//            return  new ResponseEntity<>(new ApiResponse("Please provide a product Name", false), HttpStatus.NOT_FOUND);
//        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(new ApiResponse("Please provide a valid name", false), HttpStatus.BAD_REQUEST);
        }
    }
}
