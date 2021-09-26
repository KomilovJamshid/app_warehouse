package uz.jamshid.app_warehouse.payload;

import lombok.Data;

import java.util.Date;

@Data
public class InputProductDto {
    private Double amount;
    private Double price;
    private Date expireDate;
    private Integer inputId;
    private Integer productId;
}
