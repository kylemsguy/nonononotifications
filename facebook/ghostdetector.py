from fbchat import Client
from datetime import datetime, timedelta

# Customization
MIN_HOURS = 1

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
                    aList.append({'time_sent': sent_time, 'img': thread.photo})

    return aList

client = setup()
clientuid = client.uid

print(get_result())
