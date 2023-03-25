package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        //Attempt a payment of amountSent for reservationId using the given mode ("cASh", "card", or "upi")
        //If the amountSent is less than bill, throw "Insufficient Amount" exception, otherwise update payment attributes
        //If the mode contains a string other than "cash", "card", or "upi" (any character in uppercase or lowercase), throw "Payment mode not detected" exception.
        //Note that the reservationId always exists

        Reservation reservation = reservationRepository2.findById(reservationId).get();

        Spot bookedSpot = reservation.getSpot();

        int bill = reservation.getNumOfHours() * bookedSpot.getPricePerHour();

        //check if amount == bill
        if(amountSent<bill){
            throw new Exception("Insufficient Amount");
        }

        Payment payment = new Payment();

        //if mode of payment card
        if(mode.equalsIgnoreCase(PaymentMode.CARD.toString())){

            payment.setPaymentCompleted(true);
            payment.setPaymentMode(PaymentMode.CARD);
            payment.setReservation(reservation);

        }

        //if mode of payment cash
        else if(mode.equalsIgnoreCase(PaymentMode.CASH.toString())){

            payment.setPaymentCompleted(true);
            payment.setPaymentMode(PaymentMode.CASH);
            payment.setReservation(reservation);

        }
        //if mode of payment UPI
        else if(mode.equalsIgnoreCase(PaymentMode.UPI.toString())){

            payment.setPaymentCompleted(true);
            payment.setPaymentMode(PaymentMode.UPI);
            payment.setReservation(reservation);

        }
        //invalid payment mode
        else{
            throw new Exception("Payment mode not detected");
        }

       reservationRepository2.save(reservation);

        return payment;

    }
}
