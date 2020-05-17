from fbchat import Client
from datetime import datetime, timedelta

import requests
import json

# Customization
MIN_HOURS = 1

servertokenpath = "ignoreme/servertoken.txt"
server_token = ""
with open(servertokenpath) as f:
    server_token = f.read().splitlines()[0]
device_token = ""

def set_device_token(token):
    device_token = token

def setup(username='', password=''):
    userpath = 'ignoreme/email.txt'
    passpath = 'ignoreme/password.txt'
    username = password = ''

    with open(userpath) as f:
        username = f.read().splitlines()[0]
    with open(passpath) as f:
        password = f.read().splitlines()[0]
    return Client(username, password)

def get_result():

    # Fetches a list of the 20 top threads you're currently chatting with
    threads = client.fetchThreadList()
    aList = []

    for thread in threads:
        messages = client.fetchThreadMessages(thread_id=thread.uid, limit=2)
        message_thing = messages[0]
        # I have no idea what happens if there are less than 2 messages, assume not :P

        # You ghosted them!
        if message_thing.author != clientuid:
            continue

        sent_time = datetime.fromtimestamp(int(message_thing.timestamp) // 1000)
        now = datetime.now()

        difference = now - sent_time

        # Greater than 3 is definitely ghosting XP
        if difference.total_seconds() > 3600 * MIN_HOURS:
            if difference.days < 1 and now.hour < 11:
                # You sent it at midnight... You get a pass.
                pass
            else:
                aList.append({'hours_passed': difference.total_seconds() / 3600, 'img': thread.photo})

    return aList

client = setup()
clientuid = client.uid

result = get_result()

headers = {
    'Content-Type': 'application/json',
    'Authorization': 'key=' + server_token,
}

for message in result:
    print(message)
    body = {
        'notification': message,
        'to': device_token,
        'priority': 'high',
    }

    response = requests.post("https://fcm.googleapis.com/fcm/send",
                            headers = headers, data=json.dumps(body))
    print(response.status_code)
