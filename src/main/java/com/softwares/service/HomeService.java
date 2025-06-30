package com.softwares.service;


import com.softwares.models.Home;
import com.softwares.models.HomeCategory;

import java.util.List;

public interface HomeService {

    Home creatHomePageData(List<HomeCategory> categories);

}
