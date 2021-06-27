package ru.geekbrains.webapp.common;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Getter
public class ProductPage {
    private final HttpServletRequest request;
    private String pageText = "";

    {
        InputStream is = getClass().getClassLoader().getResourceAsStream("productPageTemplate.html");
        if (is == null) {
            log.error("Can't read resource file 'productPageTemplate.html'");
        }
        try {
            pageText = new String(is != null ? is.readAllBytes() : new byte[0]);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public ProductPage(ProductCatalog catalog, HttpServletRequest request) {
        this.request = request;
        final StringBuilder sb = new StringBuilder();
        sb.append("<table class=\"table table-bordered table-striped table-hover\">\n");
        sb.append(getHeaderRow());
        sb.append("\t<tbody>");
        for (int i = 1; i <= catalog.getSize(); i++) {
            Product product = catalog.getProduct(i);
            sb.append(getProductRow(product));
        }
        sb.append("\t</tbody>");
        sb.append("</table>");
        if (pageTextIsValid()) {
            pageText = String.format(pageText, sb);
        }
    }

    public ProductPage(Product product, HttpServletRequest request) {
        this.request = request;
        final String tableContent = "<table class=\"table table-bordered table-striped table-hover\">\n" +
                getHeaderRow() +
                "\t<tbody>" +
                getProductRow(product) +
                "\t</tbody>" +
                "</table>";
        if (pageTextIsValid()) {
            pageText = String.format(pageText, tableContent);
        }
    }

    private boolean pageTextIsValid() {
        return !(pageText == null || pageText.isEmpty());
    }

    private String getHeaderRow() {
        return "\t<thead>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<th class=\"center\" scope=\"col\">ID</th>\n" +
                "\t\t\t<th class=\"center\" scope=\"col\">Name</th>\n" +
                "\t\t\t<th class=\"center\" scope=\"col\">Cost, USD</th>\n" +
                "\t\t</tr>\n" +
                "\t</thead>\n";
    }

    private String getProductRow(Product product) {
        return "\t\t<tr>\n" +
                String.format("\t\t\t<td class=\"center\">%d</td>\n", product.getId()) +
                String.format("\t\t\t<td><a href=\"%s%s/%d\">%s</a></td>\n",
                        request.getContextPath(),
                        request.getServletPath(),
                        product.getId(),
                        product.getName()) +
                String.format("\t\t\t<td class=\"right\">%.2f</td>\n", product.getPrice()) +
                "\t\t</tr>\n";
    }
}
