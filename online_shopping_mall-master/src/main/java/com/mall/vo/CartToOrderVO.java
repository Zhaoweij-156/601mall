package com.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartToOrderVO {
    String orderSn;
    List<Integer> cartIds;
    long memberId;
    int addressId;
    String note;
}
