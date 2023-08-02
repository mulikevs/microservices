package com.sitemapdev.addressservice.repository;

import com.sitemapdev.addressservice.entity.Address;
import com.sitemapdev.addressservice.response.AddressResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//public interface AddressRepository extends JpaRepository<Address, Long> {
//
////    @Query(value = "SELECT ad.id, ad.lane1,ad.lane2,ad.state,ad.uuid,ad.zip,em.bloodgroup,em.email,em.name,em.uuid FROM sitemapdevmicro.address  ad join sitemapdevmicro.employees  em on em.id=ad.employee_id where ad.employee_id =:employeeId;")
//    @Query(nativeQuery = true ,value = "SELECT ad.id, ad.lane1,ad.lane2,ad.state,ad.uuid,ad.zip FROM sitemapdevmicro.address  ad join sitemapdevmicro.employees  em on em.id=ad.employee_id where ad.employee_id = :employeeId;")
//    Address findAdressByEmployeeId(@Param("employeeId") String employeeId);
//}
//public interface AddressRepository extends JpaRepository<Address, Long> {
//
//    @Query(nativeQuery = true, value = "SELECT ad.id, ad.lane1, ad.lane2, ad.state, ad.uuid, ad.zip FROM sitemapdevmicro.address ad JOIN sitemapdevmicro.employees em ON em.id = ad.employee_id WHERE ad.employee_id = ?2;")
//    Address findAddressByEmployeeId(Long employeeId);
//}

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(nativeQuery = true, value = "SELECT ad.id, ad.lane1, ad.lane2, ad.state, ad.uuid, ad.zip " +
            "FROM sitemapdevmicro.address ad " +
            "JOIN sitemapdevmicro.employees em ON em.id = ad.employee_id " +
            "WHERE ad.employee_id = :employeeId")
    Address findAddressByEmployeeId(@Param("employeeId") Long employeeId);
}



