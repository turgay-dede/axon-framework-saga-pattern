
package com.turgaydede.command.api.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "products_lookup")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductLookupEntity {

    @Id
    private String productId;

    private String productName;

    private int quantity;
}
