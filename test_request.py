import requests
import json

ngrok = 'http://33a79ed56241.ngrok.io/webhooks/myio/webhook'
data = {'user_id': 'test-user', 'question': 'Hello', 'session_id': 'CLI-sessionId', 'projectId': 'CA2020', 'overrides': {'BOT_LIST': [{"coffee-bot": ngrok}], 'PRIORITY_BOTS': ['coffee-bot']}}

r = requests.post(url='http://52.56.181.83:5000', json=data)
r.json()

print(json.dumps(r.json(), indent=4, sort_keys=True))