package com.fcar.be.modules.inventory.service;

import com.fcar.be.modules.inventory.dto.request.CarImportReq;
import com.fcar.be.modules.inventory.dto.response.CarDetailRes;

import java.util.List;

public interface CarService {
    CarDetailRes importCar(CarImportReq request);
    List<CarDetailRes> getAllCars();
    CarDetailRes getCarByVin(String vin);
}