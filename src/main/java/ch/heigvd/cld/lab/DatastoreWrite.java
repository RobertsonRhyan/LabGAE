package ch.heigvd.cld.lab;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "DatastoreWrite", value = "/datastorewrite")
public class DatastoreWrite extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");
        PrintWriter pw = resp.getWriter();

        // Check if kind param is set
        String kind = request.getParameter("_kind");
        String key = request.getParameter("_key");
        if(kind == null){
            pw.println("_kind is missing !");
            throw new IOException("_kind is missing");
        }

        pw.println("Writing entity to datastore.");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity entity;

        // If key param isn't set, let Datastore generate one
        if(key == null){
            entity = new Entity(kind);
        }else{
            entity = new Entity(kind, key);
        }


        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String entityName = e.nextElement();
            if(entityName.startsWith("_"))
                continue;
            entity.setProperty(entityName, request.getParameter(entityName));
        }

        datastore.put(entity);

        /**
        pw.println("Writing entity to datastore.");


        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity book = new Entity("book");
        book.setProperty("title", "The grapes of wrath");
        book.setProperty("author", "John Steinbeck");
        datastore.put(book);

         */
    }
}