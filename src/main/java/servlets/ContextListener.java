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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

@WebListener
public class ContextListener implements ServletContextListener {
    private Map<Integer, String> users;
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String JDBC_DB_URL = "jdbc:postgresql://localhost:5432/bank";
    static final String JDBC_USER = "postgres";
    static final String JDBC_PASS = "qwqwqw";
    private DataSource dataSource=null;
    private GenericObjectPool gPool = null;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)  {
        System.out.println("CONTEXT INIT");
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
