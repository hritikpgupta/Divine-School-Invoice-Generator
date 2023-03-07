package hg.divine.invoice.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Invoice(
    var invoiceNumber: String,
    var date: String,
    var className: String,
    var studentName: String,
    var guardianName: String,
    var address: String,
    var rollNumber: String,

    var admissionFee: Int,
    var annualCharge: Int,
    var tuitionFee: Int,
    var computerFee: Int,
    var transportFee: Int,
    var examFee: Int,
    var supplementaryFee: Int,
    var bookPrice: Int,
    var lateFee: Int,

    var total: String


) {
    constructor() : this(
        "", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " at " + LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm")), "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, ""
    )
}
