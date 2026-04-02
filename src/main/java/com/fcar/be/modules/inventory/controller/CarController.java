package com.fcar.be.modules.inventory.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.fcar.be.core.common.dto.ApiResponse;
import com.fcar.be.modules.inventory.dto.request.CarImportReq;
import com.fcar.be.modules.inventory.dto.response.CarDetailRes;
import com.fcar.be.modules.inventory.service.CarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/import")
    public ApiResponse<CarDetailRes> importCar(@RequestBody @Valid CarImportReq request) {
        return ApiResponse.<CarDetailRes>builder()
                .result(carService.importCar(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CarDetailRes>> getAllCars() {
        return ApiResponse.<List<CarDetailRes>>builder()
                .result(carService.getAllCars())
                .build();
    }

    @GetMapping("/{vin}")
    public ApiResponse<CarDetailRes> getCarByVin(@PathVariable String vin) {
        return ApiResponse.<CarDetailRes>builder()
                .result(carService.getCarByVin(vin))
                .build();
    }
}
