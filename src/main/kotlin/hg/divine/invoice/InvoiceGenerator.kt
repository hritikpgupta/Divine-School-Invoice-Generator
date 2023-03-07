package hg.divine.invoice

import hg.divine.invoice.model.Bill
import hg.divine.invoice.model.FeeRow
import net.sf.jasperreports.engine.*
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InvoiceGenerator {

    private lateinit var jasperReport: JasperReport
    private var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    init {
        try {
            jasperReport =
                JasperCompileManager.compileReport(this.javaClass.getResourceAsStream("/invoiceTemplate/Divine_Invoice.jrxml"))
        } catch (ignored: JRException) {
        }
    }

    fun generateInvoice(bill: Bill): ByteArrayOutputStream? {
        val dataSourceMap: HashMap<String, Any> = constructDataSourceMap(bill)
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
        bill: Bill
    ): java.util.HashMap<String, Any> {
        val list = ArrayList<FeeRow>()
        list.add(FeeRow(feeType = "Tuition Fee", amount = 300))
        list.add(FeeRow(feeType = "Computer Fee", amount = 120))
        list.add(FeeRow(feeType = "Annual Fee", amount = 100))
        list.add(FeeRow(feeType = "Admission Fee", amount = 500))
        list.add(FeeRow(feeType = "Supplement Fee", amount = 70))
        list.add(FeeRow(feeType = "Transport Fee", amount = 450))
        list.add(FeeRow(feeType = "Book Fee", amount = 1250))
        list.add(FeeRow(feeType = "Exam Fee", amount = 200))
        var dataSource = JRBeanCollectionDataSource(list)
        val INR = "₹"
        val map = java.util.HashMap<String, Any>()
        map["FEE_LIST"] = dataSource
        map["TOTAL"] = "$INR 2750"
        map["NAME"] = "Hritik Gupta"
        map["GUARDIAN_NAME"] = "S/O Rakesh Gupta"
        map["ADDRESS"] = "Satyanganj, Ahraura"
        map["ROLL_NUMBER"] = "Roll No 24"
        map["CLASS"] = "Class One"
        map["INVOICE_NUMBER"] = "16483443"
        map["GENERATED_AT"] = LocalDateTime.now().format(formatter) + " at " + LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm"))
        return map
    }

}