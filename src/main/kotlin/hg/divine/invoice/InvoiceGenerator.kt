package hg.divine.invoice

import hg.divine.invoice.model.FeeRow
import hg.divine.invoice.model.Invoice
import net.sf.jasperreports.engine.*
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import java.io.ByteArrayOutputStream

class InvoiceGenerator {

    private lateinit var jasperReport: JasperReport

    init {
        try {
            jasperReport =
                JasperCompileManager.compileReport(this.javaClass.getResourceAsStream("/invoiceTemplate/Divine_Invoice.jrxml"))
        } catch (ignored: JRException) {
        }
    }

    fun generateInvoice(invoice: Invoice, list: List<FeeRow>): ByteArrayOutputStream? {
        val dataSourceMap: HashMap<String, Any> = constructDataSourceMap(invoice, list)
        return try {
            val jasperPrint = JasperFillManager.fillReport(jasperReport, dataSourceMap, JREmptyDataSource())
            val os = ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, os)
            os
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun constructDataSourceMap(
        invoice: Invoice, list: List<FeeRow>
    ): java.util.HashMap<String, Any> {
        invoice.total = "2670"
        invoice.studentName = "Hritik Gupta"
        invoice.guardianName = "S/O Rakesh Kumar Gupta"
        invoice.address = "Satyanganj, Ahraura"
        invoice.rollNumber = "24"
        invoice.className = "Play Group"
        invoice.invoiceNumber = "1643845672-2344"
        invoice.date = " 24-10-1996"
        val feeList = ArrayList<FeeRow>()
        feeList.add(FeeRow(feeType = "Computer Fee", amount = 120))
        feeList.add(FeeRow(feeType = "Annual Fee", amount = 100))
        feeList.add(FeeRow(feeType = "Late Fee", amount = 100))
        feeList.add(FeeRow(feeType = "Admission Fee", amount = 500))
        feeList.add(FeeRow(feeType = "Transport Fee", amount = 450))
        feeList.add(FeeRow(feeType = "Exam Fee", amount = 200))
       // feeList.add(FeeRow(feeType = "Supplement Fee \n*Junior Tie,Belt,Diary", amount = 70))
        feeList.add(FeeRow(feeType = "Supplement Fee", amount = 70))
        //feeList.add(FeeRow(feeType = "Tuition Fee \n*January,March,April,May,June", amount = 300))
        feeList.add(FeeRow(feeType = "Tuition Fee", amount = 300))
       // feeList.add(FeeRow(feeType = "Book Fee \n*Art and Activity,Bharat aur Vishav,Sahaj Hindi Vyakran,Science, \n Math,Geography,Geography,Geography,Geography,Geography,Geography", amount = 1250))
        feeList.add(FeeRow(feeType = "Book Fee", amount = 1250))


        val dataSource = JRBeanCollectionDataSource(feeList)
        val map = java.util.HashMap<String, Any>()
        map["FEE_LIST"] = dataSource
        map["TOTAL"] = invoice.total
        map["NAME"] = invoice.studentName
        map["GUARDIAN_NAME"] = invoice.guardianName
        map["ADDRESS"] = invoice.address
        map["ROLL_NUMBER"] = invoice.rollNumber
        map["CLASS"] = invoice.className
        map["INVOICE_NUMBER"] = invoice.invoiceNumber
        map["GENERATED_AT"] = invoice.date
        map["months"] = "January, February, March, April"
        map["books"] = "Book Details : Art and Activity $160, Bharat aur Vishav $190, Bhasha Sagar $330, Computer $230, English Grammer $195, Eglish Reader $200, G.K $210, Math $390, Sahaj Hindi Vyakran $180, Sanskrit Sagar $125, Science $290"
        //map["books"] = "Book Details : -"
       // map["books"] = "Book Details : Art and Activity $160, Bharat aur Vishav $190, Bhasha Sagar $330, Computer $230"
        return map
    }

}