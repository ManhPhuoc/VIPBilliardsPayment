package com.group1.vipbilliardspayment.dto.response;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongKeDoanhThuTheoNgayResponse {
    Date ngay;
    double doanhThu;
}
