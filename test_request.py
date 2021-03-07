import requests
import json

ngrok = 'http://5185b2e426f4.ngrok.io'
data = {'user_id': 'test-user', 'question': 'Hi', 'session_id': 'CLI-sessionId', 'projectId': 'CA2020', 'overrides': {'BOT_LIST': [{"greetings": ngrok}], 'PRIORITY_BOTS': ['greetings']}}

r = requests.post(url='http://52.56.181.83:5000', json=data)
r.json()

print(json.dumps(r.json(), indent=4, sort_keys=True))