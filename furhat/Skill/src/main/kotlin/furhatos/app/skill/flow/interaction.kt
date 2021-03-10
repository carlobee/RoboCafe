package furhatos.app.skill.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.nlu.Intent
//import furhatos.app.skill.nlu.*
import org.json.JSONObject

var coffee_complete = 0

val Start : State = state(Interaction) {
    onEntry {
        with(furhat) { listen() }
    }
    onEvent("sense.user.silence"){
        with(furhat) { listen() }
    }
    onEvent("sense.user.speak**"){
        with(furhat) {listen() }
    }
    onResponse { response ->
        System.out.println(response.text)
        val ALANA_URL = "http://52.56.181.83:5000"
//        val RASA_URL = "http://edcd47e520ab.ngrok.io/webhooks/myio/webhook"
        val COMPLETE_STRING = "Be sure to release the handle again for the next person."
//        val coffee_complete = false

        var alana_data = " "
        if (coffee_complete > 0) {
            alana_data = "{'user_id': 'test-user', 'question': " +  response.text + ", 'session_id': '03.UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':['weather_bot', 'wiki_bot_mongo', 'reddit_bot', {'greetings':'http://edcd47e520ab.ngrok.io/webhooks/myio/webhook'}], 'PRIORITY_BOTS':['greetings', 'weather_bot', 'reddit_bot', 'wiki_bot_mongo']}}"
//            val response = khttp.post(ALANA_URL, data= JSONObject(alana_data)).text
        } else {
            alana_data = "{'user_id': 'test-user', 'question': " +  response.text + ", 'session_id': '03.UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':[{'greetings':'http://edcd47e520ab.ngrok.io/webhooks/myio/webhook'}], 'PRIORITY_BOTS':['greetings']}}"
//            val response = khttp.post(ALANA_URL, data= JSONObject(alana_data)).text
        }
        val response = khttp.post(ALANA_URL, data= JSONObject(alana_data)).text
        if(JSONObject(response).getString("result").endsWith(COMPLETE_STRING)) {
            coffee_complete++
        }
//        System.out.println()
        furhat.say(JSONObject(response).getString("result"))
        with(furhat) { listen() }
    }
}

