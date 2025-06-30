package com.softwares.service;


import com.softwares.models.Seller;
import com.softwares.models.SellerReport;

import java.util.List;
import java.util.Optional;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport( SellerReport sellerReport);

}
