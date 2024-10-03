package com.ekart.springekartapplication.DTO;

import lombok.Data;

@Data
public class SellerResponseDTO {
    private String username;
    private String shopName;
    private String shopAddress;
    private String emailSeller;
    private String phoneNumberSeller;
}
