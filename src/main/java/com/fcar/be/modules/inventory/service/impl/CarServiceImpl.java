package com.fcar.be.modules.inventory.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fcar.be.core.exception.AppException;
import com.fcar.be.core.exception.ErrorCode;
import com.fcar.be.modules.inventory.dto.request.CarImportReq;
import com.fcar.be.modules.inventory.dto.response.CarDetailRes;
import com.fcar.be.modules.inventory.entity.Car;
import com.fcar.be.modules.inventory.entity.MasterData;
import com.fcar.be.modules.inventory.mapper.CarMapper;
import com.fcar.be.modules.inventory.repository.CarRepository;
import com.fcar.be.modules.inventory.repository.MasterDataRepository;
import com.fcar.be.modules.inventory.service.CarService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final MasterDataRepository masterDataRepository;
    private final CarMapper carMapper;

    @Override
    public CarDetailRes importCar(CarImportReq request) {
        if (carRepository.existsById(request.getVin())) {
            throw new AppException(ErrorCode.CAR_NOT_FOUND); // Bạn có thể thêm CAR_EXISTED vào ErrorCode
        }
        if (carRepository.existsByEngineNumber(request.getEngineNumber())) {
            throw new AppException(ErrorCode.INVALID_KEY); // Sửa thành ENGINE_NUMBER_EXISTED sau
        }

        MasterData masterData = masterDataRepository
                .findById(request.getMasterDataId())
                .orElseThrow(
                        () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)); // Sửa thành MASTER_DATA_NOT_FOUND

        Car car = carMapper.toCar(request);
        car.setMasterData(masterData);
        car.setStatus("IN_WAREHOUSE");

        return carMapper.toCarDetailRes(carRepository.save(car));
    }

    @Override
    public List<CarDetailRes> getAllCars() {
        return carRepository.findAll().stream().map(carMapper::toCarDetailRes).toList();
    }

    @Override
    public CarDetailRes getCarByVin(String vin) {
        Car car = carRepository.findById(vin).orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_FOUND));
        return carMapper.toCarDetailRes(car);
    }
}
