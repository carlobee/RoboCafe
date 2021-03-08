import requests
import json


def send_message_to_rasa(user_id, message):
    # replace rasa_server with the ip or name of actual server
    message_url = 'http://localhost:5005/webhooks/rest/webhook'
    headers = {'Content-type': 'application/json'}
    user_id = 'test6'
    message = 'hi'
    # user_id is the name of the conversation, message is the text to send to Rasa
    payload = '{"sender": "' + str(user_id) + '", "message": "' + message + '"}'
    r = requests.post(message_url, data=payload.encode('utf-8'), headers=headers)
    reply = json.loads(r.text)
    print(reply[0]['text'])
    # print(reply)
    return reply[0]['text']
