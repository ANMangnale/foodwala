package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.MenuDao;
import com.tap.model.Menu;
import com.tap.util.DBConnection;

public class MenuDaoImpl implements MenuDao {

    private static final String INSERT_MENU =
            "INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, imagePath) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_MENU =
            "SELECT * FROM menu WHERE menuId = ?";

    private static final String GET_ALL_MENUS =
            "SELECT * FROM menu";

    private static final String GET_MENUS_BY_RESTAURANT_ID =
            "SELECT * FROM menu WHERE restaurantId = ?";

    private static final String UPDATE_MENU =
            "UPDATE menu SET restaurantId = ?, itemName = ?, description = ?, price = ?, isAvailable = ?, imagePath = ? WHERE menuId = ?";

    private static final String DELETE_MENU =
            "DELETE FROM menu WHERE menuId = ?";


    @Override
    public void addMenu(Menu menu) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_MENU)) {

            pstmt.setInt(1, menu.getRestaurantId());
            pstmt.setString(2, menu.getItemName());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setBoolean(5, menu.isAvailable());
            pstmt.setString(6, menu.getImagePath());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Menu getMenu(int menuId) {

        Menu menu = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_MENU)) {

            pstmt.setInt(1, menuId);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {

                menu = new Menu();

                menu.setMenuId(resultSet.getInt("menuId"));
                menu.setRestaurantId(resultSet.getInt("restaurantId"));
                menu.setItemName(resultSet.getString("itemName"));
                menu.setDescription(resultSet.getString("description"));
                menu.setPrice(resultSet.getDouble("price"));
                menu.setAvailable(resultSet.getBoolean("isAvailable"));
                menu.setImagePath(resultSet.getString("imagePath"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menu;
    }


    @Override
    public List<Menu> getAllMenus() {

        List<Menu> menus = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ALL_MENUS)) {

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                Menu menu = new Menu();

                menu.setMenuId(resultSet.getInt("menuId"));
                menu.setRestaurantId(resultSet.getInt("restaurantId"));
                menu.setItemName(resultSet.getString("itemName"));
                menu.setDescription(resultSet.getString("description"));
                menu.setPrice(resultSet.getDouble("price"));
                menu.setAvailable(resultSet.getBoolean("isAvailable"));
                menu.setImagePath(resultSet.getString("imagePath"));

                menus.add(menu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menus;
    }


    @Override
    public List<Menu> getMenusByRestaurantId(int restaurantId) {

        List<Menu> menus = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_MENUS_BY_RESTAURANT_ID)) {

            pstmt.setInt(1, restaurantId);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                Menu menu = new Menu();

                menu.setMenuId(resultSet.getInt("menuId"));
                menu.setRestaurantId(resultSet.getInt("restaurantId"));
                menu.setItemName(resultSet.getString("itemName"));
                menu.setDescription(resultSet.getString("description"));
                menu.setPrice(resultSet.getDouble("price"));
                menu.setAvailable(resultSet.getBoolean("isAvailable"));
                menu.setImagePath(resultSet.getString("imagePath"));

                menus.add(menu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menus;
    }


    @Override
    public void updateMenu(Menu menu) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE_MENU)) {

            pstmt.setInt(1, menu.getRestaurantId());
            pstmt.setString(2, menu.getItemName());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setBoolean(5, menu.isAvailable());
            pstmt.setString(6, menu.getImagePath());
            pstmt.setInt(7, menu.getMenuId());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteMenu(int menuId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_MENU)) {

            pstmt.setInt(1, menuId);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}