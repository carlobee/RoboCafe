package furhatos.app.skill.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.nlu.Intent
import org.json.JSONObject
//import furhatos.util.Gender.*
import furhatos.util.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures

//public var coffee_complete = 1

val myVoice = Voice(gender = Gender.MALE, language = Language.ENGLISH_US, pitch = "medium", rate = 1.2)

val task_switch = 0

val Start : State = state(Interaction) {
    onEntry {
        furhat.voice = myVoice
        if (task_switch == 0) {
            furhat.say("Hello. I am Furhat. I can give you assistance on making coffee or a number of other things.")
        } else if (task_switch == 1) {
            furhat.say("Hello. I am Furhat. I can give you assistance on making coffee.")
        }
        with(furhat) { listen() }
    }
    onEvent("sense.user.silence"){
        with(furhat) { listen() }
        furhat.gesture(Gestures.BigSmile(duration = 2.0))
    }
    onEvent("sense.user.speak**"){
        with(furhat) {listen() }
        furhat.gesture(Gestures.BigSmile(duration = 2.0))
    }
    onResponse { response ->
        val ALANA_URL = "http://52.56.181.83:5000"
        val RASA_URL = "\'http://2266f366979b.ngrok.io/webhooks/myio/webhook\'"
        val ROBOTARIUM_URL = "\'http://6ae1e400fb47.ngrok.io/webhooks/myio/webhook\'"
        var alana_data = " "
        if (task_switch == 0) {
            alana_data = "{'user_id': " + response.userId + ", 'question': " + response.text + ", 'session_id': '" + response.userId + ".UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':[{'coffee-bot':" + RASA_URL + "}, {'robotarium-bot':" + ROBOTARIUM_URL + "}, 'fact_bot', 'ontology_bot', 'reddit_bot', 'news_bot_v2', 'weather_bot', 'evi'], 'PRIORITY_BOTS':['coffee-bot', 'robotarium-bot', ['fact_bot', 'weather_bot', 'news_bot_v2', 'onotology_bot', 'reddit_bot', 'wiki_bot_mongo', 'evi']]}}"
//            alana_data = "{'user_id': " + response.userId + ", 'question': " + response.text + ", 'session_id': '" + response.userId + ".UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':[{'coffee-bot':" + RASA_URL + "}, 'fact_bot', 'ontology_bot', 'reddit_bot', 'news_bot_v2', 'weather_bot', 'evi'], 'PRIORITY_BOTS':['coffee-bot', ['fact_bot', 'weather_bot', 'news_bot_v2', 'onotology_bot', 'reddit_bot', 'wiki_bot_mongo', 'evi']]}}"
        } else if (task_switch == 1) {
            alana_data = "{'user_id': " + response.userId + ", 'question': " + response.text + ", 'session_id': '" + response.userId + ".UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':[{'coffee-bot':" + RASA_URL + "}], 'PRIORITY_BOTS':['coffee-bot']}}"
        }
        val response = khttp.post(ALANA_URL, data= JSONObject(alana_data)).text
        var result_string = JSONObject(response).getString("result")
        if (result_string.contains("OK please wait while I dispense your", ignoreCase = true)) {
            furhat.gesture(Gestures.Nod)
        }
        furhat.voice = myVoice
        furhat.say(result_string)
        with(furhat) { listen() }
    }
}

