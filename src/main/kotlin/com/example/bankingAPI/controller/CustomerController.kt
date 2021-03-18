package com.example.bankingAPI.controller

import com.example.bankingAPI.pojo.Customer
import com.example.bankingAPI.repo.CustomerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
class CustomerController {

    @Autowired
    val customerRepo = CustomerRepo ()
    private val characters = "1234567890"
    private val accountNumberLength = 5

    @GetMapping("/retrieveAllCustomers")
    fun getAllCustomers() : List <Customer> {

        return customerRepo .retrieveAllCustomers()
    }

    @GetMapping("/retrieveActiveCustomers")
    fun activeAllCustomers() : List <Customer> {

        return customerRepo .activeCustomers()
    }

    @GetMapping("/findCustomer/{accountNumber}")
    fun retrieveCustomerByAccNumber(@PathVariable accountNumber:String): Customer? {
        return customerRepo.getCustomerWithAccNumber(accountNumber)
    }

    @PostMapping ("/createCustomer")
    fun createCustomer(@RequestBody customer : Customer) :String {
        var result : String = ""
        customer.accountNumber = getAccountNumber()
        if(customerRepo.addCustomer(customer))
            result =  "Customer created successfully"
        else
            result = "There was an error creating the customer. Try again."

        return result
    }

    @GetMapping("/transferMoney/{senderAccountNumber}/{recipientAccountNumber}/{amount}")
    fun getTransferDetails(@PathVariable senderAccountNumber:String, @PathVariable recipientAccountNumber:String, @PathVariable amount:String): String {
        val result = customerRepo.getTransferDetails(senderAccountNumber, recipientAccountNumber, amount)
            return result
    }

    fun getAccountNumber () : String {

       val sb = StringBuilder(accountNumberLength)
       for (x in 0 until accountNumberLength){

           val random :Int = (characters.indices).random()
           sb.append(characters[random])
       }
        return sb.toString()

   }

}