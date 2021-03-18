package com.example.bankingAPI.repo

import com.example.bankingAPI.pojo.Customer
import org.springframework.stereotype.Repository
import java.lang.StringBuilder
import java.util.*
import kotlin.math.roundToInt


@Repository
class CustomerRepo {


    private val activeCustomers = mutableListOf<Customer>()
    val allCustomers = mutableListOf<Customer>()
    init{

        val customerOne = Customer ( "12345", "Thando", "Dlamini",true,12000.00)
        val customerTwo = Customer ("54321","Sonwabile", "Sigenu", true,6000.00)
        val customerThree = Customer ("34343","Mihle", "Ngotyana", false,0.00)

        allCustomers.add(customerOne)
        allCustomers.add(customerTwo)
        allCustomers.add(customerThree)
    }
    fun retrieveAllCustomers() : List <Customer>{
        return allCustomers
    }

    fun activeCustomers() : List <Customer>{

        for (x in 0 until allCustomers.size){
            if (allCustomers[x].accStatus===true)
                activeCustomers.add(allCustomers[x])
        }
        return activeCustomers
    }

    fun getCustomerWithAccNumber(accountNumber : String) : Customer? {
        return findCustomerByAccNo(accountNumber)

    }

    fun addCustomer( newCustomer: Customer) : Boolean{
        var result : Boolean = false

        for (x in 0 until allCustomers.size){
            if(newCustomer.accountNumber != allCustomers[x].accountNumber)
                result = true
        }
        if (result)
            allCustomers.add(newCustomer)
        return result
    }

    fun findCustomerByAccNo(accountNumber:String): Customer?{
        return allCustomers.find{it.accountNumber == accountNumber}
    }

    fun getTransferDetails (senderAccountNumber:String, recipientAccountNumber:String, amount:String) : String{

        val amount = amount.toDouble()
        var result : String = ""


        for (x in 0 until allCustomers.size){
            if (allCustomers[x].accountNumber == senderAccountNumber && allCustomers[x].accStatus.toString() == "true") {
                var senderIndex = x
                    for (x in 0 until allCustomers.size) {
                        if (allCustomers[x].accountNumber == recipientAccountNumber && allCustomers[x].accStatus.toString() == "true") {
                            var receiverIndex = x
                                if (amount <= allCustomers[senderIndex].accBalance ) {
                                    allCustomers[senderIndex].accBalance -= amount
                                    allCustomers[receiverIndex].accBalance += amount
                                    result = "An amount of £ " + amount + " has been sent to " + allCustomers[receiverIndex].name +
                                             "/ Senders balance: £ " + allCustomers[senderIndex].accBalance + "/" + "Recipient balance: £ " + allCustomers[receiverIndex].accBalance
                                } else result = "There isn't sufficient funds in the account."
                            }

                    }
            }
        }
        return result
    }





}