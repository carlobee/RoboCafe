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

val Start : State = state(Interaction) {
    onEntry {
        furhat.voice = myVoice
        furhat.say("Hello. I am Furhat. I can give you assistance on making coffee or a number of other things.")
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
//        System.out.println(response.text)
//        System.out.println(response.userId)
        val ALANA_URL = "http://52.56.181.83:5000"
        val RASA_URL = "\'http://4b299c7aeb65.ngrok.io/webhooks/myio/webhook\'"
        val COMPLETE_STRING = "Be sure to release the handle again for the next person."
//        var alana_data = "{'user_id': " + response.userId + ", 'question': " +  response.text + ", 'session_id': '" + response.userId + ".UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':[{'coffee-bot':" + RASA_URL + "}, 'profanity_bot', 'fact_bot', 'ontology_bot', 'reddit_bot', 'evi', 'news_bot_v2', 'weather_bot'], 'PRIORITY_BOTS':['coffee-bot', 'profanity_bot', 'fact_bot', 'weather_bot', 'news_bot_v2', 'onotology_bot', 'reddit_bot', 'wiki_bot_mongo', 'evi']}}"
        var alana_data = "{'user_id': " + response.userId + ", 'question': " +  response.text + ", 'session_id': '" + response.userId + ".UUID4', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':[{'coffee-bot':" + RASA_URL + "}, 'fact_bot', 'ontology_bot', 'reddit_bot', 'news_bot_v2', 'weather_bot', 'evi'], 'PRIORITY_BOTS':['coffee-bot', ['fact_bot', 'weather_bot', 'news_bot_v2', 'onotology_bot', 'reddit_bot', 'wiki_bot_mongo', 'evi']]}}"
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

