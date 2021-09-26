package uz.jamshid.app_warehouse.payload;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InputDto {
    private Timestamp date;
    private String factureName;
    private String code;
    private Integer warehouseId;
    private Integer supplierId;
    private Integer currencyId;
}
