package ru.geekbrains.webapp.common;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Getter
public class ProductPage {
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
    
    public ProductPage(Catalog catalog) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<table class=\"table\">\n");
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

    public ProductPage(Product product) {
        final String tableContent = "<table class=\"table\">\n" +
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
        return "\t<thead class=\"header center\">\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td class=\"table\">ID</td>\n" +
                "\t\t\t<td class=\"table\">Name</td>\n" +
                "\t\t\t<td class=\"table\">Cost, USD</td>\n" +
                "\t\t</tr>\n" +
                "\t</thead>\n";
    }

    private String getProductRow(Product product) {
        return "\t\t<tr>\n" +
                String.format("\t\t\t<td class=\"table center\">%d</td>\n", product.getId()) +
                String.format("\t\t\t<td class=\"table\">%s</td>\n", product.getName()) +
                String.format("\t\t\t<td class=\"table right\">%.2f</td>\n", product.getPrice()) +
                "\t\t</tr>\n";
    }
}
