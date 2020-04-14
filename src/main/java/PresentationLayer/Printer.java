package PresentationLayer;

import DataAccessLayer.OrderDAO;
import DataAccessLayer.OrderSumDAO;
import DataAccessLayer.PersonDAO;
import DataAccessLayer.ProductDAO;
import Model.OrderSum;
import Model.Orders;
import Model.Person;
import Model.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;

/**
 * Class that contains methods for generating pdf files.
 */
public class Printer {
    /**
     * It generates a pdf file with the Order of a person.
     * The pdf file's name will be "Receipt" + Person's name + ".pdf"
     * The pdf will contain every order of that person and the total Price that he has to pay.
     *
     * @param person the person
     */
    public void printOrders(Person person) {
        OrderDAO orderDAO = new OrderDAO();
        ProductDAO productDAO = new ProductDAO();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Receipt" + person.getName().replaceAll(" ", "") + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("RECEIPT for " + person.getName().toUpperCase(), font);
        paragraph.add("\n\n" + "The following product(s) have been bought: ");
        java.util.List<Orders> orders = orderDAO.findAllByPersonID(person.getId());
        for (Orders iterator : orders) {
            Product productBought = productDAO.findById(iterator.getProductId());
            paragraph.add("\n" + "The product: " + productBought.getName().toUpperCase() + " in quantity of: " + iterator.getQuantity());
        }
        OrderSumDAO orderSumDAO = new OrderSumDAO();
        OrderSum orderSum = orderSumDAO.findByPersonId(person.getId());
        paragraph.add("\n" + "The total price is: " + orderSum.getTotalPrice());
        paragraph.add("\n\n" + "Thank you for shopping with us!");
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    /**
     * Generates a pdf file to inform the Person with the name given by the parameter that there is an Understock.
     *
     * @param name     the name
     * @param product  the product
     * @param quantity the quantity
     */
    public void printUnderStock(String name, String product, Integer quantity) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("UnderStock" + name.replaceAll(" ", "") + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("UNDERSTOCK MESSAGE! \nDear " + name.toUpperCase() + ", \nWe are sorry to inform you that your command for our product: " + product.toUpperCase() + " in the quantity of: " + quantity + " cannot be fulfilled as our stock contains only " + new ProductDAO().findByName(product).getQuantity() + " pieces.", font);
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();

    }

    /**
     * Generic method to generate a pdf file in tabular form for an Object(the Object can be a {@link Person} or a {@link Product}.
     * The pdf file name has the following form: the Object's class name + timeStamp + ".pdf"
     *
     * @param objectsToPrint the objects to print
     */
    public void printAll(java.util.List<?> objectsToPrint) {
        Document document = new Document();
        try {
            if (!objectsToPrint.isEmpty()) {
                PdfWriter.getInstance(document, new FileOutputStream(objectsToPrint.get(0).getClass().getName().substring(objectsToPrint.get(0).getClass().getName().indexOf(".") + 1) + new Timestamp(System.currentTimeMillis()).toString().replaceAll(":", "") + ".pdf"));
                document.open();
                document.add(new Paragraph(objectsToPrint.get(0).getClass().getName().substring(objectsToPrint.get(0).getClass().getName().indexOf(".") + 1) + "\n\n"));
                PdfPTable table = new PdfPTable(objectsToPrint.get(0).getClass().getDeclaredFields().length - 1);
                for (Field field : objectsToPrint.get(0).getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (!field.getName().equals("id")) {
                        PdfPCell header = new PdfPCell(new Paragraph(field.getName().toUpperCase()));
                        header.setBackgroundColor(BaseColor.CYAN);
                        header.setBorderWidth(2);
                        table.addCell(header);
                    }
                }

                for (Object iterator : objectsToPrint) {
                    for (Field field : iterator.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        if (!field.getName().equals("id")) {
                            table.addCell(field.get(iterator).toString());
                        }
                    }
                }
                document.add(table);
            }
        } catch (DocumentException | FileNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        document.close();
    }

    /**
     * Method used to print for each {@link Orders} in the list given through the orders parameter, the name, the product, the quantity and the total price to pay in a pdf file in a tabular form.
     * The pdf file name has the following form: "Orders" + timeStamp + ".pdf"
     *
     * @param orders the orders
     */
    public void printAllOrders(List<Orders> orders) {
        Document document = new Document();
        try {
            if (!orders.isEmpty()) {
                PdfWriter.getInstance(document, new FileOutputStream("Orders" + new Timestamp(System.currentTimeMillis()).toString().replaceAll(":", "") + ".pdf"));
                document.open();
                document.add(new Paragraph("Orders\n\n"));
                PdfPTable table = new PdfPTable(4);
                PdfPCell header = new PdfPCell(new Paragraph("NAME"));
                header.setBackgroundColor(BaseColor.CYAN);
                header.setBorderWidth(2);
                table.addCell(header);
                PdfPCell header2 = new PdfPCell(new Paragraph("PRODUCT"));
                header2.setBackgroundColor(BaseColor.CYAN);
                header2.setBorderWidth(2);
                table.addCell(header2);
                PdfPCell header3 = new PdfPCell(new Paragraph("QUANTITY"));
                header3.setBackgroundColor(BaseColor.CYAN);
                header3.setBorderWidth(2);
                table.addCell(header3);
                PdfPCell header4 = new PdfPCell(new Paragraph("TOTAL PRICE TO PAY"));
                header4.setBackgroundColor(BaseColor.CYAN);
                header4.setBorderWidth(2);
                table.addCell(header4);
                for (Orders iterator : orders) {
                    Person person = new PersonDAO().findById(iterator.getPersonId());
                    Product product = new ProductDAO().findById(iterator.getProductId());
                    OrderSum orderSum = new OrderSumDAO().findByPersonId(iterator.getPersonId());
                    table.addCell(person.getName());
                    table.addCell(product.getName());
                    table.addCell(String.valueOf(iterator.getQuantity()));
                    table.addCell(String.valueOf(orderSum.getTotalPrice()));
                }
                document.add(table);
            }
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        document.close();
    }
}
