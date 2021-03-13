package furhatos.app.skill.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.nlu.Intent
//import furhatos.app.skill.nlu.*
import org.json.JSONObject

public var coffee_complete = 1

val Start : State = state(Interaction) {
    onEntry {
        furhat.say("Hello. I am Furhat. I can give you assistance on making coffee or just talk about a number of things like the weather or give you a random fact.")
        with(furhat) { listen() }
//        coffee_complete = 0
    }
    onEvent("sense.user.silence"){
        with(furhat) { listen() }
    }
    onEvent("sense.user.speak**"){
        with(furhat) {listen() }
    }
    onResponse { response ->
//        System.out.println(response.text)
//        System.out.println(response.userId)
        val ALANA_URL = "http://52.56.181.83:5000"
        val RASA_URL = "\'http://bc28f1c2b66d.ngrok.io/webhooks/myio/webhook\'"
        val COMPLETE_STRING = "Be sure to release the handle again for the next person."
//        var alana_data = " "
        var alana_data = "{'user_id': " + response.userId + ", 'question': " +  response.text + ", 'session_id': '03.UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':['profanity_bot', 'fact_bot', 'ontology_bot', 'reddit_bot', 'evi', 'news_bot_v2', 'weather_bot', {'coffee-bot':" + RASA_URL + "}], 'PRIORITY_BOTS':['coffee-bot', 'profanity_bot', 'fact_bot', 'weather_bot', 'news_bot_v2', 'onotology_bot', 'reddit_bot', 'wiki_bot_mongo', 'evi']}}"
//        if (coffee_complete > 0) {
//            alana_data = "{'user_id': " + response.userId + ", 'question': " +  response.text + ", 'session_id': '03.UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':['fact_bot', 'news_bot_v2', 'weather_bot', {'greetings':" + RASA_URL + "}], 'PRIORITY_BOTS':['greetings', 'weather_bot', 'fact_bot', 'news_bot_v2']}}"
//        } else {
//            alana_data = "{'user_id': " + response.userId + ", 'question': " +  response.text + ", 'session_id': '03.UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':[{'greetings':" + RASA_URL + "}], 'PRIORITY_BOTS':['greetings']}}"
//        }
        val response = khttp.post(ALANA_URL, data= JSONObject(alana_data)).text
//        if(JSONObject(response).getString("result").endsWith(COMPLETE_STRING)) {
//            coffee_complete++
//        }
        furhat.say(JSONObject(response).getString("result"))
        with(furhat) { listen() }
    }
}

