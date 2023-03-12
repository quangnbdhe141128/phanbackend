package com.example.demo.repository;

import com.example.demo.domain.model.Vehicle;
import com.example.demo.request.booking.HomeBookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query(value = "SELECT p.* FROM vehicle p LEFT JOIN vehicle_type t ON  t.id = p.vehicle_type_id" +
            "  WHERE t.id = :#{#request.vehicleType} AND p.location = :#{#request.location}" +
            " AND (:#{#request.seatType} IS NULL OR  p.seat_type_id = :#{#request.seatType})" +
            " AND (:#{#request.vehicleCompany} IS NULL OR ( p.vehicle_company_id = :#{#request.vehicleCompany}))" +
            " AND(:#{#request.fuel} IS NULL OR ( p.fuel_type_id = :#{#request.fuel}))" +
            " AND (:#{#request.accountId} IS NULL OR ( p.account_id = :#{#request.accountId}))" +
            " AND (:#{#request.maxPrice} IS NULL OR ( p.price <= :#{#request.maxPrice}))" +
            " AND p.price >= :#{#request.minPrice}" +
            " AND p.status = true " +
            " ORDER BY (p.point) DESC,p.price DESC", nativeQuery = true)
    List<Vehicle> getVehicleeOrderPriceDESC(@Param("request") HomeBookingRequest request);

    @Query(value = "SELECT p.* FROM vehicle p LEFT JOIN vehicle_type t ON  t.id = p.vehicle_type_id" +
            "  WHERE t.id = :#{#request.vehicleType} AND p.location = :#{#request.location}" +
            " AND (:#{#request.seatType} IS NULL OR  p.seat_type_id = :#{#request.seatType})" +
            " AND (:#{#request.vehicleCompany} IS NULL OR ( p.vehicle_company_id = :#{#request.vehicleCompany}))" +
            " AND(:#{#request.fuel} IS NULL OR ( p.fuel_type_id = :#{#request.fuel}))" +
            " AND (:#{#request.accountId} IS NULL OR ( p.account_id = :#{#request.accountId}))" +
            " AND (:#{#request.maxPrice} IS NULL OR ( p.price <= :#{#request.maxPrice}))" +
            " AND p.price >= :#{#request.minPrice}" +
            " AND p.status = true " +
            " ORDER BY (p.point) DESC , p.price ASC", nativeQuery = true)
    List<Vehicle> getVehicleOrderPriceASC(@Param("request") HomeBookingRequest request);

    Optional<Vehicle> findByIdAndStatusIsTrue(Long id);

}
