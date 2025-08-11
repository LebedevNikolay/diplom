package data;

import lombok.SneakyThrows;
import lombok.val;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {

    private DbHelper() {
    }

    public static Connection getConnection() throws SQLException {
        var dbUrl = System.getProperty("db.url");
        var dbUserName = System.getProperty("db.user");
        var dbPassword = System.getProperty("db.pass");
        return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
    }

    @SneakyThrows
    public static void cleanTables() {
        var payment = "DELETE FROM payment_entity;";
        var credit = "DELETE FROM credit_request_entity;";
        var order = "DELETE FROM order_entity;";
        var runner = new QueryRunner();
        try (var connection = getConnection();) {
            runner.update(connection, payment);
            runner.update(connection, credit);
            runner.update(connection, order);
        } catch (SQLException e) {
            System.out.println("Таблицы payment_entity, credit_request_entity, order_entity очищены");
        }
    }

    @SneakyThrows
    public static String getCreditStatusDB() {
        val status = "SELECT status FROM credit_request_entity;";
        val runner = new QueryRunner();
        String creditStatus;

        try (var connection = getConnection();
        ) {
            creditStatus = runner.query(connection, status, new ScalarHandler<>());
        }
        return creditStatus;
    }

    @SneakyThrows
    public static String getPaymentStatusDB() {
        val status = "SELECT status FROM payment_entity;";
        val runner = new QueryRunner();
        String payStatus;

        try (var connection = getConnection();
        ) {
            payStatus = runner.query(connection, status, new ScalarHandler<>());
        }

        return payStatus;
    }

    @SneakyThrows
    public static long getPaymentCount() {
        val count = "SELECT COUNT(id) as count FROM payment_entity;";
        val runner = new QueryRunner();
        long payCount;

        try (
                var connection = getConnection();
        ) {
            payCount = runner.query(connection, count, new ScalarHandler<>());
        }
        return payCount;
    }

    @SneakyThrows
    public static long getCreditCount() {
        val count = "SELECT COUNT(id) as count FROM credit_request_entity;";
        val runner = new QueryRunner();
        long creditCount;

        try (var connection = getConnection();
        ) {
            creditCount = runner.query(connection, count, new ScalarHandler<>());
        }
        return creditCount;
    }

    @SneakyThrows
    public static long getOrderCount() {
        val count = "SELECT COUNT(id) as count FROM order_entity;";
        val runner = new QueryRunner();
        long orderCount;

        try (var connection = getConnection();
        ) {
            orderCount = runner.query(connection, count, new ScalarHandler<>());
        }
        return orderCount;
    }
}