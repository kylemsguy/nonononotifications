from fbchat import Client
from datetime import datetime, timedelta

import snarkycomment as snarky

import requests
import json
import random

# Customization
MIN_HOURS = 1

servertokenpath = "ignoreme/servertoken.txt"
server_token = ""
with open(servertokenpath) as f:
    server_token = f.read().splitlines()[0]
devicetokenpath = "ignoreme/devicetoken.txt"
device_token = ""
with open(devicetokenpath) as f:
    device_token = f.read().splitlines()[0]

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

            is_read = message.is_read

            sent_time = datetime.fromtimestamp(int(message.timestamp) // 1000)
            now = datetime.now()

            difference = now - sent_time

            # Greater than 3 is definitely ghosting XP
            if difference.total_seconds() > 3600 * MIN_HOURS:
                if False and difference.days < 1 and now.hour < 11:
                    # You sent it at midnight... You get a pass.
                    pass
                else:
                    aList.append({'hours_passed': difference.total_seconds() / 3600,
                                'uid': thread.uid,
                                'timestamp': thread.last_message_timestamp,
                                'is_read': is_read,
                                'first_name': thread.first_name,
                                'full_name': thread.name,
                                'img': thread.photo})

    return aList

def send_notifications():
    if not result:
        return
    index = random.randint(0, len(result)-1)
    message = result[index]

    # for message in result:

    # print(message)
    comment = snarky.get_comment(message)
    title = snarky.get_title(message)
    print(title)
    print(comment)

    body = {
        'data':{
            'uid': message['uid'],
            'timestamp': message['timestamp'],
            'first_name': message['first_name'],
            'full_name': message['full_name'],
            'title': title,
            'body': comment,
            'image': message['img']
        },
        # Don't use notification, since it uses default notifs
        'project_id': "nononotifications-deaf0",
        'to': device_token,
        'priority': 'high',
    }

    response = requests.post("https://fcm.googleapis.com/fcm/send",
                            headers = headers, data=json.dumps(body))
    print(response)


if __name__ == "__main__":
    client = setup()
    clientuid = client.uid

    result = get_result()

    headers = {
        'Content-Type': 'application/json',
        'Authorization': 'key=' + server_token,
    }

    send_notifications()
