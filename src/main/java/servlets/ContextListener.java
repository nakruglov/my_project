package servlets;

import model.user;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {
    private Map<Integer, String> users;
    public static final String PATH_TO_PROPERTIES = "E:/Code/from git/sweater-JPA_Postgres/my_progect/src/main/resources/config.properties";
    //static final String JDBC_DRIVER = "org.postgresql.Driver";
    //static final String JDBC_DB_URL = "jdbc:postgresql://localhost:5432/bank";
    //tatic final String JDBC_USER = "postgres";
    //static final String JDBC_PASS = "qwqwqw";
    private DataSource dataSource=null;
    private GenericObjectPool gPool = null;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)  {
        FileInputStream fileInputStream;
        Properties prop = new Properties();
        String JDBC_DRIVER="";
        String JDBC_DB_URL="";
        String JDBC_USER="";
        String JDBC_PASS="";
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            JDBC_DRIVER = prop.getProperty("JDBC_DRIVER");
            JDBC_DB_URL = prop.getProperty("JDBC_DB_URL");
            JDBC_USER = prop.getProperty("JDBC_USER");
            JDBC_PASS = prop.getProperty("JDBC_PASS");
            //печатаем полученные данные в консоль
            System.out.println(JDBC_DRIVER);
            System.out.println(JDBC_DB_URL);
            System.out.println(JDBC_USER);
            System.out.println(JDBC_PASS);

        } catch (IOException e) {
            System.out.println("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружено");
            e.printStackTrace();
        }
        final ServletContext servletContext = servletContextEvent.getServletContext();
        try {
            Class.forName(JDBC_DRIVER);
            ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
            PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, null);
            gPool = new GenericObjectPool(pcf);
            gPool.setMaxIdle(5);
            dataSource = new PoolingDataSource(gPool);
            servletContext.setAttribute("ds",dataSource);
            servletContext.setAttribute("users", users);

        } catch (Exception e){
            System.out.println("SQL exeption occured");}


        };


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
