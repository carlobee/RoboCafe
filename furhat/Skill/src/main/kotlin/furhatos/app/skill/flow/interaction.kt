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
        System.out.println(response.userId)
        val ALANA_URL = "http://52.56.181.83:5000"
        val RASA_URL = "\'http://140fdb247b8c.ngrok.io/webhooks/myio/webhook\'"
        val COMPLETE_STRING = "Be sure to release the handle again for the next person."
        var alana_data = " "
        if (coffee_complete > 0) {
            alana_data = "{'user_id': " + response.userId + ", 'question': " +  response.text + ", 'session_id': '03.UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':['weather_bot', 'wiki_bot_mongo', 'reddit_bot', {'greetings':" + RASA_URL + "}], 'PRIORITY_BOTS':['greetings', 'weather_bot', 'reddit_bot', 'wiki_bot_mongo']}}"
        } else {
            alana_data = "{'user_id': " + response.userId + ", 'question': " +  response.text + ", 'session_id': '03.UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':[{'greetings':" + RASA_URL + "}], 'PRIORITY_BOTS':['greetings']}}"
        }
        val response = khttp.post(ALANA_URL, data= JSONObject(alana_data)).text
        if(JSONObject(response).getString("result").endsWith(COMPLETE_STRING)) {
            coffee_complete++
        }
        furhat.say(JSONObject(response).getString("result"))
        with(furhat) { listen() }
    }
}

