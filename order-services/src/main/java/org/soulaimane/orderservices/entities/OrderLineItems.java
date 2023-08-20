package org.soulaimane.orderservices.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "T_Order_Items")
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
