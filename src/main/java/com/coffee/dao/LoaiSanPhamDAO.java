package com.coffee.dao;

import com.coffee.entity.LoaiSanPham;
import com.coffee.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDAO extends CoffeeDAO<LoaiSanPham, String> {

    String insertLoaiSanPham = "INSERT INTO LOAISANPHAM VALUES (?, ?, ?)";
    String updateLoaiSanPham = "UPDATE LOAISANPHAM SET TenLoai=?, MoTa=? WHERE MaLoai=?";
    String deleteLoaiSanPham = "DELETE FROM LOAISANPHAM WHERE MaLoai=?";
    String selectAllLoaiSanPham = "SELECT * FROM LOAISANPHAM";
    String selectByIdLoaiSanPham = "SELECT * FROM LOAISANPHAM WHERE MaLoai=?";

    @Override
    public void insert(LoaiSanPham entity) {
        XJdbc.update(insertLoaiSanPham,
                entity.getMaLoai(),
                entity.getTenLoai(),
                entity.getMoTa()
        );
    }

    @Override
    public void update(LoaiSanPham entity) {
        XJdbc.update(updateLoaiSanPham,
                entity.getTenLoai(),
                entity.getMoTa(),
                entity.getMaLoai()
        );
    }

    @Override
    public void delete(String id) {
        XJdbc.update(deleteLoaiSanPham, id);
    }

    @Override
    public List<LoaiSanPham> selectAll() {
        return this.selectBySql(selectAllLoaiSanPham);
    }

    @Override
    public List<LoaiSanPham> selectBySql(String sql, Object... args) {
        List<LoaiSanPham> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                LoaiSanPham loai = new LoaiSanPham();

                loai.setMaLoai(resultSet.getString(1));
                loai.setTenLoai(resultSet.getString(2));
                loai.setMoTa(resultSet.getString(3));

                list.add(loai);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public LoaiSanPham selectById(String id) {
        List<LoaiSanPham> list = this.selectBySql(selectByIdLoaiSanPham, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
