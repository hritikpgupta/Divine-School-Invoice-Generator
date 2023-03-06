package hg.divine.invoice

import hg.divine.invoice.model.Bill
import net.sf.jasperreports.engine.*
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
        val map = java.util.HashMap<String, Any>()
        map["BILL_NUMBER"] = bill.billNo
        map["BILL_DATE"] = bill.date
        map["CLASS_NAME"] = bill.className
        map["NAME"] = bill.name
        map["ADMISSION_FEE"] = bill.admissionFee
        map["ANNUAL_CHARGE"] = bill.annualCharge
        map["TUITION_FEE"] = bill.tuitionFee
        map["COMPUTER_FEE"] = bill.computerFee
        map["TRANSPORT_FEE"] = bill.transportFee
        map["EXAM_FEE"] = bill.examFee
        map["SUPPLEMENTARY_FEE"] = bill.supplementaryFee
        map["BOOK_PRICE"] = bill.bookPrice
        map["LATE_FEE"] = bill.lateFee
        map["MONTHS"] = bill.months
        map["TOTAL"] = bill.total
        map["GENERATED_DATE"] =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " on " + LocalDateTime.now()
                .format(formatter)
        return map
    }

}