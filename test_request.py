import requests
import json

ngrok = 'http://b572c08e8052.ngrok.io/webhooks/myio/webhook'
data = {'user_id': 'me', 'question': 'yes', 'session_id': 'CLI-sessionId', 'projectId': 'CA2020', 'overrides': {'BOT_LIST': [{"greetings": ngrok}], 'PRIORITY_BOTS': ['greetings']}}

r = requests.post(url='http://52.56.181.83:5000', json=data)
r.json()

print(json.dumps(r.json(), indent=4, sort_keys=True))