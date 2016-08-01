package com.kat

import groovy.json.JsonSlurper
import groovy.json.JsonBuilder
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KatWebApp {
    static def timer = new Timer()
    static void main(String[] args) {
        SpringApplication.run(KatWebApp.class, args)
        timer.schedule(new NotificationTask(),1000, 30000)// starts in 1 second after boot and cycles every 30 seconds
    }

}

class NotificationTask extends TimerTask {

    static def i=0;
    @Override
    void run() {
        notifySubscribers()
    }

    private void notifySubscribers(){
        String dataPlans = new File('./src/main/sample_data/plans.json').getText('UTF-8')
        String subscriptions = new File('./src/main/sample_data/subscriptions.json').getText('UTF-8')
        String users = new File('./src/main/sample_data/users.json').getText('UTF-8')

        def jsonSlurper = new JsonSlurper()
        def subscriptionList = jsonSlurper.parseText(subscriptions)
        def userList = jsonSlurper.parseText(users)
        def dataPlanList = jsonSlurper.parseText(dataPlans)
        def subscriptionUpdated = false;

        subscriptionList.subscriptions.each { subscription ->
            println "-----------------"
            println subscription.id
            println " notified values are $subscription.notifiedValues"
            println "Price per month threshold is $subscription.pricePerMonthThreshold"

            def subscribersDataPlan = dataPlanList.plans.findAll { dataPlan ->
                ((dataPlan.pricePerMonth <= subscription.pricePerMonthThreshold) &&
                        (!subscription.notifiedValues.contains(dataPlan.pricePerMonth)) &&
                        (dataPlan.type == subscription.planType)
                        )
            }

            if(subscribersDataPlan.size() > 0){
                //notify the subscriber if he/she has not been notified yet
                def u = userList.users.find { user ->
                    user.id == subscription.userId
                }
                println "*** notify $u.name using $subscription.notificationMethod that the" +
                        " $subscription.planType Internet plan is less than or equal to $subscription.pricePerMonthThreshold"
                println" --- data plans:"
                subscribersDataPlan.each{ dataPlan ->
                    subscription.notifiedValues.add(dataPlan.pricePerMonth)
                    println "Data Plan is $dataPlan.name price per month is $dataPlan.pricePerMonth"
                }
                subscriptionUpdated = true
            }
            println "------------------"
            if(subscriptionUpdated){
                new File('./src/main/sample_data/subscriptions.json').write(new JsonBuilder(subscriptionList).toPrettyString())
            }

        }


    }
}



