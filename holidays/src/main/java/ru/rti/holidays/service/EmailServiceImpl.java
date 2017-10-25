package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.utility.CommonUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Set;

@SpringComponent
public class EmailServiceImpl implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    public JavaMailSender emailSender;

    public static final String MAIL_SUBJECT_YOUR_HOLIDAY_PERIODS_HAVE_BEEN_NEGOTIATED = "Ваши периоды отпуска были согласованы";
    public static final String MAIL_SUBJECT_YOUR_HOLIDAY_PERIODS_HAVE_BEEN_REJECTED = "Ваши периоды отпуска были отклонены";
    public static final String MAIL_SUBJECT_EMPLOYEE_SUBMITTED_HOLIDAY_PERIOD = "Сотрудник направил период отпуска на согласование";
    public static final String MAIL_SUBJECT_PREFIX = "[Система отпусков РТИ]: ";
    public static final String MAIL_FROM = "Система отпусков РТИ";
    public static final String DOUBLE_BREAK_LINE = "<br/><br/>";
    //TODO: update link before going to production mode!!!
    public static final String MAIL_BODY_FOOTER = DOUBLE_BREAK_LINE + "Для просмотра текущего статуса Ваших отпусков перейдите по следующей <a href='http://localhost:8080/main'>ссылке</a>";
    public static final String MAIL_BODY_EMPLOYEE_SUBMITTED_HOLIDAY_PERIOD_NEED_NEGOTIATION = "<h3>Сотрудник направил на согласование период отпуска, требуется Ваше согласование.</h3><br/>"+
            "ФИО сотрудника: <b>%s</b><br/>" +
            "Дата начала отпуска: <b>%s</b><br/>" +
            "Количество дней: <b>%s</b><br/>" + MAIL_BODY_FOOTER;
    public static final String MAIL_BODY_EMPLOYEE_SUBMITTED_HOLIDAY_PERIODS = "<h3>%s направил на согласование периоды отпуска, требуется Ваше согласование.</h3><br/>";
    public static final String MAIL_BODY_MANAGER_NEGOTIATED_HOLIDAY_PERIODS = "%s, добрый день!" + DOUBLE_BREAK_LINE +
            "<b>%s</b> согласовал Ваши периоды отпуска, направленные Вами на согласование:<br/><br/>";
    public static final String MAIL_BODY_MANAGER_REJECTED_HOLIDAY_PERIODS = "%s, добрый день!" + DOUBLE_BREAK_LINE +
            "<b>%s</b> отклонил Ваши периоды отпуска, направленные Вами на согласование:<br/><br/>";
    public static final String MAIL_BODY_PART_HOLIDAY_PERIOD = "Дата начала отпуска: <b>%s</b><br/>" +
            "Количество дней: <b>%s</b>";

    /**
     * Sends the Email to all the managers of the Employee, when the Employee submits new holiday period for negotiation
     * @param holidayPeriod a new holiday period, added by Employee and submitted for negotiation
     * @param employee an Employee instance, who submitted the holiday period for negotiation
     * @param managers a Set of managers, who negotiate the holiday period
     * @return
     */
    public boolean sendMailHolidayPeriodSubmitted(HolidayPeriod holidayPeriod, Employee employee, Set<Employee> managers) {
        if (holidayPeriod == null || employee == null || managers == null) {
            log.error("Error: 'null' value detected when not expected! " +
                    "Details: EmailServiceImpl.java, method: sendMailHolidayPeriodSubmitted, params: holidayPeriod = " + holidayPeriod + ", employee = " + employee + ", managers = " + managers);
            return false;
        }

        String messageBody = String.format(MAIL_BODY_EMPLOYEE_SUBMITTED_HOLIDAY_PERIOD_NEED_NEGOTIATION,
                employee.getFullName(),
                holidayPeriod.getDateStartAsString(),
                holidayPeriod.getNumDaysAsString()
        );

        boolean finalResult = true;
        for (Employee manager : managers) {
            finalResult = finalResult && sendMail(manager.getEmail(), messageBody, MAIL_SUBJECT_EMPLOYEE_SUBMITTED_HOLIDAY_PERIOD);
        }

        return finalResult;
    }

    @Override
    public boolean sendMailHolidayPeriodSubmitted(Iterable<HolidayPeriod> holidayPeriods, Employee employee, Set<Employee> managers) {
        if (holidayPeriods == null || employee == null || managers == null) {
            log.error("Error: 'null' value detected when not expected! " +
                    "Details: EmailServiceImpl.java, method: sendMailHolidayPeriodSubmitted, params: holidayPeriods = " + holidayPeriods + ", employee = " + employee + ", managers = " + managers);
            return false;
        }

        String messageBodyStart = String.format(MAIL_BODY_EMPLOYEE_SUBMITTED_HOLIDAY_PERIODS,
                employee.getFullName()
        );

        StringBuilder sbHolidayPeriods = new StringBuilder();
        for (HolidayPeriod hp : holidayPeriods) {
            if (sbHolidayPeriods.length() > 0) {
                sbHolidayPeriods.append(DOUBLE_BREAK_LINE);
            }
            sbHolidayPeriods.append(String.format(MAIL_BODY_PART_HOLIDAY_PERIOD, hp.getDateStartAsString(), hp.getNumDaysAsString()));
        }

        String messageBody = messageBodyStart + sbHolidayPeriods.toString();

        boolean finalResult = true;
        for (Employee manager : managers) {
            finalResult = finalResult && sendMail(manager.getEmail(), messageBody, MAIL_SUBJECT_EMPLOYEE_SUBMITTED_HOLIDAY_PERIOD);
        }

        return finalResult;
    }

    /**
     * Sends the Email to the Employee, when the manager negotiated the holiday period for this Employee
     * @param holidayPeriods A collection of Holiday Periods, that have been negotiated by the manager
     * @param manager The instance of Employee who is the manager, that negotiated the holiday periods
     * @return
     */
    @Override
    public boolean sendMailHolidayPeriodsNegotiated(Iterable<EmployeeHolidayPeriod> holidayPeriods, Employee manager) {
        if (holidayPeriods == null || manager == null) {
            log.error("Error: 'null' value detected when not expected! " +
                    "Details: EmailServiceImpl.java, method: sendMailHolidayPeriodsNegotiated, params: holidayPeriods = " + holidayPeriods + ", manager = " + manager);
            return false;
        }

        EmployeeHolidayPeriod first = holidayPeriods.iterator().next();
        String employeeFullName = first.getEmployeeFullName();
        String employeeEmail = first.getEmployeeEmail();

        if (CommonUtils.checkIfAnyIsNull(employeeFullName, employeeEmail)) {
            log.error("Error: 'null' value detected when not expected! " +
                    "Details: EmailServiceImpl.java, method: sendMailHolidayPeriodsNegotiated, params: employeeFullName = " + employeeFullName + ", employeeEmail = " + employeeEmail);
            return false;
        }

        String messageBodyStart = String.format(MAIL_BODY_MANAGER_NEGOTIATED_HOLIDAY_PERIODS,
                employeeFullName,
                manager.getFullName()
        );

        StringBuilder sbHolidayPeriods = new StringBuilder();
        for (EmployeeHolidayPeriod hp : holidayPeriods) {
            if (sbHolidayPeriods.length() > 0) {
                sbHolidayPeriods.append(DOUBLE_BREAK_LINE);
            }
            sbHolidayPeriods.append(String.format(MAIL_BODY_PART_HOLIDAY_PERIOD, hp.getDateStartAsString(), hp.getNumDaysAsString()));
        }

        String messageBodyFull = messageBodyStart + sbHolidayPeriods.toString() + MAIL_BODY_FOOTER;

        boolean result = sendMail(employeeEmail, messageBodyFull, MAIL_SUBJECT_YOUR_HOLIDAY_PERIODS_HAVE_BEEN_NEGOTIATED);

        return result;
    }

    /**
     * Sends the Email to the Employee, when the manager rejected the holiday period for this Employee
     * @param holidayPeriods A collection of Holiday Periods, that have been rejected by the manager
     * @param manager The instance of Employee who is the manager, that rejected the holiday periods
     * @return
     */
    @Override
    public boolean sendMailHolidayPeriodsRejected(Iterable<EmployeeHolidayPeriod> holidayPeriods, Employee manager) {
        if (holidayPeriods == null || manager == null) {
            log.error("Error: 'null' value detected when not expected! " +
                    "Details: EmailServiceImpl.java, method: sendMailHolidayPeriodsRejected, params: holidayPeriods = " + holidayPeriods + ", manager = " + manager);
            return false;
        }

        EmployeeHolidayPeriod first = holidayPeriods.iterator().next();
        String employeeFullName = first.getEmployeeFullName();
        String employeeEmail = first.getEmployeeEmail();

        if (CommonUtils.checkIfAnyIsNull(employeeFullName, employeeEmail)) {
            log.error("Error: 'null' value detected when not expected! " +
                    "Details: EmailServiceImpl.java, method: sendMailHolidayPeriodsRejected, params: employeeFullName = " + employeeFullName + ", employeeEmail = " + employeeEmail);
            return false;
        }

        String messageBodyStart = String.format(MAIL_BODY_MANAGER_REJECTED_HOLIDAY_PERIODS,
                employeeFullName,
                manager.getFullName()
        );

        StringBuilder sbHolidayPeriods = new StringBuilder();
        for (EmployeeHolidayPeriod hp : holidayPeriods) {
            if (sbHolidayPeriods.length() > 0) {
                sbHolidayPeriods.append(DOUBLE_BREAK_LINE);
            }
            sbHolidayPeriods.append(String.format(MAIL_BODY_PART_HOLIDAY_PERIOD, hp.getDateStartAsString(), hp.getNumDaysAsString()));
        }

        String messageBodyFull = messageBodyStart + sbHolidayPeriods.toString() + MAIL_BODY_FOOTER;

        boolean result = sendMail(employeeEmail, messageBodyFull, MAIL_SUBJECT_YOUR_HOLIDAY_PERIODS_HAVE_BEEN_REJECTED);

        return result;
    }

    /**
     * Generic method to send an Email letter.
     * @param to the recipient of the letter
     * @param messageBody the message text (body) of the letter
     * @param subject the subject of the letter
     * @return returns true if no errors occurred while the whole delivery process of the email. Otherwise, false.
     */
    public boolean sendMail(String to, String messageBody, String subject) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMsgHelper = null;
        try {
            mimeMsgHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessage.setContent(messageBody, "text/html; charset=UTF-8");
            mimeMsgHelper.setTo(to);
            mimeMessage.setSentDate(new Date());
            mimeMsgHelper.setSubject(MAIL_SUBJECT_PREFIX + subject);
            //TODO:change email to real address before going to production mode!!!
            mimeMsgHelper.setFrom(MAIL_FROM + "<mabramkin@gmail.com>");
            emailSender.send(mimeMessage);
        } catch (MessagingException msge) {
            log.error("Cannot send E-Mail message (MessagingException caught). Reason: " + msge.getLocalizedMessage());
            return false;
        } catch (MailException me) {
            log.error("Cannot send E-Mail message (MailException caught). Reason: " + me.getLocalizedMessage());
            return false;
        }

        return true;
    }
}