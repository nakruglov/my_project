package servlets;

import org.apache.commons.dbcp2.PoolingDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//@WebServlet(name = "/")
public class GetIndexPageServlet extends HttpServlet {
    private static String index = "/WEB-INF/index.jsp";

    @Override
    public void init() throws ServletException {
        ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        DataSource dataSource;
        //ConnectionPool jdbcObj = new ConnectionPool();
        try {
            dataSource =(DataSource)( getServletContext().getAttribute("ds"));
            System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
            connObj = dataSource.getConnection();

            pstmtObj = connObj.prepareStatement("SELECT * FROM person");
            rsObj = pstmtObj.executeQuery();
            while (rsObj.next()) {
                System.out.println("Username: " + rsObj.getString(2));
            }
            System.out.println("\n=====Releasing Connection Object To Pool=====\n");
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
           PrintWriter pw = resp.getWriter();
           pw.println("WWWW");

        //req.getRequestDispatcher(index).forward(req, resp);

    }
}