import hg.divine.invoice.InvoiceGenerator
import hg.divine.invoice.model.FeeRow
import hg.divine.invoice.model.Invoice
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.Collections.emptyList

fun main() {
    val generator = InvoiceGenerator()
    val byteOS = generator.generateInvoice(Invoice(), emptyList<FeeRow>())
    byteOS.let {
        val fos = FileOutputStream(File("C:\\Users\\hgupta\\Downloads\\ha.pdf"))
        byteOS?.writeTo(fos)
    }

}