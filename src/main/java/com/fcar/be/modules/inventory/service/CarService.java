package com.fcar.be.modules.inventory.service;

import java.util.List;

import com.fcar.be.modules.inventory.dto.request.CarImportReq;
import com.fcar.be.modules.inventory.dto.response.CarDetailRes;

public interface CarService {
    CarDetailRes importCar(CarImportReq request);

    List<CarDetailRes> getAllCars();

    CarDetailRes getCarByVin(String vin);
}
