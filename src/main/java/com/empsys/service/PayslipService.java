


package com.empsys.service;

import com.empsys.entity.PayslipEntity;
import com.empsys.entity.NotificationEntity;
import com.empsys.dao.NotificationRepository;
import com.empsys.dao.PayslipRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PayslipService {

    @Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public PayslipEntity generatePayslip(PayslipEntity payslip) {
        // Step 1: Calculate net salary
        int paidDays = payslip.getPresentDays() + payslip.getSickLeave() + payslip.getCasualLeave();
        double perDaySalary = payslip.getBasicSalary() / payslip.getTotalWorkingDays();
        double netSalary = Double.parseDouble(new DecimalFormat("#.##").format(perDaySalary * paidDays));
        payslip.setNetSalary(netSalary);

        // Step 2: Save initial payslip (without PDF)
        PayslipEntity savedPayslip = payslipRepository.save(payslip);

        // Step 3: Generate PDF
        try {
            String filename = "payslip_" + savedPayslip.getEmail().replaceAll("[^a-zA-Z0-9]", "_") + "_" + savedPayslip.getMonth() + ".pdf";
            String folderPath = "uploads";
            File directory = new File(folderPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fullPath = folderPath + "/" + filename;
            PdfWriter writer = new PdfWriter(new FileOutputStream(fullPath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Payslip"));
            document.add(new Paragraph("Month: " + savedPayslip.getMonth()));
            document.add(new Paragraph("Email: " + savedPayslip.getEmail()));
            document.add(new Paragraph("Employee ID: " + savedPayslip.getEmployeeId()));
            document.add(new Paragraph("Basic Salary: ₹" + savedPayslip.getBasicSalary()));
            document.add(new Paragraph("Total Working Days: " + savedPayslip.getTotalWorkingDays()));
            document.add(new Paragraph("Present Days: " + savedPayslip.getPresentDays()));
            document.add(new Paragraph("Sick Leave: " + savedPayslip.getSickLeave()));
            document.add(new Paragraph("Casual Leave: " + savedPayslip.getCasualLeave()));
            document.add(new Paragraph("Unpaid Leave: " + savedPayslip.getUnpaidLeave()));
            document.add(new Paragraph("Net Salary: ₹" + netSalary));
            document.close();

            // Step 4: Set PDF URL
            String pdfUrl = "http://localhost:8081/api/payroll/payslip/pdf/" + filename;
            savedPayslip.setPdfUrl(pdfUrl);

            // Step 5: Update payslip with PDF URL
            payslipRepository.save(savedPayslip);

            // ✅ Step 6: Send Notification to Employee
            NotificationEntity notification = new NotificationEntity();
            notification.setSenderEmail("hr@gmail.com");
            notification.setReceiverEmail(savedPayslip.getEmail());
            notification.setMessage("Your payslip for " + savedPayslip.getMonth() + " has been generated.");
            notification.setSeen(false);
            notification.setTimestamp(LocalDateTime.now());
            notificationRepository.save(notification);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedPayslip;
    }

    public java.util.List<PayslipEntity> getPayslipsByEmail(String email) {
        return payslipRepository.findByEmail(email);
    }
    
    public List<PayslipEntity> getAllPayslips() {
        return payslipRepository.findAll();
    }

}

