package ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int productId;
//    @NotNull(message="vat should not be null!!")
    @NotEmpty(message = "Please provide a Product Name")
    @NotBlank(message = "Please provide a Product Name")
    @NotNull(message = "Please provide a Product Name")
    @Size(max=50, message="name must not be more than 50 words")
    private String productName;
    private double price;
    private LocalDateTime createdAt;

}
