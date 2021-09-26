package uz.jamshid.app_warehouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.jamshid.app_warehouse.entity.template.AbsEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Measurement extends AbsEntity {
}
