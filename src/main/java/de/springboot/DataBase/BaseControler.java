package de.springboot.DataBase;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class BaseControler {

    private static JdbcTemplate jdbcTemplate;


    public static void initialization() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setUrl("jdbc:h2:file:D:/tmp/DataBase/test");
        dataSource.setPassword("");
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("CREATE TABLE if NOT EXISTS SHOP_USERS( id  INTEGER(10) AUTO_INCREMENT," +
                "login VARCHAR (20) NOT NULL," +
                "psw VARCHAR (20) NOT NULL," +
                "tel VARCHAR (11) NOT NULL," +
                "PRIMARY KEY (login)) ");
        System.out.println("Создание таблицы SHOP_USERS завршено");
    }


    public static boolean register(String login, String psw, String tel) {
        System.out.print("Регистрация нового пользователя " + login);
        try{
            jdbcTemplate.update(
                    "INSERT INTO SHOP_USERS(login,psw,tel) values(?,?,?)",
                    login, psw, tel);
            System.out.println(" - УСПЕШНА");
        } catch (Exception e) {
            System.out.println(" - НЕ УДАЛАСЬ");
            return false;
        }
        return true;
    }

    public static boolean autorize(String login, String psw) {
        String sql = "SELECT COUNT(ID) FROM SHOP_USERS where (login='" + login + "' AND psw='" + psw + "')";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);

        switch (count) {
            case 0:
                System.out.println("Не успешная попытка авторизоваться login = " + login);
                return false;
            case 1:
                System.out.println("Пользователь " + login + " вошел в систему");
                return true;

            default:
                System.out.println("ОШИБКА, МНОЖЕСТВЕННОСТЬ LOGIN = " + login + " В БД");
                return true;

        }
    }
}


