package uz.jamshid.app_warehouse.payload;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OutputDto {
    private Timestamp date;
    private String factureName;
    private String code;
    private Integer warehouseId;
    private Integer clientId;
    private Integer currencyId;
}
