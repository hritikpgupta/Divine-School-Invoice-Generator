import hg.divine.invoice.InvoiceGenerator
import hg.divine.invoice.model.Bill
import java.io.File
import java.io.FileOutputStream

fun main() {

    val generator = InvoiceGenerator()
    val byteOS = generator.generateInvoice(Bill())
    byteOS.let {

        val fos = FileOutputStream(File("C:\\Users\\hriti\\Downloads\\abc.pdf"))
        byteOS?.writeTo(fos)
    }

}