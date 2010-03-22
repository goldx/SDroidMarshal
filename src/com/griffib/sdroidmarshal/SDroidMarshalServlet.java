package com.griffib.sdroidmarshal;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;


@SuppressWarnings("serial")
public class SDroidMarshalServlet extends HttpServlet {
  
  private byte[] recievedMsg = null;
  private static final Logger log = 
    Logger.getLogger(SDroidMarshalServlet.class.getName());

  
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    resp.setContentType("text/plain");
    if (recievedMsg == null) {
      resp.getWriter().println("No message recieved!");
    } else {
      resp.getWriter().println("Message recieved");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doPost(req, resp);
    
    try {
      ServletFileUpload upload = new ServletFileUpload();
      resp.setContentType("text/plain");

      FileItemIterator iterator = upload.getItemIterator(req);
      while (iterator.hasNext()) {
        FileItemStream item = iterator.next();
        InputStream stream = item.openStream();

        if (item.isFormField()) {
          log.warning("Got a form field: " + item.getFieldName());
        } else {
          log.warning("Got an uploaded file: " + item.getFieldName() +
                      ", name = " + item.getName());
          
        }
      }
    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    }
}
