package servlets;

import model.user;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "/")
public class GetIndexPageServlet extends HttpServlet {
    private static String index = "/WEB-INF/index.jsp";
    private Map<Integer, String> users;
    int i=1;
    final List<String> list= new ArrayList<>();
    @Override
    public void init() throws ServletException {
        ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        DataSource dataSource;

        final ConcurrentHashMap<Integer,String> users = (ConcurrentHashMap<Integer,String>)getServletContext().getAttribute("users");
        this.users = (ConcurrentHashMap<Integer, String>) users;
        //ConnectionPool jdbcObj = new ConnectionPool();
        try {
            dataSource =(DataSource)( getServletContext().getAttribute("ds"));
            System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
            connObj = dataSource.getConnection();

            pstmtObj = connObj.prepareStatement("SELECT * FROM users");
            rsObj = pstmtObj.executeQuery();
            while (rsObj.next()) {
                //System.out.println("Username: " + rsObj.getString(2));
               // users.put(i++,rsObj.getString(2));
                list.add(rsObj.getString(2));
            }
            System.out.println("\n=====Releasing Connection Object To Pool=====\n");
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
         req.setAttribute("users", list);
         req.getRequestDispatcher(index).forward(req, resp);
        //PrintWriter pw = resp.getWriter();
        /*for (String s:list
             ) {
            //System.out.println("MAP ="+s);
            pw.write(s);
        }*/



    }
}