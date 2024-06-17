package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    public boolean verifyUserExistsByUser(String user){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from account where username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean verifyUserExistsById(int Id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from account where account_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Id);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean verifyUserCreds(String user, String pass){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from account where username = ? and password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Account retrieveUserByUser(String user){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from account where username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
            return new Account();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return new Account();
    }

    public Account createAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "insert into account (username, password) values (?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while(rs.next()){
                return new Account(rs.getInt(1), account.getUsername(), account.getPassword());
            }
            return new Account();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return new Account();
    }
}