package furhatos.app.skill.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.nlu.Intent
//import furhatos.app.skill.nlu.*
import org.json.JSONObject

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
        val alana_data = "{'user_id': 'test-user', 'question': response.text, 'session_id': 'CLI-sessionId', 'projectId': 'CA2020', 'overrides': {'BOT_LIST':['coherence_bot', {'greetings':'http://19540022a18d.ngrok.io'}], 'PRIORITY_BOTS':['greetings', 'coherence_bot']}}"
        val response = khttp.post(ALANA_URL, data= JSONObject(alana_data)).text
        System.out.println(response)
        with(furhat) { listen() }
    }
}
