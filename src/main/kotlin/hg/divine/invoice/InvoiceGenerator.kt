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
        list.add(FeeRow(feeType = "Tuition", amount = 300))
        list.add(FeeRow(feeType = "Computer", amount = 100))
        var dataSource = JRBeanCollectionDataSource(list)

        val map = java.util.HashMap<String, Any>()
        map["FEE_LIST"] = dataSource
        map["TOTAL"] = 400
        map["NAME"] = "Hritik Gupta"
        map["GUARDIAN_NAME"] = "Rakesh Gupta"
        map["ADDRESS"] = "Ahraura"
        map["ROLL_NUMBER"] = "24"
        map["CLASS"] = "Class One"
        map["GENERATED_AT"] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " on " + LocalDateTime.now()
                .format(formatter)
        return map
    }

}