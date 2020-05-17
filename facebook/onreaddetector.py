from fbchat import Client
from datetime import datetime, timedelta

userpath = 'ignoreme/email.txt'
passpath = 'ignoreme/password.txt'
username = password = ""

with open(userpath) as f:
    username = f.read().splitlines()[0]
with open(passpath) as f:
    password = f.read().splitlines()[0]



client = Client(username, password)

users = client.fetchAllUsers()

# Fetches a list of the 20 top threads you're currently chatting with
threads = client.fetchThreadList()

for thread in threads:
    messages = client.fetchThreadMessages(thread_id=thread.uid, limit=1)
    for message in messages:
        print(message)
        sent_time = datetime.fromtimestamp(int(message.timestamp)//1000)
        now = datetime.now()

        print(sent_time.hour)
        difference = now - sent_time

        # Greater than 3 is definitely ghosting XP
        hours = 3
        if difference.total_seconds() > 3600*hours:
            if difference.days < 1 and now.hour < 11:
                # You sent it at midnight... You get a pass.
                pass
            else:
                print('ghosted :P')
                pass


if not client.isLoggedIn():
    client.logout()
