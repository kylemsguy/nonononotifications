from fbchat import Client
from datetime import datetime, timedelta

import snarkycomment as snarky

import requests
import json

# Customization
MIN_HOURS = 1

servertokenpath = "ignoreme/servertoken.txt"
server_token = ""
with open(servertokenpath) as f:
    server_token = f.read().splitlines()[0]
device_token = "dApTE--3R7C9XQMsY0QePz:APA91bGE9x1XS5mi0ZslYQ42cqzuoN76h5o1r2zq2_BcrYQfXaeyuH5XDI6_rkMdPCI4pdbbse8LNwGVE7V5mgiKtZg8Xe_Akouv_Lhz-4XjmPSTrcPgS6He7fQAihrPR5NzbRrRIAuw"

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
        # print(thread)
        messages = client.fetchThreadMessages(thread_id=thread.uid, limit=1)
        for message in messages:
            # print(message)

            # You ghosted them!
            if message.author != clientuid:
                continue

            sent_time = datetime.fromtimestamp(int(message.timestamp) // 1000)
            now = datetime.now()

            difference = now - sent_time

            # Greater than 3 is definitely ghosting XP
            if difference.total_seconds() > 3600 * MIN_HOURS:
                if difference.days < 1 and now.hour < 11:
                    # You sent it at midnight... You get a pass.
                    pass
                else:
                    aList.append({'hours_passed': difference.total_seconds() / 3600,
                                'uid': thread.uid,
                                'timestamp': thread.last_message_timestamp,
                                'name': thread.first_name,
                                'img': thread.photo})

    return aList

def send_notifications():
    for message in result:
        # print(message)
        print(snarky.get_comment(message))

        body = {
            'data':{
                'uid': message['uid'],
                'timestamp': message['timestamp'],
                'first_name': message['name'],
            },
            'notification': {
                                'title': "We're here for you",
                                'body': snarky.get_comment(message),
                                'image': message['img']
                            }
            ,
            'project_id': "nononotifications-deaf0",
            'to': device_token,
            'priority': 'high',
        }

        response = requests.post("https://fcm.googleapis.com/fcm/send",
                                headers = headers, data=json.dumps(body))
        print(response)

client = setup()
clientuid = client.uid

result = get_result()

headers = {
    'Content-Type': 'application/json',
    'Authorization': 'key=' + server_token,
}

send_notifications()
