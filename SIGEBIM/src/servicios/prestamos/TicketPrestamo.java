package servicios.prestamos;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import modelo.prestamo.Prestamo;

public class TicketPrestamo {

    public static void generarComprobante(Prestamo prestamo) {
        Document document = new Document();
        try {
            String nombreArchivo = "comprobante_" + prestamo.getIdPrestamo() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            Image logo = Image.getInstance("src/img/LOGO.png");
            logo.scaleToFit(120, 120);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("BIBLIOTECA MUNICIPAL DE CHACLACAYO", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("------------------------------------------------------------------------------------------------------------------------"));
            document.add(new Paragraph(" "));

            Font fontLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font fontDato = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("ID de Préstamo: ", fontLabel));
            document.add(new Paragraph(prestamo.getIdPrestamo(), fontDato));

            document.add(new Paragraph("ISBN del Libro: ", fontLabel));
            document.add(new Paragraph(prestamo.getLibro().getIsbn(), fontDato));

            document.add(new Paragraph("Nombre del Libro: ", fontLabel));
            document.add(new Paragraph(prestamo.getLibro().getTitulo(), fontDato));

            document.add(new Paragraph("DNI: ", fontLabel));
            document.add(new Paragraph(prestamo.getUsuario().getDni(), fontDato));

            document.add(new Paragraph("Nombre del Usuario: ", fontLabel));
            document.add(new Paragraph(prestamo.getUsuario().getNombre(), fontDato));

            // USO DE MÉTODOS FORMATEADOS SEGUROS
            document.add(new Paragraph("Fecha de Préstamo: ", fontLabel));
            document.add(new Paragraph(prestamo.getFechaPrestamoFormateada(), fontDato));

            document.add(new Paragraph("Fecha Límite: ", fontLabel));
            document.add(new Paragraph(prestamo.getFechaLimiteFormateada(), fontDato));

            document.add(new Paragraph("Fecha de Devolución: ", fontLabel));
            document.add(new Paragraph(prestamo.getFechaDevolucionFormateada(), fontDato));

            document.add(new Paragraph("Estado: ", fontLabel));
            document.add(new Paragraph(prestamo.getEstadoPrestamo().getTexto(), fontDato));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("------------------------------------------------------------------------------------------------------------------------"));
            document.add(new Paragraph(" "));

            Font fontPie = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10);
            Paragraph pie = new Paragraph("Gracias por usar el servicio de biblioteca municipal", fontPie);
            pie.setAlignment(Element.ALIGN_CENTER);
            document.add(pie);

            document.close();
            JOptionPane.showMessageDialog(null, "Comprobante generado: " + nombreArchivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar comprobante: " + e.getMessage());
        }
    }
}