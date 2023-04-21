package id.ac.ui.cs.advprog.bayarservice.service.coupon;

import id.ac.ui.cs.advprog.bayarservice.dto.coupon.CouponRequest;
import id.ac.ui.cs.advprog.bayarservice.dto.coupon.UseCouponRequest;
import id.ac.ui.cs.advprog.bayarservice.model.coupon.Coupon;

import java.util.UUID;

public interface CouponService {
    Coupon findById(Integer id);
    Coupon update(Integer id, CouponRequest request);
    void useCoupon(UUID id, UseCouponRequest request);
}