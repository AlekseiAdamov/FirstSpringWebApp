package ru.geekbrains.webapp.servlets;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import ru.geekbrains.webapp.common.Catalog;
import ru.geekbrains.webapp.common.Product;
import ru.geekbrains.webapp.common.ProductPage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(urlPatterns = "/catalog/*")
public class CatalogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info(String.format("GET request: %s", req));

        PrintWriter writer = resp.getWriter();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            final Catalog catalog = Catalog.getInstance();
            writer.println(new ProductPage(catalog, req).getPageText());
            return;
        }

        final String stringId = pathInfo.replace("/", "");
        try {
            long id = Long.parseLong(stringId);
            final Product product = Catalog.getInstance().getProduct(id);
            if (product == null) {
                sendNotFound(resp, stringId);
                return;
            }
            writer.println(new ProductPage(product, req).getPageText());
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            sendNotFound(resp, stringId);
        }
    }

    private void sendNotFound(HttpServletResponse resp, String stringId) throws IOException {
        resp.setStatus(HttpStatus.SC_NOT_FOUND);
        resp.getWriter().println(String.format("Unable to find product with the id '%s'", stringId));
    }
}
