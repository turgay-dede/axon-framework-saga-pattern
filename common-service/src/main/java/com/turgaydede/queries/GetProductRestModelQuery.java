package com.turgaydede.queries;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductRestModelQuery {
    private String productId;
}
